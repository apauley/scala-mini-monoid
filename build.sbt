name := "mini-monoid"

version := "0.1"

scalaVersion := "2.12.7"

val specs2Version = "4.3.4"
val specs2Deps = Seq(
  "org.specs2" %% "specs2-core" % specs2Version % "test",
  "org.specs2" %% "specs2-scalacheck" % specs2Version % "test",
)

libraryDependencies ++= specs2Deps

// http://tpolecat.github.io/2014/04/11/scalac-flags.html
scalacOptions ++= Seq(
  "-deprecation",
  "-encoding", "UTF-8",
  "-feature",
  "-language:existentials",
  "-language:higherKinds",
  "-language:implicitConversions",
  "-unchecked",
  "-Xfatal-warnings",
  "-Xlint",
  "-Yno-adapted-args",
  "-Ywarn-dead-code",
  "-Ywarn-numeric-widen",
  "-Ywarn-value-discard",
  "-Xfuture"
)

scalacOptions in Test ++= Seq("-Yrangepos")

// Docs on each type of check is in the WartRemover README: https://github.com/puffnfresh/wartremover
wartremoverErrors in (Compile, compile) ++= Warts.allBut(Wart.Overloading, Wart.PublicInference)

resolvers ++= Seq("snapshots", "releases").map(Resolver.sonatypeRepo)
