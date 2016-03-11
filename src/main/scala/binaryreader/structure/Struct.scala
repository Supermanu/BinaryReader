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

/**
 * Created by manuel on 9/06/15.
 */
object Struct {
  def apply(name: String, struct: Structure*) = new Struct(name, struct)
}

class Struct(val name: String, struct: Seq[Structure]) extends Structure {
  val children = struct.toVector
  children.foreach(s => s.parent = Some(this))
  lazy val length = children.map(_.length).sum

  def rename(newName: String): Struct = new Struct(newName, struct.map(_.copy()))

  def copy(name: String): Struct = {
    val newStruct = struct.map(s => s.copy(s.name))
    new Struct(name, newStruct)
  }

  override def debugString(level: Int = 0): Unit = {
    println("\t" * level + "Struct: " + name)
    children.foreach(s => s.debugString(level + 1))
  }
}
