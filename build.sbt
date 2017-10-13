name := "event-rest-service"
organization := "com.arisanet"
version := "0.1.0"
scalaVersion := "2.11.8"

libraryDependencies ++= {
  val akkaV = "2.4.11"
  val scalaTestV = "3.0.0"
  val slickVersion = "3.1.1"
  val circeV = "0.6.1"
  Seq(
    "com.typesafe.akka" %% "akka-http-core" % akkaV,
    "com.typesafe.akka" %% "akka-http-experimental" % akkaV,
    "de.heikoseeberger" %% "akka-http-circe" % "1.11.0",
    "com.typesafe.slick" %% "slick" % slickVersion,
    "com.typesafe.slick" %% "slick-codegen" % slickVersion % "compile",
    "com.github.tminglei" %% "slick-pg" % "0.14.4",
    "com.typesafe.slick" %% "slick" % "3.1.1",
    "com.github.tminglei" %% "slick-pg" % "0.15.0-M3",
    "com.github.tminglei" %% "slick-pg_joda-time" % "0.15.0-M3",
    "com.github.tminglei" %% "slick-pg_circe-json" % "0.15.0-M3",
    "com.github.tminglei" %% "slick-pg_jts" % "0.15.0-M3",
    "com.github.tminglei" %% "slick-pg_json4s" % "0.15.0-M3",
    "com.github.tminglei" %% "slick-pg_play-json" % "0.15.0-M3",
    "com.github.tminglei" %% "slick-pg_spray-json" % "0.15.0-M3",
    "com.github.tminglei" %% "slick-pg_argonaut" % "0.15.0-M3",
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

// code generation task that calls the customized code generator
lazy val slick = taskKey[Seq[File]]("gen-tables")
lazy val slickCodeGenTask = Def.task {
  val dir = sourceManaged.value
  val cp = (dependencyClasspath in Compile).value
  val r = (runner in Compile).value
  val s = streams.value
  val outputDir = (dir / "slick").getPath // place generated files in sbt's managed sources folder
  toError(r.run("demo.CustomizedCodeGenerator", cp.files, Array(outputDir), s.log))
  val fname = outputDir + "/demo/Tables.scala"
  Seq(file(fname))
}