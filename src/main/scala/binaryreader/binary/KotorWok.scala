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

package binaryreader.binary

import binaryreader.structure._

import scala.collection.mutable.ArrayBuffer
import scala.tools.nsc.transform.Mixin

class KotorWok(val path: String) extends Binary {
  private val vertex = Struct("vertex", Array3("position", LFloat32("xyz")))
  private val face   = Struct("face", Array3("indices", ULInt32("index")))

  private val AABBStatic : Struct= Struct("AABBStatic",
    IntValue("AABBOffset", s => s.getParent.getFieldInt("AABBOffset")),
    Array3("min", LFloat32("xyz")),
    Array3("max", LFloat32("xyz")),
    SLInt32("leaf_face"),
    Padding("unknown 4", 4),
    ULInt32("plane"),
    SLInt32("leftOffset"),
    SLInt32("rightOffset")
  )

  private val AABBData = Struct( "AABBData",
    IntValue("AABBOffset", s => s.getParent.getFieldInt("AABBOffset")),
    Pointer("toAABB", s => s.getValue("AABBOffset"),
      RecursiveStruct("AABBrec",
        s => s.getFieldInt("leftOffset") > 0, s => s.getFieldInt("leftOffset") * 44 + s.getParent.getValue("AABBOffset"),
        s => s.getFieldInt("rightOffset") > 0, s => s.getFieldInt("rightOffset") * 44 + s.getParent.getValue("AABBOffset"),
        AABBStatic))
  )

  val fs = new FileStructure("", path,
    Text("WokVersion", 8),
    ULInt32("WalkmeshType"),
    Padding("Reserved", 48),
    Array3("position", LFloat32("xyz")),
    ULInt32("VerticesCount"),
    ULInt32("vertexOffset"),
    ULInt32("FacesCount"),
    ULInt32("faceOffset"),
    ULInt32("WalktypesOffset"),
    ULInt32("NormalizedInvertedNormalsOffset"),
    ULInt32("FacePlanesCoefficientOffset"),
    ULInt32("AABBCount"),
    ULInt32("AABBOffset"),
    ULInt32("Unknown"),
    ULInt32("WalkableFacesEdgesAdjacencyMatrixCount"),
    ULInt32("WalkableFacesEdgesAdjacencyMatrixCountOffset"),
    ULInt32("PerimetricEdgesCount"),
    ULInt32("PerimetricEdgesOffset"),
    ULInt32("PerimetricCount"),
    ULInt32("PerimetricOffset"),
    Pointer("toVertices", s => s.getFieldInt("vertexOffset"),
      ArrayStruct("vertices", vertex, (s: Structure) => s.getFieldInt("VerticesCount"))),
    Pointer("toFaces", s => s.getFieldInt("faceOffset"),
      ArrayStruct("faces", face, (s: Structure) => s.getFieldInt("FacesCount"))),
    Pointer("toWalkTypes", s => s.getFieldInt("WalktypesOffset"),
      ArrayStruct("walkTypes", ULInt32("walktype"), (s: Structure) => s.getFieldInt("FacesCount"))),
    Pointer("toAABB", s => s.getFieldInt("AABBOffset"),
      RecursiveStruct("AABBrec",
        s => s.getFieldInt("leftOffset") > 0, s => s.getFieldInt("leftOffset") * 44 + s.getParent.getFieldInt("AABBOffset"),
        s => s.getFieldInt("rightOffset") > 0, s => s.getFieldInt("rightOffset") * 44 + s.getParent.getFieldInt("AABBOffset"),
        AABBStatic))
  )
}
