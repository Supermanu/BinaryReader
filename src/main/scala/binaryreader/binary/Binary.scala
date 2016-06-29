package binaryreader.binary

import binaryreader.structure.FileStructure

/**
  * Created by manuel on 25/06/16.
  */
abstract class Binary {
  val path: String
  val fs: FileStructure
}
