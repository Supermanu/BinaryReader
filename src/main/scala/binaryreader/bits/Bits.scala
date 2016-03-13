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

import Endianness._

/**
 * Created by manuel on 4/06/15.
 */

object Bits {
  def isBitSet(bits: Bits)(bit: Int): Boolean = {
    val b = {
      if (bits.endianness == BigEndian) bits.bytes.reverse
      else bits.bytes
    }

    val byte = b(bit / 8)
    ((byte >> (bit % 8)) & 1) == 1
  }

}

trait Bits {
  protected val bytes: Array[Byte]
  val endianness: Endianness

  def length = bytes.length

  def getBytes: Array[Byte] = bytes

  def toBooleans: Vector[Boolean] = {
    0 to (8 * bytes.length - 1) map Bits.isBitSet(this) toVector
  }

  def toHex: String = {
    "0x" +bytes.map("%02x".format(_)).mkString("").toUpperCase
  }

  def byteToBooleans(byte: Byte): Vector[Boolean] = {
    (0 to 7).reverse.map{ p =>
      ((byte >> (p % 8)) & 1) == 1
    }.toVector
  }

  def booleansToInt(bools: Vector[Boolean], bitPlace: Int): Int = {
    val index = bools.indices.reverse

    bools.zip(index).map{ x =>
//      println((x._1, x._2 + bitPlace))
      if (x._1) Math.pow(2.0, x._2 + bitPlace)
      else 0.0
    }.sum.toInt
  }

  def toFloat: Float = ???

  def toChar: String = {
    new String(bytes.takeWhile(_ != 0))
  }

  def toSInt: Int = {
    val bools = {
      if (endianness == BigEndian) bytes.map(byteToBooleans)
      else bytes.map(byteToBooleans).reverse
    }

    val sign = bools.head.head

    def reverseSign(in: Boolean): Boolean = {
      if (sign) !in else in
    }

    val unsignedPart = bools.slice(1, bools.length).zipWithIndex.map{ b =>
      booleansToInt(b._1.map(reverseSign), (bools.length - b._2 - 2) * 8)
    }.sum

    val signedPart = booleansToInt(bools.head.map(reverseSign).slice(0,8), (bools.length - 1) * 8 )

    (if (sign) -1 else 1) * (unsignedPart + signedPart + (if (sign) 1 else 0))
  }

  def toUInt: Int = {
    val bools = {
      if (endianness == BigEndian) bytes.map(byteToBooleans)
      else bytes.map(byteToBooleans).reverse
    }

//    bools.foreach(println)
    bools.zipWithIndex.map{ x =>
      booleansToInt(x._1, (bools.length - x._2 - 1) * 8)
    }.sum
  }

}
