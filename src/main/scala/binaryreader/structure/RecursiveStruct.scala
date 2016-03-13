package binaryreader.structure

/**
  * Created by manuel on 12/03/16.
  */
object RecursiveStruct {
  def apply(name: String,
            cond1: (Structure) => Boolean, pointer1: (Structure) => Int,
            cond2: (Structure) => Boolean, pointer2: (Structure) => Int,
            staticStruct: Structure): RecursiveStruct = {
    new RecursiveStruct(name, cond1, pointer1, cond2, pointer2, staticStruct)
  }

  def apply(name: String,
            cond: (Structure) => Boolean, pointer: (Structure) => Int,
            staticStruct: Structure): RecursiveStruct = {
    new RecursiveStruct(name, cond, pointer, s => false, s => 0, staticStruct)
  }

}

class RecursiveStruct(val name: String,
                      cond1: (Structure) => Boolean, pointer1: (Structure) => Int,
                      cond2: (Structure) => Boolean, pointer2: (Structure) => Int,
                      staticStruct: Structure) extends ContainerStructure(Seq(staticStruct)) {
  lazy val condResult1 = cond1(children(0))
  lazy val condResult2 = cond2(children(0))

  def copy(name: String): RecursiveStruct = new RecursiveStruct(name, cond1, pointer1, cond2, pointer2, staticStruct.copy())

  override def propagate(pos: Int): Int = {
    val out = super.propagate(pos)

    if (condResult1) {
      val nextStruct = Pointer("nextStruct", pointer1(children(0)), new RecursiveStruct(name, cond1, pointer1, cond2, pointer2, staticStruct.copy()))
      nextStruct.parent = Option(this)
      nextStruct.propagate(out)
      children(0).children.append(nextStruct)
    }

    if (condResult2) {
      val nextStruct = Pointer("nextStruct", pointer2(children(0)), new RecursiveStruct(name, cond1, pointer1, cond2, pointer2, staticStruct.copy()))
      nextStruct.parent = Option(this)
      nextStruct.propagate(out)
      children(0).children.append(nextStruct)
    }
    out
  }

  override def debugString(level: Int): Unit = {
    println("\t" * level + "RecursiveStruct (" + name + "_1): " + condResult1)
    println("\t" * level + "RecursiveStruct (" + name + "_2): " + condResult2)
    children.foreach(s => s.debugString(level + 1))
  }

}