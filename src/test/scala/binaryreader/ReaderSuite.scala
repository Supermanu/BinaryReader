package binaryreader

import org.scalatest._

/**
 * Created by manuel on 4/06/15.
 */

class ReaderSuite extends FunSuite {
  test("Read file") {
    val reader = new Reader
    val bytes = reader.readFile("/home/manuel/dev/xoreos/sometilemodel/tcn01_a01_01.mdl")
    assert(bytes.length == 60068)
  }

}
