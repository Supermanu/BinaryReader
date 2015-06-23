package binaryreader.structure

/**
 * Created by manuel on 9/06/15.
 */

abstract class DataStructure extends Structure {
  lazy val children = Vector.empty[Structure]
}
