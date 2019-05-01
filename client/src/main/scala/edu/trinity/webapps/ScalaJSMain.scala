package edu.trinity.webapps

import edu.trinity.webapps.shared.SharedTables._
import org.scalajs.dom

object ScalaJSMain {
  def main(args: Array[String]): Unit = {
    if (dom.document.getElementById("profile-page") != null) ProfilePage.pageSetup()

  }
}
