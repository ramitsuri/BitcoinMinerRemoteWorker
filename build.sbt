name := "BitcoinMinerRemoteWorker"

version := "1.0"

scalaVersion := "2.11.7"


libraryDependencies ++= Seq(
  "com.typesafe.akka" %% "akka-actor" % "2.3.11",
  "com.typesafe.akka" %% "akka-testkit" % "2.3.11",
  "org.scalatest" %% "scalatest" % "2.2.4" % "test",
  "junit" % "junit" % "4.12" % "test",
  "com.novocode" % "junit-interface" % "0.11" % "test",
  "com.typesafe.akka" %% "akka-remote" % "2.3.10"
)