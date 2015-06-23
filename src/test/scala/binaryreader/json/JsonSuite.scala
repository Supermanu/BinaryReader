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
package binaryreader.json

import org.json4s._
import org.json4s.jackson.JsonMethods._
import org.json4s.JsonDSL._

import org.scalatest.FunSuite

class JsonSuite extends FunSuite {

  test ("simple json test") {
    val field = JField("entry_1", JInt(5))
    val bigField = JField("container", JArray(List(field, JInt(9))))

    println(pretty(render(field ~ field.copy("entry_2"))))
  }

}
