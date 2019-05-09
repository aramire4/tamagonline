package edu.trinity.webapps

import org.scalajs.dom
import scala.scalajs.js
import org.querki.jquery._

import scala.scalajs.js.annotation._
import play.api.libs.json._
import play.api.libs.json.Reads._
import play.api.libs.functional.syntax._
import edu.trinity.webapps.shared.SharedTables._
import org.scalajs.dom.html.Canvas
import org.scalajs.dom.raw.HTMLImageElement

//note to self - query stuff is a post/get, meaning you can keep sessions with that?!!??

object Battle {

  def pageSetup(t: TamagoData): Unit = {
    Player.clearData()
    $("#main-body").empty()
    $("#main-body").append($("<div id=\"profile-page\"></div>"))
    $("#profile").click(() => ProfilePage.pageSetup())
    $("#store").click(() => Store.pageSetup())
    $("#help").click(() => Help.pageSetup())
  }
  
  val str = """
<span>
	<body>
		<div class="topnav">
			<a id="profile">Profile</a> 
			<a id="store">Store</a> 
			<a id="help">Help</a>
    </div>

		<h2>Profile</h2>
		<p class = "center"> Below are all of your tamagos! Click on one to interact with it. </p>
		<canvas id="petCenter" width="1400" height="600"
			style="border: 3px solid"></canvas>
		<br>
  <ul id="pets" class="center"> <h3> Tamago List </h3> </ul>
	</body>
</span>"""

}
