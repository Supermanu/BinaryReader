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

package binaryreader.structure

import binaryreader.{Data, Reader}

/**
 * Created by manuel on 7/06/15.
 */

class FileStructure(val name: String, path: String, struct: Structure*) extends ContainerStructure(struct) {
  val reader = new Reader
  Data.setData(reader.readFile(path))

  children.foreach(c => c.parent = Some(this))

  override lazy val position = 0

  def readBinaryFile(): Unit = {
    propagate(position)
  }

//  def copy(name: String): FileStructure = new FileStructure(name, path, struct.map(_.copy()))
  def copy(name: String): FileStructure = ???
}
