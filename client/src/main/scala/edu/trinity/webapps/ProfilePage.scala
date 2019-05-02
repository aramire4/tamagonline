package edu.trinity.webapps

import org.scalajs.dom
import scala.scalajs.js
import org.querki.jquery._

import scala.scalajs.js.annotation._
import play.api.libs.json._
import play.api.libs.json.Reads._
import play.api.libs.functional.syntax._
import edu.trinity.webapps.shared.SharedTables._


//note to self - query stuff is a post/get, meaning you can keep sessions with that?!!??

object ProfilePage {
  def pageSetup(): Unit = {

    val str = """<span>
	<body>
		<div class="topnav">
			<a href="profile" class="active">Profile</a> 
			<a href="playCenter">Current Pet</a> 
			<a href="shop">Shop</a> 
			<a href="help">Help</a>

		</div>
		<h2>Profile</h2>
		<p class = "center"> HEY BRUH BRUH heard u wanna see some of your lil virtual pets. if u want that dumb shit go check out b spears group project. we here to break knees and fuck toma-chads</p>
		<canvas id="petCenter" width="1400" height="600"
			style="border: 3px solid"></canvas>
		<br>
		<script type="text/javascript" src="scripts/petScript.js"></script>
	</body>
</span>"""
    
    $("#profile-page").append($(str))
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
      for (t <- Json.parse(js.JSON.stringify(o)).as[Array[PlayerData]]) {
        val playerPar = $(s"<p>${t.username}, ${t.id}, ${t.password}</p>")
        $("#profile-page").append(playerPar)
      }
    })
  }
}