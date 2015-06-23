package binaryreader.structure

import binaryreader.bits.Bits
import binaryreader.bits.Endianness._
import binaryreader.Data
import org.json4s.JsonDSL._
import org.json4s._

/**
 * Created by manuel on 7/06/15.
 */

abstract class Field[B] (val endianness: Endianness) extends DataStructure {
  var bits: Option[Bits] = None

  def setBits(bytes: Array[Byte])
  def getValue: B

  def getHex: String = {
    if (length > 0) bits.get.toHex
    else "Empty field"
  }

  override def propagate(pos: Int): Int = {
    val posCopy = pos
    currentPosition = posCopy
    setBits(Data.getData(posCopy, posCopy + length))
    length

  }

}
