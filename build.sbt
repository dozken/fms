name := "FMS"

version := "2.3.0-RC1"

lazy val root = (project in file(".")).enablePlugins(PlayJava)

scalaVersion := "2.11.1"

organization := "be.objectify"

libraryDependencies ++= Seq(
  javaEbean,
  "be.objectify" %% "deadbolt-java" % "2.3.0-RC1",
  "postgresql" % "postgresql" % "9.1-901-1.jdbc4",
  "org.webjars" %% "webjars-play" % "2.3.0",
  "org.webjars" % "bootstrap" % "3.2.0"
)

resolvers += Resolver.url("Objectify Play Repository", url("http://schaloner.github.com/releases/"))(Resolver.ivyStylePatterns)
