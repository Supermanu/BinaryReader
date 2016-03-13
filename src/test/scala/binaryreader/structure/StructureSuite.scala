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

import org.scalatest.FunSuite
import binaryreader.bits.Endianness._

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
      new Pointer("",s => s.children.length + 2, new UInt("", LittleEndian, 4))
    )
    fs.readBinaryFile()
    fs.debugString(0)
    fs.getJsonField
  }
}
