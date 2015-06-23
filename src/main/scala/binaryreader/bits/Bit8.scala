package binaryreader.bits

import binaryreader.bits.Endianness._
/**
 * Created by manuel on 4/06/15.
 */

class Bit8(byte: Byte, val endianness: Endianness = BigEndian) extends Bits {
  val bytes = Array(byte)
}
