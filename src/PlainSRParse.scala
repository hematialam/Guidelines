
import java.io.File
import java.io.FileInputStream
import java.io.FileNotFoundException
import java.io.IOException
import java.io.InputStream
import java.util.ArrayList
import java.util.Map
import java.util.Properties
import scala.collection.JavaConverters._
import scala.collection.mutable.Buffer
import java.nio.file.{Paths, Files}
import java.nio.charset.StandardCharsets

import edu.stanford.nlp.dcoref.CorefChain;
import edu.stanford.nlp.dcoref.CorefCoreAnnotations.CorefChainAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.NamedEntityTagAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.PartOfSpeechAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.SentencesAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.TextAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.TokensAnnotation;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.semgraph.SemanticGraph;
import edu.stanford.nlp.semgraph.SemanticGraphCoreAnnotations.CollapsedCCProcessedDependenciesAnnotation;
import edu.stanford.nlp.trees.Tree;
import edu.stanford.nlp.trees.TreeCoreAnnotations.TreeAnnotation;
import edu.stanford.nlp.util.CoreMap;

object PlainSRParse extends App {
  
  def parseToTrees(pipeline: StanfordCoreNLP, text: String) : List[Tree] = {
      
      // create an empty Annotation just with the given text
      val document = new Annotation(text)
      
      // run all Annotators on this text
      pipeline.annotate(document)
      
      // these are all the sentences in this document
      // a CoreMap is essentially a Map that uses class objects as keys and has values with custom types
      val sentences = document.get(classOf[SentencesAnnotation]).asScala
      sentences.toList.map(sentence => {
        // this is the parse tree of the current sentence
        sentence.get(classOf[TreeAnnotation]);
      })
  }

  override def main(args: Array[String])= {
    // Boilerplate code to start the pipeline
    val props = new Properties()
    props.put("annotators", "tokenize, ssplit, pos, parse")
    props.put("parse.model", "edu/stanford/nlp/models/srparser/englishSR.ser.gz")
    val pipeline = new StanfordCoreNLP(props)
    
    // Run some sentences through the pipeline
    val trees = io.Source.fromFile("A1C Sentences", "utf-8")
      .getLines()
      .flatMap { text =>
        parseToTrees(pipeline, text)
      }.toList
    val trees_string= trees.map(_.toString)
      .reduce(_++"\n"++_)
    
    // This is more a Java idiom but Scala doesn't seem to have an equivalent 
    Files.write(Paths.get("A1C_Trees"), trees_string.getBytes(StandardCharsets.UTF_8))
  }
}

