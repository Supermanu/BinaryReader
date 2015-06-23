package binaryreader.bits

import Endianness._
import java.nio.ByteBuffer
import java.nio.ByteOrder


/**
 * Created by manuel on 7/06/15.
 */
class Bit32(val bytes: Array[Byte], val endianness: Endianness = BigEndian) extends Bits {
  if (bytes.length != 4) throw new ClassCastException("Bit32 should be of length 4 and not " + bytes.length)

  override def toFloat: Float = {
    val bo = {
      if (endianness == BigEndian) ByteOrder.BIG_ENDIAN
      else ByteOrder.LITTLE_ENDIAN
    }
    val bb = ByteBuffer.wrap(bytes).order(bo)
    bb.getFloat
  }

  def toDouble: Double = {
    val bo = {
      if (endianness == BigEndian) ByteOrder.BIG_ENDIAN
      else ByteOrder.LITTLE_ENDIAN
    }

    val bb = ByteBuffer.wrap(bytes).order(bo)
    bb.getFloat.toDouble
  }
}
