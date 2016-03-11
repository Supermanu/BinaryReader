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

import binaryreader.bits.{Bit8, Bit16, Bit32}
import binaryreader.bits.Endianness._
import org.json4s._
import org.json4s.JsonDSL._

/**
 * Created by manuel on 9/06/15.
 */

object LFloat32 {
  def apply(name: String) : FloatField = new FloatField(name, LittleEndian, 4)
}

class FloatField(val name: String, endianness: Endianness, val length: Int) extends Field[Float](endianness) {
  def setBits(bytes: Array[Byte]): Unit = {
    length match {
//      case 8 => bits = Some(new Bit64(bytes, endianness))
      case 4 => bits = Some(new Bit32(bytes, endianness))
      case 2 => bits = Some(new Bit16(bytes, endianness))
    }
  }

  def getValue: Float = bits.get.toFloat

  override def copy(name: String): FloatField = {
    new FloatField(name: String, endianness, length)
  }

  override def debugString(level: Int): Unit = {
    println("\t"* level + name + " Float: " + getValue + " Position: " + position)
  }

  override def getJsonField: JObject = {
    JField("name", name) ~ JField("position", currentPosition) ~ JField("length", length) ~ JField("value", getValue)
  }

}
