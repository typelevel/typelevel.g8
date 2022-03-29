// https://typelevel.org/sbt-typelevel/faq.html#what-is-a-base-version-anyway
ThisBuild / tlBaseVersion := "0.0" // your current series x.y

ThisBuild / organization := "$organization$"
ThisBuild / organizationName := "$organization_name$"
ThisBuild / licenses := Seq(License.Apache2)
ThisBuild / developers := List(
  // your GitHub handle and name
  tlGitHubDev("$github_username$", "$contributor_name$")
)

// true by default, set to false to publish to s01.oss.sonatype.org
ThisBuild / tlSonatypeUseLegacyHost := true

val Scala213 = "$scala_version$"
ThisBuild / crossScalaVersions := Seq(Scala213, "$other_scala_version$")
ThisBuild / scalaVersion := Scala213 // the default Scala

lazy val root = tlCrossRootProject.aggregate(core)

lazy val core = crossProject(JVMPlatform, JSPlatform)
  .crossType(CrossType.Pure)
  .in(file("core"))
  .settings(
    name := "$name;format="norm"$",
    libraryDependencies ++= Seq(
      "org.typelevel" %%% "cats-core"           % "2.7.0",
      "org.typelevel" %%% "cats-effect"         % "3.3.9",
      "org.scalameta" %%% "munit"               % "0.7.29" % Test,
      "org.typelevel" %%% "munit-cats-effect-3" % "1.0.7"  % Test,
    )
  )
