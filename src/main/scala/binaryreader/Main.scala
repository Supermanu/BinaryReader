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
