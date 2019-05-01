package edu.trinity.webapps

import edu.trinity.webapps.shared.SharedTables.PlayerRow
import org.scalajs.dom

object ScalaJSMain {
  def main(args: Array[String]): Unit = {
    if (dom.document.getElementById("profilePage") != null) ProfilePage.pageSetup()

  }
}
