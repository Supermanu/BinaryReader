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

package binaryreader

import binaryreader.binary.KotorWok
import binaryreader.structure._

import org.scalatest.FunSuite
import org.json4s._
import org.json4s.jackson.JsonMethods._
import org.json4s.JsonDSL._


class KotorModelSuite extends FunSuite {
  test("Processing...") {
    println("processing")
    val path = "/home/manuel/Developpement/Scala/BinaryReader/data/m02ad_01a.wok"
    val model = new KotorWok(path)
    model.fs.readBinaryFile()
    println(pretty(model.fs.getJsonField))

    println("----------------------" * 3)
    model.fs.debugString()

  }
}
