package binaryreader.structure

import binaryreader.bits.{Bit8, Bit16, Bit32}
import binaryreader.bits.Endianness._
import org.json4s._
import org.json4s.JsonDSL._

/**
 * Created by manuel on 9/06/15.
 */

object LFloat32 {
  def apply(name: String) : FloatField = new FloatField(name, LittleEndian, 4)
}

class FloatField(val name: String, endianness: Endianness, val length: Int) extends Field[Float](endianness) {
  def setBits(bytes: Array[Byte]): Unit = {
    length match {
//      case 8 => bits = Some(new Bit64(bytes, endianness))
      case 4 => bits = Some(new Bit32(bytes, endianness))
      case 2 => bits = Some(new Bit16(bytes, endianness))
    }
  }

  def getValue: Float = bits.get.toFloat

  override def copy(name: String): FloatField = {
    new FloatField(name: String, endianness, length)
  }

  override def debugString(level: Int): Unit = {
    println("\t"* level + name + " Float: " + getValue + " Position: " + position)
  }

  override def getJsonField: JObject = {
    JField("name", name) ~ JField("position", currentPosition) ~ JField("length", length) ~ JField("value", getValue)
  }

}
