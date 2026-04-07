ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "3.8.3"

lazy val root = (project in file("."))
  .settings(
    name := "tpo2",
    Compile / sourceDirectories += baseDirectory.value / "src" / "main" / "scala",
    Test / sourceDirectories += baseDirectory.value / "src" / "test" / "scala"
  )
