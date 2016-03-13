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

object If {
  def apply(name: String = "If", cond: (Structure) => Boolean, struct: Structure) = new If(name, cond, struct)
}

class If(val name: String = "If", cond: (Structure) => Boolean, struct: Structure) extends ContainerStructure(Seq(struct)) {

  lazy val condResult = cond(getParent)

  def copy(name: String): If = new If(name, cond, struct.copy())

  override def propagate(pos: Int): Int = {
    if (!condResult) return 0

    super.propagate(pos)
  }

  override def debugString(level: Int): Unit = {
    println("\t" * level + "If (" + name + "): " + condResult)
    if (condResult) children.foreach(s => s.debugString(level + 1))
  }
}
