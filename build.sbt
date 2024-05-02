ThisBuild / tlBaseVersion := "0.0" // your current series x.y

// We don't want to publish
ThisBuild / tlCiReleaseTags := false
ThisBuild / tlCiReleaseBranches := Nil
ThisBuild / tlCiDependencyGraphJob := false

ThisBuild / githubWorkflowBuild := Seq(
  WorkflowStep.Sbt(
    List("g8Test"),
    name = Some("Test generated template")
  )
)

val PrimaryOS = "ubuntu-latest"
val MacOS = "macos-latest"
ThisBuild / githubWorkflowOSes := Seq(PrimaryOS, MacOS)

val PrimaryJava = JavaSpec.temurin("8")
val LTSJava = JavaSpec.temurin("17")
ThisBuild / githubWorkflowJavaVersions := Seq(PrimaryJava, LTSJava)

// This build is for this Giter8 template.
// To test the template run `g8` or `g8Test` from the sbt session.
// See http://www.foundweekends.org/giter8/testing.html#Using+the+Giter8Plugin for more details.
lazy val root = (project in file("."))
  .aggregate(phantomDependencies)
  .settings(
    name := "typelevel.g8",
    Test / test := {
      val _ = (Test / g8Test).toTask("").value
    },
    scriptedLaunchOpts ++= List("-Xms1024m", "-Xmx1024m", "-XX:ReservedCodeCacheSize=128m", "-Xss2m", "-Dfile.encoding=UTF-8"),
    resolvers += Resolver.url("typesafe", url("https://repo.typesafe.com/typesafe/ivy-releases/"))(Resolver.ivyStylePatterns)
  )

lazy val phantomDependencies = project
  .settings(
    libraryDependencies ++= Seq(
      "org.typelevel" %%% "cats-core" % "2.10.0",
      "org.typelevel" %%% "cats-effect" % "3.5.4",
      "org.scalameta" %%% "munit" % "0.7.29" % Test,
      "org.typelevel" %%% "munit-cats-effect-3" % "1.0.7" % Test,
      "org.scala-lang"  % "scala-library" % "2.13.14",
      "org.scala-lang"  % "scala3-library_3" % "3.3.3"
    ),
  )
