name := "scala_sbt"

organization := "it.vige.hadoop"

version := "0.1"

scalaVersion := "2.11.7"

libraryDependencies ++= Seq(
  "org.apache.hadoop" % "hadoop-client" % "2.5.1-mapr-1501" excludeAll(
    ExclusionRule(organization = "com.sun.jdmk"),
    ExclusionRule(organization = "com.sun.jmx"),
    ExclusionRule(organization = "javax.jms")), 
  "org.json" % "json" % "20151123",
  "org.scalatest" % "scalatest_2.11" % "2.2.5" % "test"
)

resolvers += "MapR jars" at "http://repository.mapr.com/nexus/content/groups/mapr-public/"

initialCommands := "import it.vige.hadoop.samples._"

