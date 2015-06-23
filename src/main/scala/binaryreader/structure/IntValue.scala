package binaryreader.structure

import org.json4s._
import org.json4s.JsonDSL._

/**
 * Created by manuel on 9/06/15.
 */

object IntValue {
  def apply(name: String, value: Int) = new IntValue(name, value)
  def apply(name: String, f: (Structure) => Int) = new IntValue(name, f)
}

class IntValue(val name: String, valueIn: Int = -1, f: (Structure) => Int = s => -1) extends Structure {

  def this(valueName: String, fct: (Structure) => Int) = {
    this(valueName, f = fct)
  }

  lazy val value: Int = {
    if (valueIn == -1) f(getParent)
    else valueIn
  }

  val children = Vector.empty[Structure]
  val length = 0

  def copy(name: String) : IntValue = {
    new IntValue(name, valueIn, f)
  }

  override def propagate(pos: Int): Int = 0

  override def debugString(level: Int): Unit = {
    println("\t"* level + name + " Value: " + value)
  }
}
