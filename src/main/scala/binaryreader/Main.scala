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

import binaryreader.binary._
import org.json4s.jackson.JsonMethods._

object Main extends App {
  def modelToJson(path: String, game: String="kotor") = {
    val model: Binary = game match {
      case "nwn" => new NwnModel(path)
      case "kotor" => new KotorWok(path)
    }

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
    var argMap: Map[String, String] = Map.empty
    while (argPos < args.length) {
      if (args(argPos).startsWith("--") || args(argPos).startsWith("-")) {
        if (argPos == args.length - 1) {
          println("Value missing for " + args(argPos))
        } else {
          argMap = argMap + (args(argPos) -> args(argPos + 1))
        }
      }
      argPos += 1
    }

    // Default game: nwn
    val game: String = argMap.getOrElse("-g", argMap.getOrElse("--game", "nwn"))

    // Convert to json
    if (argMap.contains("-j") || argMap.contains("--json")) {
      modelToJson(argMap.getOrElse[String]("-j", argMap.get("--json").get), game)
    }
  }
}
