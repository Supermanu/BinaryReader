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

package binaryreader.bits

import binaryreader.Reader
import org.scalatest.FunSuite

/**
 * Created by manuel on 4/06/15.
 */
class Bit8Suite extends FunSuite {
  test ("byte to booleans") {
    val reader = new Reader
    val bytes = reader.readFile("/home/manuel/dev/xoreos/sometilemodel/tcn01_a01_01.mdl")
    val byte = new Bit8(bytes(0))

    assert((0 to 7 map Bits.isBitSet(byte)) == (0 to 7).map(x => false))
  }

  test ("convert byte to integer") {
    val bits1 = new Bit8(26.toByte)
    val bits2 = new Bit8(-26.toByte)
    val bits3 = new Bit8(135.toByte)

//    println(bits1.toSInt)
//    println("bits2")
//    println(bits2.toSInt)

    assert(bits1.toSInt == 26)
    assert(bits1.toUInt == 26)
    assert(bits2.toUInt == 230)
    assert(bits2.toSInt == -26)
    assert(bits3.toUInt == 135)
    assert(bits3.toSInt == -121)
  }

}
