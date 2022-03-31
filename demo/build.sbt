ThisBuild / version      := "0.0.1"
ThisBuild / scalaVersion := "2.12.15"
ThisBuild / organization := "com.github.setldemo"
ThisBuild / retrieveManaged := true

ThisBuild / assemblyMergeStrategy := {
  case PathList("META-INF", xs @ _*) => MergeStrategy.discard
  case x => MergeStrategy.first
}

val sparkVersion = "3.2.0"

val sparkDependencies = Seq(
      "org.apache.spark" %% "spark-core" % sparkVersion,
      "org.apache.spark" %% "spark-sql" % sparkVersion,
      "org.apache.spark" %% "spark-hive" % sparkVersion,
      "org.apache.spark" %% "spark-yarn" % sparkVersion
)

lazy val demo = (project in file("."))
  .settings(
    name := "demo",
    libraryDependencies += "io.github.setl-framework" %% "setl" % "1.0.0-RC2",
    libraryDependencies += "com.github.scopt" %% "scopt" % "4.0.1",

    libraryDependencies ++= sparkDependencies.map(_ % Provided)
  )
