name := "akka-persistence-protobuf-example"
version := "1.2"
scalaVersion := "2.12.4"

lazy val akkaVersion    = "2.5.25"

//Akka Persistence
libraryDependencies += "com.typesafe.akka" %% "akka-stream"             %  akkaVersion
libraryDependencies += "com.typesafe.akka" %% "akka-persistence"        %  akkaVersion
libraryDependencies += "com.typesafe.akka" %% "akka-persistence-query"  %  akkaVersion

// Java LevelDB
libraryDependencies += "org.iq80.leveldb"            % "leveldb"          % "0.12"
libraryDependencies += "org.fusesource.leveldbjni"   % "leveldbjni-all"   % "1.8"

// PostgresSQL DB
libraryDependencies += "be.wegenenverkeer" %% "akka-persistence-pg" % "0.10.0"

//Trigger ScalaPB (see ./project/protoc.sbt)
PB.targets in Compile := Seq(
  scalapb.gen() -> (sourceManaged in Compile).value
)

//Import-Bug (scalaPB)
libraryDependencies += "com.thesamet.scalapb" %% "scalapb-runtime" % scalapb.compiler.Version.scalapbVersion % "protobuf"