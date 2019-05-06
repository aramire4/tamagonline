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
    $("#main-body").empty()
    $("#main-body").append($("<div id=\"profile-page\"></div>"))
    $("#profile-page").append($(str))
    $("#store").click(() => Store.pageSetup())
    addPlayer()
    getTamagos()
  }

  def getTamagos(): Unit = {
    val canvas = dom.document.getElementById("petCenter").asInstanceOf[dom.raw.HTMLCanvasElement]
    val context = canvas.getContext("2d")
    $.getJSON("/tamagos", success = (o, s, j) => {
      for (t <- Json.parse(js.JSON.stringify(o)).as[Array[TamagoData]]) {
        val tamaPar = $(s"<p>name: ${t.name}, age: ${t.age}, health: ${t.health}</p>")
        $("#profile-page").append(tamaPar)
        context.fillRect(50,50,50,50)
        context.fillText("name: "+t.name, 200, 200)
      }
    })
  }

  def addPlayer(): Unit = {
    $.getJSON("/player", success = (o, s, j) => {
      for (t <- Json.parse(js.JSON.stringify(o)).as[Array[PlayerData]]) {
        val playerPar = $(s"<p>${t.username}, ${t.id}, ${t.password}</p>")
        $("#profile-page").append(playerPar)
      }
    })
  }

  val str = """
<span>
	<body>
		<div class="topnav">
			<a href="profile" class="active">Profile</a> 
			<a href="playCenter">Current Pet</a> 
			<button id="store">Store</button> 
			<a href="help">Help</a>

		<h2>Profile</h2>
		<p class = "center"> HEY BRUH BRUH heard u wanna see some of your lil virtual pets. if u want that dumb shit go check out b spears group project. we here to break knees and fuck toma-chads</p>
		<canvas id="petCenter" width="1400" height="600"
			style="border: 3px solid"></canvas>
		<br>
	</body>
</span>"""

}