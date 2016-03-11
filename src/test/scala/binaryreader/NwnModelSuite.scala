package binaryreader

import binaryreader.model.NwnModel
import binaryreader.structure._

import org.scalatest.FunSuite
import org.json4s._
import org.json4s.jackson.JsonMethods._
import org.json4s.JsonDSL._

/**
 * Created by manuel on 9/06/15.
 */

class NwnModelSuite extends FunSuite {
  test("Processing...") {
    println("processing")
    val path = "/home/manuel/Developpement/Scala/BinaryReader/data/tcn01_a01_01.mdl"
    val model = new NwnModel(path)
    model.fs.readBinaryFile()
    println(pretty(model.fs.getJsonField))

    println("----------------------" * 3)
    model.fs.debugString()

  }
}
