package binaryreader.structure

import org.json4s._
import org.json4s.JsonDSL._

/**
 * Created by manuel on 9/06/15.
 */
object Struct {
  def apply(name: String, struct: Structure*) = new Struct(name, struct)
}

class Struct(val name: String, struct: Seq[Structure]) extends Structure {
  val children = struct.toVector
  children.foreach(s => s.parent = Some(this))
  lazy val length = children.map(_.length).sum

  def rename(newName: String): Struct = new Struct(newName, struct.map(_.copy()))

  def copy(name: String): Struct = {
    val newStruct = struct.map(s => s.copy(s.name))
    new Struct(name, newStruct)
  }

  override def debugString(level: Int = 0): Unit = {
    println("\t" * level + "Struct: " + name)
    children.foreach(s => s.debugString(level + 1))
  }
}
