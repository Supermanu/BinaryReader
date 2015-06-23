package binaryreader.structure

import org.scalatest.FunSuite
import binaryreader.bits.Endianness._

/**
 * Created by manuel on 7/06/15.
 */

class StructureSuite extends FunSuite {
  test ("Test simple Field in Structure") {
    val fs = new FileStructure("","/home/manuel/dev/xoreos/sometilemodel/tcn01_a01_01.mdl",
      new UInt("", LittleEndian, 4),
      new UInt("", LittleEndian, 4)
    )

    fs.readBinaryFile()
    fs.debugString(0)
    println(fs.getJsonField)
    assert(fs.children.length == 2)
    assert(fs.children.map(_.asInstanceOf[UInt].getValue).apply(1) == 31508)
  }

  test ("Test Pointer in Structure") {
    val fs = new FileStructure("", "/home/manuel/dev/xoreos/sometilemodel/tcn01_a01_01.mdl",
      new Pointer("", 4, new UInt("", LittleEndian,4)),
      new UInt("", LittleEndian,4),
      new UInt("", LittleEndian,4)
    )

    fs.readBinaryFile()
    fs.debugString(0)
    fs.getJsonField

    assert(fs.children.size == 3)
    assert(fs.children(1).asInstanceOf[UInt].getValue == 0)
  }

  test ("Test ArrayF in Structure") {
    val fs = new FileStructure("", "/home/manuel/dev/xoreos/sometilemodel/tcn01_a01_01.mdl",
      ArrayStruct("", new UInt("", LittleEndian, 4), 2),
      new UInt("", LittleEndian,4)
    )

    fs.readBinaryFile()
    fs.debugString(0)
    fs.getJsonField
  }

  test ("Test Arrayf in Structure with function arg") {
    val fs = new FileStructure("", "/home/manuel/dev/xoreos/sometilemodel/tcn01_a01_01.mdl",
      ArrayStruct("", new UInt("", LittleEndian, 4), 2),
      new UInt("", LittleEndian,4)
    )

    fs.readBinaryFile()
    fs.debugString(0)
    fs.getJsonField
  }

  test ("Test recursive call") {
    val fs = new FileStructure("", "/home/manuel/dev/xoreos/sometilemodel/tcn01_a01_01.mdl",
      ArrayStruct("", new UInt("", LittleEndian, 4), 2),
      new Pointer("", s => s.children.length + 2, new UInt("", LittleEndian, 4))
    )

    fs.readBinaryFile()
    fs.debugString(0)
    fs.getJsonField
  }
}
