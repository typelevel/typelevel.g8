ThisBuild / tlBaseVersion := "0.0" // your current series x.y

// We don't want to publish
ThisBuild / tlCiReleaseTags := false
ThisBuild / tlCiReleaseBranches := Nil

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
  .enablePlugins(ScriptedPlugin)
  .settings(
    name := "typelevel.g8",
    libraryDependencies ++= Seq(
      "org.typelevel" %%% "cats-core" % "2.8.0",
      "org.typelevel" %%% "cats-effect" % "3.3.14",
      "org.scalameta" %%% "munit" % "0.7.29" % Test,
      "org.typelevel" %%% "munit-cats-effect-3" % "1.0.7" % Test
    ),
    Test / test := {
      val _ = (Test / g8Test).toTask("").value
    },
    scriptedLaunchOpts ++= List("-Xms1024m", "-Xmx1024m", "-XX:ReservedCodeCacheSize=128m", "-Xss2m", "-Dfile.encoding=UTF-8"),
    resolvers += Resolver.url("typesafe", url("https://repo.typesafe.com/typesafe/ivy-releases/"))(Resolver.ivyStylePatterns)
  )

lazy val copyTask = taskKey[Unit]("A sample task.")

lazy val updateGHWorkflow = (project in file(".update"))
  .enablePlugins(ScriptedPlugin)
    .settings(
      Test / test := {
        val _ = (Test / g8Test).toTask("").value
      },
      scriptedLaunchOpts ++= List("-Xms1024m", "-Xmx1024m", "-XX:ReservedCodeCacheSize=128m", "-Xss2m", "-Dfile.encoding=UTF-8"),
      resolvers += Resolver.url("typesafe", url("https://repo.typesafe.com/typesafe/ivy-releases/"))(Resolver.ivyStylePatterns),
      Compile / g8 / sources :=
        Seq((LocalRootProject / baseDirectory).value /  "src" / "main" / "g8"),
      Compile / g8 / unmanagedSourceDirectories :=
        Seq((LocalRootProject / baseDirectory).value /  "src" / "main" / "g8"),
      copyTask := {
        IO.copy(Seq((
          baseDirectory.value / ".github/workflows/ci.yml",
          (LocalRootProject / baseDirectory).value / "src/main/g8/.github/workflows/ci.yml"
        )))
      },
      g8TestScript := (LocalRootProject / baseDirectory).value / "project/gen-inner-workflow.test"
    )
