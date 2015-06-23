/** **************************************************************
  * Licensed to Datalayer (http://datalayer.io) under one or     *
  * more contributor license agreements.  See the NOTICE file    *
  * distributed with this work for additional information        *
  * regarding copyright ownership. Datalayer licenses this file  *
  * to you under the Apache License, Version 2.0 (the            *
  * "License"); you may not use this file except in compliance   *
  * with the License.  You may obtain a copy of the License at   *
  * *
  * http://www.apache.org/licenses/LICENSE-2.0                 *
  * *
  * Unless required by applicable law or agreed to in writing,   *
  * software distributed under the License is distributed on an  *
  * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY       *
  * KIND, either express or implied.  See the License for the    *
  * specific language governing permissions and limitations      *
  * under the License.                                           *
  * ***************************************************************/
package binaryreader.structure

import org.json4s._
import org.json4s.JsonDSL._

object If {
  def apply(name: String = "If", cond: (Structure) => Boolean, struct: Structure) = new If(name, cond, struct)
}

class If(val name: String = "If", cond: (Structure) => Boolean, struct: Structure) extends ContainerStructure(Seq(struct)) {

  lazy val condResult = cond(getParent)

  def copy(name: String): If = new If(name, cond, struct.copy())

  override def propagate(pos: Int): Int = {
    if (!condResult) return 0

    super.propagate(pos)
  }

  override def debugString(level: Int): Unit = {
    println("\t" * level + "If (" + name + "): " + condResult)
    if (condResult) children.foreach(s => s.debugString(level + 1))
  }

}
