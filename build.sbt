
version       := "0.0.2"

scalaVersion  := "2.11.6"

scalacOptions := Seq("-unchecked", "-deprecation", "-encoding", "utf8")

libraryDependencies ++= {
  val akkaV = "2.3.9"
  val sprayV = "1.3.3"
  Seq(
    "io.spray"            %%  "spray-can"     % sprayV,
    "io.spray"            %%  "spray-routing" % sprayV,
    "io.spray"            %%  "spray-testkit" % sprayV  % "test",
    "io.spray" %%  "spray-json" % "1.3.1",
    "com.typesafe.akka"   %%  "akka-actor"    % akkaV,
    "com.typesafe.akka"   %%  "akka-testkit"  % akkaV   % "test",
    "org.specs2"          %%  "specs2-core"   % "2.3.11" % "test",
    "org.specs2"          %%  "specs2-mock"   % "2.3.11"  ,
    "org.scalatest" % "scalatest_2.11" % "2.2.1" % "test",
    "junit" % "junit" % "4.11" % "test",
    "com.typesafe.slick" %% "slick" % "3.0.0",
    "com.typesafe.slick" %% "slick-codegen" % "3.0.0",
    "mysql" % "mysql-connector-java" % "latest.release",
    "com.github.tototoshi" %% "slick-joda-mapper" % "2.0.0",
  	"joda-time" % "joda-time" % "2.7",
  	"org.joda" % "joda-convert" % "1.7",
    "com.typesafe" % "config" % "1.2.1",
    "com.h2database" % "h2" % "1.3.175",
    "org.postgresql" % "postgresql" % "9.3-1100-jdbc41",
    "com.typesafe.scala-logging" %% "scala-logging" % "3.1.0",
    "ch.qos.logback" % "logback-classic" % "1.1.3",
    "org.slf4j" % "slf4j-nop" % "1.6.4",
    "com.zaxxer" % "HikariCP-java6" % "2.3.6",
    "com.gettyimages" %% "spray-swagger" % "0.5.1",
    "org.scala-lang" % "scala-reflect" % scalaVersion.value
  )
}


fork in run := true