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

import java.nio.file.{Files, Paths}

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
