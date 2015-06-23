/** **************************************************************
  * Licensed to Datalayer (http://datalayer.io) under one or     *
  * more contributor license agreements.  See the NOTICE file    *
  * distributed with this work for additional information        *
  * regarding copyright ownership. Datalayer licenses this file  *
  * to you under the Apache License, Version 2.0 (the            *
  * "License"); you may not use this file except in compliance   *
  * with the License.  You may obtain a copy of the License at   *
  * *
  * http://www.apache.org/licenses/LICENSE-2.0                 *
  * *
  * Unless required by applicable law or agreed to in writing,   *
  * software distributed under the License is distributed on an  *
  * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY       *
  * KIND, either express or implied.  See the License for the    *
  * specific language governing permissions and limitations      *
  * under the License.                                           *
  * ***************************************************************/
package binaryreader.structure

import binaryreader.bits.BitField
import binaryreader.bits.Endianness._
import org.json4s.JsonDSL._
import org.json4s._

object Text {
  def apply(name: String, length: Int) = new Text(name, length)
}


class Text(val name: String, val length: Int) extends Field[String](BigEndian) {

  def getValue: String = bits.get.toChar

  def setBits(bytes: Array[Byte]): Unit = {
    bits = Some(new BitField (bytes, endianness))
  }

  override def copy(name: String): Text = {
    new Text(name: String, length)
  }

  override def debugString(level: Int): Unit = {
    println("\t"* level + name + " Text: " + getValue + " Position: " + position + " Length: " + length)
  }

  override def getJsonField: JObject = {
    JField("name", name) ~ JField("position", currentPosition) ~ JField("length", length) ~ JField("value", getValue)
  }

}
