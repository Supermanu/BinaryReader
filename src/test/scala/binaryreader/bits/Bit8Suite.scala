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
