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

import binaryreader.bits.Bits
import binaryreader.bits.Endianness._
import binaryreader.Data
import org.json4s.JsonDSL._
import org.json4s._

/**
 * Created by manuel on 7/06/15.
 */

abstract class Field[B] (val endianness: Endianness) extends DataStructure {
  var bits: Option[Bits] = None

  def setBits(bytes: Array[Byte])
  def getValue: B

  def getHex: String = {
    if (length > 0) bits.get.toHex
    else "Empty field"
  }

  override def propagate(pos: Int): Int = {
    val posCopy = pos
    currentPosition = posCopy
    setBits(Data.getData(posCopy, posCopy + length))
    length
  }

}
