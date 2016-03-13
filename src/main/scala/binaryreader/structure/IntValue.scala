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

import scala.collection.mutable.ArrayBuffer


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

  val children = ArrayBuffer.empty[Structure]
  val length = 0

  def copy(name: String) : IntValue = {
    new IntValue(name, valueIn, f)
  }

  override def propagate(pos: Int): Int = 0

  override def debugString(level: Int): Unit = {
    println("\t"* level + name + " Value: " + value)
  }
}
