
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
    "com.typesafe.akka"   %%  "akka-slf4j" 	  % akkaV,   
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
    "org.scala-lang" % "scala-reflect" % scalaVersion.value,
    "org.scalactic" %% "scalactic" % "2.2.6",
    "org.scalatest" %% "scalatest" % "2.2.6" % "test",
    "org.scalacheck" %% "scalacheck" % "1.12.5" % "test"
  )
  

}

fork in run := true

// This is critical. parallelExecution has to be turned off as the test DB will 
// have state from one test which will affect other tests 
parallelExecution in ThisBuild := false