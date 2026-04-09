ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "3.8.3"

lazy val root = (project in file("."))
  .settings(
    name := "tpo2",
    Compile / sourceDirectories += baseDirectory.value / "src" / "main" / "scala",
    Test / sourceDirectories += baseDirectory.value / "src" / "test" / "scala",
    Test / unmanagedResourceDirectories += baseDirectory.value / "src/test/resources",
    libraryDependencies ++= Seq(
        "org.junit.jupiter" % "junit-jupiter-api" % "5.10.0" % Test,
        "org.junit.jupiter" % "junit-jupiter-engine" % "5.10.0" % Test,
        "org.junit.jupiter" % "junit-jupiter-params" % "5.10.0" % Test,
        "org.mockito" % "mockito-core" % "5.6.0" % Test,
        "org.mockito" % "mockito-junit-jupiter" % "5.6.0" % Test
    )
  )
