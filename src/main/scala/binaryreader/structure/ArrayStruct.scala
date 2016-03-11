package binaryreader.structure

import org.json4s._

import org.json4s.JsonDSL._

/**
 * Created by manuel on 9/06/15.
 */

object Array3 {
  def apply(name: String, struct: Structure) = {
    new ArrayStruct(name, s => Vector(struct.copy(struct.name + "_0"), struct.copy(struct.name + "_1"), struct.copy(struct.name + "_2")), 3)
  }
}

object ArrayStruct {
  def apply(name: String, struct: (Structure) => Vector[Structure], size: Int) = {
    new ArrayStruct(name, struct, size = size)
  }

  def apply(name: String, struct: Structure, size: Int) = {
    new ArrayStructConst(name, struct, size)
  }

  def apply(name: String, struct: Structure, fctS: (Structure) => Int) = {
    new ArrayStructConst(name, struct, sizeFunc = fctS)
  }

  def apply(name: String, struct: (Structure) => Vector[Structure], fctS: (Structure) => Int) = {
    new ArrayStruct(name, struct, -1, fctS)
  }
}

class ArrayStructConst(val name: String, struct: Structure, size: Int = - 1, sizeFunc: (Structure) => Int = s => -1) extends DataStructure {

  lazy val arraySize = {
    if (size == -1) sizeFunc(getParent)
    else size
  }

  def copy(name: String): ArrayStructConst = new ArrayStructConst(name, struct, size, sizeFunc)

  lazy val length = struct.length * arraySize

  override def propagate(pos: Int): Int = {
    super.propagate(pos)
  }

  override lazy val children = {
    val c = new Array[Structure](arraySize).zipWithIndex.map(x => struct.copy(name + "_" + x._2)).toVector
    c.foreach(s => s.parent = Some(this))
    c
  }


}

class ArrayStruct(val name: String,  struct: (Structure) => Vector[Structure], size: Int = - 1, sizeFunc: (Structure) => Int = s => -1) extends DataStructure {

  lazy val arraySize = {
    if (size == -1) sizeFunc(getParent)
    else size
  }

  def copy(name: String): ArrayStruct = new ArrayStruct(name, struct, size, sizeFunc)

  lazy val length = struct(getParent).length * arraySize
  override lazy val children = {
    val c = struct(getParent)
    c.foreach(s => s.parent = Some(this))
    c
  }

}
