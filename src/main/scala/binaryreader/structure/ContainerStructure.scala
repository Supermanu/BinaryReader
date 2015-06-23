package binaryreader.structure

/**
 * Created by manuel on 9/06/15.
 */

abstract class ContainerStructure(struct: Seq[Structure]) extends Structure {
  val length = 0
  val children = struct.toVector
  children.foreach(c => c.parent = Some(this))
}
