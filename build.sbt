name := "scalatest-scaladi-scalamock-akka-fsm"

version := "0.1"

scalaVersion := "2.11.7"

val akkaVersion = "2.3.12"

libraryDependencies ++= Seq(
"com.typesafe.akka" %% "akka-actor"   % akkaVersion,
"com.typesafe.akka" %% "akka-slf4j"   % akkaVersion,

"com.typesafe.akka" %% "akka-testkit"   % akkaVersion,


"org.scaldi" %% "scaldi-akka" % "0.5.6",

"org.scalatest" % "scalatest_2.11" % "2.2.4" % "test",
"org.scalamock" %% "scalamock-scalatest-support" % "3.2.2" % "test"
)