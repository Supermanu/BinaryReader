package binaryreader.bits

import binaryreader.bits.Endianness._

/**
 * Created by manuel on 4/06/15.
 */

class Bit16(val bytes: Array[Byte], val endianness: Endianness = BigEndian) extends Bits {
  if (bytes.length != 2) throw new ClassCastException("Bit16 should be of length 2 and not " + bytes.length)

}
