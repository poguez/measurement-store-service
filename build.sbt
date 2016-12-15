name := "event-rest-service"
organization := "com.arisanet"
version := "0.1.0"
scalaVersion := "2.11.8"

libraryDependencies ++= {
  val akkaV = "2.4.11"
  val scalaTestV = "3.0.0"
  val slickVersion = "3.1.1"
  val circeV = "0.5.1"
  Seq(
    "com.typesafe.akka" %% "akka-http-core" % akkaV,
    "com.typesafe.akka" %% "akka-http-experimental" % akkaV,
    "de.heikoseeberger" %% "akka-http-circe" % "1.6.0",

    "com.typesafe.slick" %% "slick" % slickVersion,
    "com.typesafe.slick" %% "slick-codegen" % slickVersion % "compile",


    "org.postgresql" % "postgresql" % "9.4-1201-jdbc41",
    "org.flywaydb" % "flyway-core" % "3.2.1",

    "com.zaxxer" % "HikariCP" % "2.4.5",
    "org.slf4j" % "slf4j-nop" % "1.6.4",

    "io.circe" %% "circe-core" % circeV,
    "io.circe" %% "circe-generic" % circeV,
    "io.circe" %% "circe-parser" % circeV,

    "org.scalatest" %% "scalatest" % scalaTestV % "test",
    "com.typesafe.akka" %% "akka-http-testkit" % akkaV % "test",
    "ru.yandex.qatools.embed" % "postgresql-embedded" % "1.15" % "test"
  )
}

Revolver.settings
enablePlugins(JavaAppPackaging)
enablePlugins(DockerPlugin)

dockerExposedPorts := Seq(9004)
dockerEntrypoint := Seq("bin/%s" format executableScriptName.value, "-Dconfig.resource=docker.conf")

fork in run := true

slick <<= slickCodeGenTask; // register manual sbt command

// code generation task
lazy val slick = TaskKey[Seq[File]]("gen-tables")
lazy val slickCodeGenTask = (sourceManaged, dependencyClasspath in Compile, runner in Compile, streams) map { (dir, cp, r, s) =>
  val outputDir = (dir / "slick").getPath // place generated files in sbt's managed sources folder
val url = "jdbc:postgresql://localhost:5432/event-rest-service-akka" // connection info
val jdbcDriver = "org.postgresql.Driver"
  val slickDriver = "slick.driver.PostgresDriver"
  val pkg = "com.arisanet.restapi.models"
  toError(r.run("slick.codegen.SourceCodeGenerator", cp.files, Array(slickDriver, jdbcDriver, url, outputDir, pkg), s.log))
  val fname = outputDir + "/dao/Tables.scala"
  Seq(file(fname))
}