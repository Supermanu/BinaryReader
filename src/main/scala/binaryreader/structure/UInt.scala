package binaryreader.structure

import binaryreader.bits._
import binaryreader.bits.Endianness._
import org.json4s._
import org.json4s.JsonDSL._

/**
 * Created by manuel on 9/06/15.
 */

object ULInt {
  def apply(name: String, length: Int): UInt = new UInt(name, LittleEndian, length)
}

object UBInt {
  def apply(name: String): UInt = new UInt(name, BigEndian, 1)
}

object ULInt8 {
  def apply(name: String): UInt = new UInt(name, LittleEndian, 1)
}

object UBInt8 {
  def apply(name: String): UInt = new UInt(name, BigEndian, 1)
}

object ULInt16 {
  def apply(name: String): UInt = new UInt(name, LittleEndian, 2)
}

object UBInt16 {
  def apply(name: String): UInt = new UInt(name, BigEndian, 1)
}

object ULInt32 {
  def apply(name: String): UInt = new UInt(name, LittleEndian, 4)
}

object UBInt32 {
  def apply(name: String): UInt = new UInt(name, BigEndian, 1)
}

class UInt(val name: String, endianness: Endianness, val length: Int)  extends Field[Int](endianness) {

  def setBits(bytes: Array[Byte]): Unit = {
    length match {
      case 4 => bits = Some(new Bit32(bytes, endianness))
      case 2 => bits = Some(new Bit16(bytes, endianness))
      case 1 => bits = Some(new Bit8 (bytes.head, endianness))
      case _ => throw new UnsupportedOperationException("Bad number of bytes")
    }
  }

  def getValue: Int = bits.get.toUInt

  override def copy(name: String): UInt = {
    new UInt(name: String, endianness, length)
  }

  override def debugString(level: Int): Unit = {
    println("\t"* level + name + " UInt: " + getValue + " Position: " + position)
  }

  override def getJsonField: JObject = {
    JField("name", name) ~ JField("position", position) ~ JField("length", length) ~ JField("value", getValue)
  }

}
