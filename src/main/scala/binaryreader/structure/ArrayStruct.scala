/*This file is part of BinaryReader.

BinaryReader is free software: you can redistribute it and/or modify
  it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

BinaryReader is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
  GNU General Public License for more details.

You should have received a copy of the GNU General Public License
  along with BinaryReader.  If not, see <http://www.gnu.org/licenses/>.*/

package binaryreader.structure

import org.json4s._

import org.json4s.JsonDSL._

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
