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
