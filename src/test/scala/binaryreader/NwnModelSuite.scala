package binaryreader

import binaryreader.structure._

import org.scalatest.FunSuite
import org.json4s._
import org.json4s.jackson.JsonMethods._
import org.json4s.JsonDSL._

/**
 * Created by manuel on 9/06/15.
 */

class NwnModelSuite extends FunSuite {
  val arrayDef = Struct("arrayDef",
    ULInt32("offset"),
    ULInt32("usedCount"),
    ULInt32("allocatedcount") )

  val face = Struct("faces",
    Array3("position", LFloat32("xyz")),
    Padding("Plane distance", 4),
    ULInt32("walkability"),
    Padding(size = 6),
    Array3("indices", ULInt16("indice"))
  )

  val vertex = Array3("vertices", LFloat32("xyz"))

  val mesh = Struct("mesh",
    IntValue("offModelData", s => s.getParent.getValue("offModelData")),
    IntValue("offRawData", s => s.getParent.getValue("offRawData")),
    Padding(size = 8),
    arrayDef.rename("facesOffset"),
    Array3("boundingMin", LFloat32("xyz")),
    Array3("boundingMax", LFloat32("xyz")),
    LFloat32("radius"),
    Array3("pointsAverage", LFloat32("xyz")),
    Array3("ambient", LFloat32("val")),
    Array3("diffuse", LFloat32("val")),
    Array3("specular", LFloat32("val")),
    LFloat32("shininess"),
    ULInt32("shadow"),
    ULInt32("beaming"),
    ULInt32("render"),
    ULInt32("transparencyHint"),
    Padding("Unknown", 4),
    ArrayStruct("textures",Text("texture", 64), 4),
    ULInt32("tileFade"),
    Padding("Vextex indices", 12),
    Padding("Left over faces", 12),
    Padding("Vertex indices counts", 12),
    Padding("Vertex indices offsets", 12),
    Padding("Unknown", 8),
    ULInt8("triangleMode"),
    Padding("Padding + Unknown", 3+4),
    ULInt32("vertexOffset"),
    ULInt16("vertexCount"),
    ULInt16("textureCount"),
    ArrayStruct("textureVertexOffset", ULInt32("offset"), 4),
    ULInt32("normalOffset"),
    ULInt32("colorOffset"),
    ArrayStruct("textureAnimOffset", ULInt32("textureAnimOffset"), 6),
    ULInt8("lightMapped"),
    ULInt8("rotateTexture"),
    Padding("Padding", 2),
    Padding("Normal sum / 2", 4),
    Padding("Unknown", 4),
    Pointer("toFaces", s => s.get("facesOffset").getFieldInt("offset") + s.getValue("offModelData"),
      ArrayStruct("faces", face, (s: Structure) => s.get("facesOffset").getFieldInt("usedCount"))),
    Pointer("toFaces", s => s.getFieldInt("vertexOffset") + s.getValue("offModelData"),
      ArrayStruct("faces", face, (s: Structure) => s.getFieldInt("vertexCount")))
  // Texture ,

  )

  val AABBNode = Struct( "AABBNode",
    IntValue("offModelData", s => s.getParent.getValue("offModelData")),
    IntValue("offRawData", s => s.getParent.getValue("offRawData"))
//    mesh,
//    ULInt32("AABBOffset"),
//    Pointer(lambda ctx: ctx.offModelData + ctx.AABBOffset, AABB)
  )

  def getChildNodes(s: Structure): Vector[Structure] = {
    val offModelData = s.getValue("offModelData")
    s.get("toChildren").get("children").children.zipWithIndex.map{c =>
      val offset = c._1.asInstanceOf[UInt].getValue + offModelData
      Pointer("toChild_" + c._2, offset, node.rename("childNode_" + c._2))
    }
  }

  val node: Struct = Struct("node",
    IntValue("offModelData", s => s.getParent.getValue("offModelData")),
    IntValue("offRawData", s => s.getParent.getValue("offRawData")),
    Padding("Routine_00", 4),
    Padding("Routine_01", 4),
    Padding("Routine_02", 4),
    Padding("Routine_03", 4),
    Padding("Routine_04", 4),
    Padding("Routine_05", 4),
    ULInt32("inheritColorFlag"),
    ULInt32("partNumber"),
    Text("nodeName", 32),
    Padding(size = 8), // Parent pointer
    arrayDef.rename("childrenArrayDef"),
    Pointer("toChildren", s => s.get("childrenArrayDef").getFieldInt("offset") + s.getValue("offModelData"),
      ArrayStruct("children", ULInt32("child"), (s: Structure) => s.get("childrenArrayDef").getFieldInt("usedCount"))),
    arrayDef.rename("controllerKeyOffset"),
    arrayDef.rename("controllerDataOffset"),
    Pointer("toChildren", s => s.get("controllerDataOffset").getFieldInt("offset") + s.getValue("offModelData"),
      ArrayStruct("children", ULInt32("child"), (s: Structure) => s.get("controllerDataOffset").getFieldInt("usedCount"))),
    ULInt32("flags"),
    If("CheckIfMesh", s => s.getFieldInt("flags").&(32) == 32, mesh),
//    If("CheckIfAABBNode", s => s.getFieldInt("flags") == 2, ),
    IntValue("numberOfChildren", s => s.get("childrenArrayDef").getFieldInt("usedCount")),
    If("CheckChildrenNode", s => s.getValue("numberOfChildren") > 0,
      ArrayStruct("children", (s: Structure) => getChildNodes(s), (s: Structure) => s.getValue("numberOfChildren")))

  )

  val fs = new FileStructure("", "/home/manuel/dev/xoreos/sometilemodel/tcn01_a01_01.mdl",
    Padding(size = 4),
    ULInt32("sizeModelData"),
    ULInt32("sizeRawData"),
    IntValue("offModelData", 12),
    IntValue("offRawData", s => s.getValue("offModelData") + s.getFieldInt("sizeModelData")),
    Padding("routine_00", 4),
    Padding("routine_01", 4),
    Text("modelName", 64),
    ULInt32("nodeHeadPointer"),
    ULInt32("nodeCount"),
    Padding(size = 24 + 4), // Unknown + Reference count
    ULInt8("type"),
    Padding(size = 3+2), // Padding + Unknown
    ULInt8("classification"),
    ULInt8("fogged"),
    Padding(size = 4),
    arrayDef.rename("childrenArrayDef"),
    Padding(size = 4),
    Array3("boundingMin", LFloat32("p")),
    Array3("boundingMax", LFloat32("p")),
    LFloat32("radius"),
    LFloat32("animationScale"),
    Text("superModelName", 64),
    Pointer("toRootNode", s => s.getValue("offModelData") + s.getFieldInt("nodeHeadPointer"), node.rename("rootNode"))
  )


  test("Processing...") {
    println("processing")
    fs.readBinaryFile()

    println("----------------------" * 3)
    fs.debugString()

    println(pretty(fs.getJsonField))
  }
}
