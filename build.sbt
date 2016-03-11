lazy val commonSettings = Seq(
  organization := "binaryreader",
  version := "0.1.0",
  scalaVersion := "2.11.7"
)

val json4sJackson = "org.json4s" %% "json4s-jackson" % "3.2.10"

lazy val dependencySettings = Seq(
  libraryDependencies ++= Seq(
    "org.scalatest" % "scalatest_2.11" % "2.2.6" % "test",
    json4sJackson
  )
)

lazy val root = (project in file(".")).
  settings(commonSettings: _*).
  settings(dependencySettings: _*).
  settings(
    name := "BinaryReader",
    mainClass in assembly := Some("binaryreader.Main"),
    test in assembly := {}
  )
