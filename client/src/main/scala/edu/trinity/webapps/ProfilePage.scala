package edu.trinity.webapps

import org.scalajs.dom
import scala.scalajs.js
import org.querki.jquery._

import scala.scalajs.js.annotation._
import play.api.libs.json._
import play.api.libs.json.Reads._
import play.api.libs.functional.syntax._
import edu.trinity.webapps.shared.SharedTables.PlayerRow
import edu.trinity.webapps.shared.SharedTables.playerRowReads
import edu.trinity.webapps.shared.SharedTables.playerRowWrites


//note to self - query stuff is a post/get, meaning you can keep sessions with that?!!??

object ProfilePage {
  def pageSetup(): Unit = {
    println("Setup the bootyhole")
    addPlayer("barnabus") //don't know where I would get this parameter. Through post session?
  }
 
  
  /**
   * Given a player ID, returns the tamagos associated with that player. 
   * 
   * @param pid ID of the player of interest
   * @return a unit that populates the html dom with tamago info
   */
  def addPlayer(uname:String): Unit = {
    println("printing the tamagos")
    $.getJSON("/player/"+uname, success = (o, s, j) => {
      for (t <- Json.parse(js.JSON.stringify(o)).as[Array[PlayerRow]]) {
        val playerPar = $(s"<p>${t.username}, ${t.id}, ${t.password}</p>")
        $("#profile-page").append(playerPar)
      }
    })
  }
}