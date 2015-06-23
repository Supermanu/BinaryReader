package binaryreader.structure

/**
 * Created by manuel on 9/06/15.
 */

object Padding {
  def apply(name: String = "Padding", size: Int): Padding = new Padding(name, size)
}

class Padding(val name: String = "Padding", val size: Int) extends DataStructure {
  val length = size

  def copy(name: String): Padding = new Padding(name, size)

  override def propagate(pos: Int): Int = {
    currentPosition = pos
    size
  }

  override def debugString(level: Int): Unit = {
    println("\t"* level + name + ": " + size + " Position: " + position)
  }
}
