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

import java.nio.{ByteOrder, ByteBuffer}

import org.scalatest.FunSuite
import binaryreader.bits.Endianness._

class Bit32Suite extends FunSuite {
  test ("convert 4 bytes to Int") {
    val b = ByteBuffer.allocate(4)
    val b1 = ByteBuffer.allocate(4)
    val b2 = ByteBuffer.allocate(4)

    val bits1 = new Bit32(b.putInt(26).array())
    val bits2 = new Bit32(b1.putInt(-226).array())
    val bits3 = new Bit32(b2.putInt(50226).array())

    assert(bits1.toSInt == 26)
    assert(bits1.toUInt == 26)
    assert(bits2.toSInt == -226)
    assert(bits3.toUInt == 50226)
  }

  test ("convert 4 bytes to float with LE") {
    val b = ByteBuffer.allocate(4).order(ByteOrder.LITTLE_ENDIAN)

    val bits1 = new Bit32(b.putFloat(5.2F).array(), LittleEndian)
    assert(bits1.toFloat == 5.2F)
  }

  test ("convert 4 bytes to float with BE") {
    val b = ByteBuffer.allocate(4).order(ByteOrder.BIG_ENDIAN)

    val bits1 = new Bit32(b.putFloat(5.2F).array(), BigEndian)
    assert(bits1.toFloat == 5.2F)
  }

  test ("cuac") {
    val b = ByteBuffer.allocate(4).order(ByteOrder.LITTLE_ENDIAN)

    val bits1 = new Bit32(b.putFloat(-3.0F).array(), LittleEndian)
    println(bits1.toHex)
    assert(bits1.toFloat == -3.0F)
  }
}
