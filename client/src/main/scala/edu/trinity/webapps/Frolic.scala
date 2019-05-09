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
import org.scalajs.dom.raw.WebSocket
import org.scalajs.dom.raw.KeyboardEvent

//note to self - query stuff is a post/get, meaning you can keep sessions with that?!!??

object Frolic {

  var canvas = dom.document.getElementById("FrolicZone").asInstanceOf[dom.raw.HTMLCanvasElement]
  var context = canvas.getContext("2d")
  var x = canvas.width / 2
  var y = canvas.height / 2
  var up = false;
  var down = false;
  var left = false;
  var right = false;
  var step = 3

  def pageSetup(t: TamagoData): Unit = {
    Player.clearData()
    $("#main-body").empty()
    //$("#main-body").append($("<div id=\"profile-page\"></div>"))
    $("#main-body").append($("<div id=\"Frolic\"></div>"))
    $("#Frolic").append($(str))
    $("#profile").click(() => ProfilePage.pageSetup())
    $("#store").click(() => Store.pageSetup())
    $("#help").click(() => Help.pageSetup())

    //val wsRoute = dom.document.getElementById("ws-route");
    //val socket = new WebSocket(wsRoute.value.replace("http", "ws"));
    /*
    dom.window.onkeydown = (e: dom.KeyboardEvent) => {
      if (e.keyCode == 38) {
        //up
        y -= step
        socket.send(x + ", " + y)
      }
      if (e.keyCode == 40) {
        //down
        y += step
        socket.send(x + ", " + y)
      }
      if (e.keyCode == 37) {
        //left
        x -= step
        socket.send(x + ", " + y)
      }
      if (e.keyCode == 39) {
        //right
        x += step
        socket.send(x + ", " + y)
      }
    }
		*/
    addTamago(t)
  }
  

  def addTamago(t: TamagoData): Unit = {

  }

  dom.window.onkeyup = (e: dom.KeyboardEvent) => {
    if (e.keyCode == 38) {
      //up

    }
    if (e.keyCode == 40) {
      //down

    }
    if (e.keyCode == 37) {
      //left

    }
    if (e.keyCode == 39) {
      //right

    }
  }

  /*
  def checkKey(e:KeyboardEvent): Unit ={
  	e.keyCode match {
  		case 38 =>
  		  //up arrow
  			//console.log("up");
  			//console.log("moved coordinate: " + p1.x + " " + (p1.y+step));
  			if(p1.y - step > 0) p1.y-= step;
  			socket.send(p1.x + ", "+ p1.y);
  			break
  		case 40 =>
  		  //down arrow
  			if(p1.y + step < height-playerSize) p1.y +=step;
  			socket.send(p1.x + ", " + p1.y);
  			break;
  		case 37 =>
  		  //left
  			if(p1.x - step > 0) p1.x -= step;
  			socket.send(p1.x + ", " + p1.y);
  			break;
  		case 39 =>
  		  //right
  			if(p1.x + step < width-playerSize) p1.x += step;
  			socket.send(p1.x + ", " + p1.y);
  			break;
  	}
  }
  */
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
