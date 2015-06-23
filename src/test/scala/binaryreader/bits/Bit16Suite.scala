package binaryreader.bits

import java.nio.ByteBuffer

import org.scalatest.FunSuite

/**
 * Created by manuel on 7/06/15.
 */

class Bit16Suite extends FunSuite {

  test ("convert 2 bytes to short") {
    val b1 = ByteBuffer.allocate(2)
    val b2 = ByteBuffer.allocate(2)
    val b3 = ByteBuffer.allocate(2)

    val bits1 = new Bit16(b1.putShort(266).array())
    val bits2 = new Bit16(b2.putShort(-265).array())
    val bits3 = new Bit16(b3.putShort(-32767).array())

    assert(bits1.toSInt == 266)
    assert(bits1.toUInt == 266)
    assert(bits2.toUInt == 65271)
    assert(bits2.toSInt == -265)
    assert(bits3.toUInt == 32769)
    assert(bits3.toSInt == -32767)
  }
}
