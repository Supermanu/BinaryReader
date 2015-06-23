package binaryreader.structure

import org.json4s._
import org.json4s.JsonDSL._

/**
 * Created by manuel on 9/06/15.
 */

object Pointer {
  def apply(name: String, start: Int, children: Structure*) = new Pointer(name, start, child = children)
  def apply(name: String, startFct: (Structure) => Int, children: Structure*) = new Pointer(name, startFunc = startFct, child = children)
}

class Pointer(val name: String, start: Int = - 1, startFunc: (Structure) => Int = s => -1, child: Seq[Structure]) extends ContainerStructure(child) {
  override lazy val position = {
    if (start == -1) startFunc(getParent)
    else start
  }

  def copy(name: String): Pointer = new Pointer(name, start, startFunc, child.map(_.copy()))

  def this(name: String, startP: Int, childSeq: Structure*) = {
    this(name, startP, _ => -1, childSeq)
  }

  def this(name: String, startFuncP: (Structure) => Int, childSeq: Structure*) = {
    this(name, -1, startFuncP, childSeq)
  }

  override def propagate(pos: Int): Int = {
    super.propagate(position)
    0
  }
  override def debugString(level: Int): Unit = {
    println("\t"* level + name + " Pointer to: " + position)
    children.foreach(s => s.debugString(level))
  }

  override def getJsonField: JObject = {
    JField("pointerTo", children.map(c => c.getJsonField))
  }
}
