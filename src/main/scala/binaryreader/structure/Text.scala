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

import binaryreader.bits.BitField
import binaryreader.bits.Endianness._
import org.json4s.JsonDSL._
import org.json4s._

object Text {
  def apply(name: String, length: Int) = new Text(name, length)
}


class Text(val name: String, val length: Int) extends Field[String](BigEndian) {

  def getValue: String = bits.get.toChar

  def setBits(bytes: Array[Byte]): Unit = {
    bits = Some(new BitField (bytes, endianness))
  }

  override def copy(name: String): Text = {
    new Text(name: String, length)
  }

  override def debugString(level: Int): Unit = {
    println("\t"* level + name + " Text: " + getValue + " Position: " + position + " Length: " + length)
  }

  override def getJsonField: JObject = {
    JField("name", name) ~ JField("position", currentPosition) ~ JField("length", length) ~ JField("value", getValue)
  }

}
