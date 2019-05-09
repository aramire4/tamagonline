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

object Frolic {
  var wsRoute = null //document.getElementById("ws-route");
  var socket = null //new WebSocket(wsRoute.value.replace("http", "ws"));

  var canvas = dom.document.getElementById("FrolicZone").asInstanceOf[dom.raw.HTMLCanvasElement]
  var context = canvas.getContext("2d")

  def pageSetup(t: TamagoData): Unit = {
    Player.clearData()
    $("#main-body").empty()
    //$("#main-body").append($("<div id=\"profile-page\"></div>"))
    $("#main-body").append($("<div id=\"Frolic\"></div>"))
    $("#Frolic").append($(str))
    $("#profile").click(() => ProfilePage.pageSetup())
    $("#store").click(() => Store.pageSetup())
    $("#help").click(() => Help.pageSetup())

    wsRoute = document.getElementById("ws-route");
    socket = new WebSocket(wsRoute.value.replace("http", "ws"));
    
    addTamago(t)
  }

  def addTamago(t: TamagoData): Unit = {

  }

  val str = """
<span>
	<body>
		<div class="topnav">
			<a id="profile">Profile</a> 
			<a id="store">Store</a> 
			<a id="help">Help</a>
    </div>

		<h2>Frolic</h2>
	  <input type="hidden" id="ws-route" value="@routes.WebSocket.socket.absoluteURL()"></input>
		<canvas id="FrolicZone" width="1400" height="600"
			style="border: 3px solid"></canvas>
		<br>
	</body>
</span>"""

}
