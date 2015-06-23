package binaryreader

/**
 * Created by manuel on 9/06/15.
 */

object Data {
  private var data: Array[Byte] = Array.empty[Byte]

  def setData(d: Array[Byte]): Unit = {
    data = d
  }

  def getData(start: Int, end: Int): Array[Byte] = {
    data.slice(start, end)
  }

  def length: Int = data.length
}
