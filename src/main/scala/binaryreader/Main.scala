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

import binaryreader.model.NwnModel
import org.json4s.jackson.JsonMethods._

object Main extends App {
  def modelToJson(path: String) = {
    val model = new NwnModel(path)
    model.fs.readBinaryFile()
    println(compact(model.fs.getJsonField))
  }

  def convertBytes(b: String): Unit = {
    if (b.length % 2 == 1) {
      println("Need hexadecimal value with an odd number of character.")
      return
    }

    val bytes = new Array[Byte](b.length / 2)
    bytes.zipWithIndex.map(x => Integer.parseInt(b.subSequence(x._2 * 2, x._2 * 2 + 2).toString, 16).toByte)
  }

  override def main (args: Array[String]) {
    var argPos = 0
    while (argPos < args.length) {
      args(argPos) match {
        case "--json" | "-j" => {
          if (argPos == args.length - 1) println("model path missing")
          else {
            argPos += 1
            modelToJson(args(argPos))
          }
        }
        case "--convert" | "-c" => {
          if (argPos == args.length - 1) println("no bytes to convert")
          else {
            argPos += 1
            modelToJson(args(argPos))
          }
        }
        case _ => println("Unknown parameter")
      }
      argPos += 1
    }
  }
}
