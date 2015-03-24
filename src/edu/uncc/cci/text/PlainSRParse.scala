
package edu.uncc.cci.text

import java.util.Properties
import scala.collection.JavaConverters._
import java.nio.file.{Paths, Files}
import java.nio.charset.StandardCharsets
import edu.stanford.nlp.ling.CoreAnnotations.SentencesAnnotation
import edu.stanford.nlp.pipeline.Annotation
import edu.stanford.nlp.pipeline.StanfordCoreNLP
import edu.stanford.nlp.trees._
import edu.stanford.nlp.trees.Tree
import edu.stanford.nlp.trees.TypedDependency
import edu.stanford.nlp.trees.TreeCoreAnnotations.TreeAnnotation
import edu.stanford.nlp.ling.CoreAnnotations.SentencesAnnotation
import edu.stanford.nlp.trees.TreeCoreAnnotations.TreeAnnotation
import edu.stanford.nlp.semgraph.SemanticGraph;
import edu.stanford.nlp.semgraph.SemanticGraphCoreAnnotations.CollapsedCCProcessedDependenciesAnnotation;
//import com.chaoticity.dependensee.Main;

object PlainSRParse extends App {
  
  def parseToTrees(pipeline: StanfordCoreNLP, text: String) : List[(Tree, SemanticGraph)] = {
      
      // create an empty Annotation just with the given text
      val document = new Annotation(text)
      
      // run all Annotators on this text
      pipeline.annotate(document)
      
      // these are all the sentences in this document
      // a CoreMap is essentially a Map that uses class objects as keys and has values with custom types
      val sentences = document.get(classOf[SentencesAnnotation]).asScala
      sentences.toList.map(sentence => {
        // this is the parse tree of the current sentence
        (
          sentence.get(classOf[TreeAnnotation]),
          sentence.get(classOf[CollapsedCCProcessedDependenciesAnnotation])
        )
      })
  }

  override def main(args: Array[String])= {
    // Boilerplate code to start the pipeline
    val props = new Properties()
    props.put("annotators", "tokenize, ssplit, pos, parse")
    props.put("parse.model", "edu/stanford/nlp/models/srparser/englishSR.ser.gz")
    val pipeline = new StanfordCoreNLP(props)
    
    // Run some sentences through the pipeline
    val trees = io.Source.fromFile("ABRS", "utf-8")
      .getLines()
      .toList.par // Read all the sentences and then process them all at once
      .flatMap ( parseToTrees(pipeline, _) )
      .toList
    val synopsis = trees.map { case (tree, graph) =>
          tree.pennString() ++
          graph.toString
    }.reduce (_++"########\n"++_)
    /*
     * This does not work because it is too old. But it may help to know how to
     * get the associated data
    val tlp = new PennTreebankLanguagePack()
    val gsf = tlp.grammaticalStructureFactory()
    val gs = gsf.newGrammaticalStructure(tree)
    val tdl = gs.typedDependenciesCCprocessed(true)
    Main.writeImage(tree, tdl, "example.png", 3)
    */
    
    /*println(trees
      //.map(handle_tree)
      // CONDITIONs are not being generated. That's a problem.
      // TODO: Find out why
      //.filter(_.main_kind == PhraseType.CONDITION)
      .map(_.toString)
      .reduce(_++_))*/
    
    // This is more a Java idiom but Scala doesn't seem to have an equivalent 
    Files.write(Paths.get("ABRS_Trees"), synopsis.getBytes(StandardCharsets.UTF_8))
  }
  
  def handle_tree(subject : Tree) : Phrase = {
    if (subject.isLeaf())
      classify_word(subject.value())
    else
      subject.children().map(handle_tree).reduce(merge_phrases)
  }
  
  /**
   * This is an extremely simple hack to demonstrate what we are looking for.
   * Given a word, decide what class it fits into, which is one of:
   * Ailment (Diabetes)
   * Test (A1C)
   * Value (1.1)
   * Operator (>, <, =)
   * Word (everything else)
   */
  def classify_word(word : String) : Phrase = {
    val kinds = new Array[Double](PhraseType.values().length)
    if (word.equals("A1C")) {
      kinds(PhraseType.TEST.ordinal()) = 1
    } else if (word.equalsIgnoreCase("diabetes")) {
      kinds(PhraseType.AILMENT.ordinal()) = 1
    } else if (word.matches("[<>=-–]")) {
      kinds(PhraseType.OPERATOR.ordinal()) = 1
    } else if (word.matches("[0-9.,-]+")) {
      kinds(PhraseType.VALUE.ordinal()) = 1
    } else {
      kinds(PhraseType.WORD.ordinal()) = 1
    }
    
    new Phrase(word, kinds)
  }
  
  def merge_phrases(left : Phrase, right : Phrase) : Phrase = {
    val phrases = List(left, right)
    val kinds = left.kinds zip right.kinds map { case (x, y) => Math.max(x, y) }
    
    
    
    if (kinds(PhraseType.VALUE.ordinal()) == 1 
        && kinds(PhraseType.OPERATOR.ordinal()) == 1
        && kinds(PhraseType.TEST.ordinal()) == 1) {
      kinds(PhraseType.CONDITION.ordinal()) == 1
      if (kinds(PhraseType.AILMENT.ordinal()) == 1) {
        kinds(PhraseType.LINK.ordinal()) = 1
      }
    } 
    new Phrase(left.content ++ " " ++ right.content, kinds)
  }
}
