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
