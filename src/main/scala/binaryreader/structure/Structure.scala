package binaryreader.structure

/**
 * Created by manuel on 7/06/15.
 */

import org.json4s._
import org.json4s.jackson.JsonMethods._
import org.json4s.JsonDSL._

import scala.collection.mutable.ArrayBuffer

trait Structure {
  lazy val position: Int = currentPosition
  val name: String
  var currentPosition = -1
  val length: Int
  val children: ArrayBuffer[Structure]

  var parent: Option[Structure] = None

  def isLeaf: Boolean = children.isEmpty

  def copy(name: String = name): Structure

  def get(child: String): Structure = {
    val f = children.filter(s => s.name == child)
    if (f.isEmpty) {
      children.foreach(c => println(c.name))
      throw new NoSuchElementException(name  + " is unable to find " + child)
    }

    f.head
  }

  // Get recursively the parent
  def getParent: Structure = {
    if (parent.isEmpty) throw new NoSuchElementException(name + " has no parent")

    val p = parent.get
    p match {
      case s: If => s.getParent
      case s: ArrayStruct => s.getParent
      case s: Pointer => s.getParent
      //case s: RecursiveStruct => s.getParent
      case s => s
    }
  }

  def getField[A](name: String): Field[A] = {
    get(name).asInstanceOf[Field[A]]
  }

  def getFieldInt(name: String): Int = {
    get(name).asInstanceOf[Field[Int]].getValue
  }

  def getValue(fieldName: String): Int = {
    val f = children.filter(s => s.name == fieldName)
    if (f.isEmpty) throw new NoSuchElementException("Unable to find " + fieldName)

    f.head.asInstanceOf[IntValue].value
  }

  // Return the number of bytes processed
  def propagate(pos: Int): Int = {
    currentPosition = pos
    var currPos = pos
    //children.foreach(c => println(c.name))
    children.foreach { child =>
      child.parent = Some(this)
      currPos += child.propagate(currPos)
    }

    currPos - position
  }

  def getJsonField: JObject = {

    val c = children.filter{s =>
      s match {
        case i:If => i.condResult
        //case r:RecursiveStruct => r.condResult1
        case v:IntValue => false
        case _ => true
      }
//      if (!s.isInstanceOf[If]) true
//      else s.asInstanceOf[If].condResult
    }


    JField("name", name) ~ JField("position", currentPosition) ~ JField("length", length) ~ JField("children", c.map(c => c.getJsonField))
  }

  override def toString: String = {
    this.getClass.getCanonicalName
  }

  def debugString(level: Int = 0): Unit = {
    println("\t" * level + "Struct: " + name)
    children.foreach(s => s.debugString(level + 1))
  }

}
