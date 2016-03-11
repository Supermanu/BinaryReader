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

/**
 * Created by manuel on 9/06/15.
 */
import binaryreader.bits._
import binaryreader.bits.Endianness._

/**
 * Created by manuel on 9/06/15.
 */

object SLInt {
  def apply(name: String, length: Int): SInt = new SInt(name, LittleEndian, length)
}

object SBInt {
  def apply(name: String): SInt = new SInt(name, BigEndian, 1)
}

object SLInt8 {
  def apply(name: String): SInt = new SInt(name, LittleEndian, 1)
}

object SBInt8 {
  def apply(name: String): SInt = new SInt(name, BigEndian, 1)
}

object SLInt16 {
  def apply(name: String): SInt = new SInt(name, LittleEndian, 2)
}

object SBInt16 {
  def apply(name: String): SInt = new SInt(name, BigEndian, 1)
}

object SLInt32 {
  def apply(name: String): SInt = new SInt(name, LittleEndian, 4)
}

object SBInt32 {
  def apply(name: String): SInt = new SInt(name, BigEndian, 1)
}

class SInt(val name: String, endianness: Endianness, val length: Int)  extends Field[Int](endianness) {

  def setBits(bytes: Array[Byte]): Unit = {
    length match {
      case 4 => bits = Some(new Bit32(bytes, endianness))
      case 2 => bits = Some(new Bit16(bytes, endianness))
      case 1 => bits = Some(new Bit8 (bytes.head, endianness))
    }
  }

  def getValue: Int = bits.get.toSInt

  override def copy(name: String): SInt = {
    new SInt(name: String, endianness, length)
  }

  override def debugString(level: Int): Unit = {
    println("\t"* level + name + " SInt: " + getValue + " Position: " + position)
  }

}
