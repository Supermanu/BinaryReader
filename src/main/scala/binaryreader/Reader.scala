package binaryreader

import java.nio.file.{Files, Paths}

/**
*  Created by manuel on 4/06/15.
*/

class Reader {
  def readFile(path: String): Array[Byte] = {
    try {
      Files.readAllBytes(Paths.get(path))
    } catch {
      case e: Throwable => {
        println(e.getMessage)
        Array.empty[Byte]
      }
    }
  }
}
