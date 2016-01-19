import OcsKeys._

// note: inter-project dependencies are declared at the top, in projects.sbt

name := "edu.gemini.dataman.app"

// version set in ThisBuild

unmanagedJars in Compile ++= Seq(
  new File(baseDirectory.value, "../../lib/bundle/argonaut_2.10-6.1.jar"),
  new File(baseDirectory.value, "../../lib/bundle/monocle-core_2.10-1.1.0.jar"),
  new File(baseDirectory.value, "../../lib/bundle/monocle-macro_2.10-1.1.0.jar"),
  new File(baseDirectory.value, "../../lib/bundle/quasiquotes_2.10-2.0.1.jar"),
  new File(baseDirectory.value, "../../lib/bundle/org.scala-lang.scala-reflect_2.10.1.v20130302-092018-VFINAL-33e32179fd.jar"),
  new File(baseDirectory.value, "../../lib/bundle/osgi.cmpn-4.3.1.jar"),
  new File(baseDirectory.value, "../../lib/bundle/scalaz-concurrent_2.10-7.1.6.jar")
)

osgiSettings

ocsBundleSettings

OsgiKeys.bundleActivator := Some("edu.gemini.dataman.osgi.Activator")

OsgiKeys.bundleSymbolicName := name.value

OsgiKeys.dynamicImportPackage := Seq("")

OsgiKeys.exportPackage := Seq()


OsgiKeys.privatePackage := Seq(
  "edu.gemini.dataman.*"
  )
