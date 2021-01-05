import Dependencies._

ThisBuild / scalaVersion     := "2.12.12"
ThisBuild / version          := "0.1.0-SNAPSHOT"
ThisBuild / organization     := "com.example"
ThisBuild / organizationName := "example"

lazy val root = (project in file("."))
  .settings(
    name := "mongo-migrate-example",
    libraryDependencies ++= Seq(
      "io.netty"         % "netty-all" % "4.1.51.Final",


      // Modern driver
      "org.mongodb.scala" %% "mongo-scala-driver" % "4.1.0",

      // Legacy driver
      "org.mongodb" %% "casbah-core" % "3.1.1",
      "org.mongodb" %% "casbah-gridfs" % "3.1.1",
      "com.github.salat" %% "salat-core" % "1.11.2",
      "com.github.salat" %% "salat-util" % "1.11.2",
    )
  )

