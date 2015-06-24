package edu.uncc.cci.text

import edu.stanford.nlp.trees.Tree
import java.util.EnumMap

case class Phrase(val content: String, val kinds: Array[Double]) {
  
  def main_kind = {
    kinds.zip(PhraseType.values).maxBy(_._1)._2
  }
  override def toString = {
    s"Phrase( ${content}, a ${main_kind})"
  }
}