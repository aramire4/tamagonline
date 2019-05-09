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

object ProfilePage {

  def pageSetup(): Unit = {
    Player.clearData()
    $("#main-body").empty()
    $("#main-body").append($("<div id=\"profile-page\"></div>"))
    $("#profile-page").append($(str))
    $("#store").click(() => Store.pageSetup())
    $("#help").click(() => Help.pageSetup())
    addPlayer()
    getTamagos()
  }

  def getTamagos(): Unit = {
    val canvas = dom.document.getElementById("petCenter").asInstanceOf[dom.raw.HTMLCanvasElement]
    val context = canvas.getContext("2d")
    context.clearRect(0, 0, canvas.width, canvas.height);
    $.getJSON("/tamagos", success = (o, s, j) => {
      var count = 1
      for (t <- Json.parse(js.JSON.stringify(o)).as[Array[TamagoData]]) {
        val tamaPar = $(s"<li id = 't$count' class='hoverPet'> ${t.name} (health: ${t.health}) </li>")
        //put a link on name to CurrentPet
        $("#pets").append(tamaPar)
        $("#t"+count).click(() => {
          CurrentPet.pageSetup(t)
        })
        
        Player.tamagos ::= t
        count+=1
      }
      showTamago()
    })
  }

  def showTamago(): Unit = {
    val canvas = dom.document.getElementById("petCenter").asInstanceOf[dom.raw.HTMLCanvasElement]
    val context = canvas.getContext("2d")
    context.clearRect(0, 0, canvas.width, canvas.height);
    var x = 100;
    var y = 50;
    var count = 0;
    for (t <- Player.tamagos) {
      var image = dom.document.getElementById("tomahat").asInstanceOf[HTMLImageElement]
      t.age match {
        case 1 => {
          image = dom.document.getElementById("tomahat").asInstanceOf[HTMLImageElement]
        }
        case 2 => {
          image = dom.document.getElementById("tomaflap").asInstanceOf[HTMLImageElement]
        }
        case 3 => {
          image = dom.document.getElementById("tomamuscle").asInstanceOf[HTMLImageElement]
        }
        case 4 => {
          image = dom.document.getElementById("tomamark").asInstanceOf[HTMLImageElement]
        }
        case 5 => {
          image = dom.document.getElementById("tomasad").asInstanceOf[HTMLImageElement]
        }
        case 6 => {
          image = dom.document.getElementById("tomastache").asInstanceOf[HTMLImageElement]
        }
        case 7 => {
          image = dom.document.getElementById("tomagib").asInstanceOf[HTMLImageElement]
        }
        case 8 => {
          image = dom.document.getElementById("tomagusta").asInstanceOf[HTMLImageElement]
        }
        case 9 => {
          image = dom.document.getElementById("tomahawk").asInstanceOf[HTMLImageElement]
        }
        case 10 => {
          image = dom.document.getElementById("tomasprout").asInstanceOf[HTMLImageElement]
        }
        case 11 => {
          image = dom.document.getElementById("tomamystery").asInstanceOf[HTMLImageElement]
        }
        case 12 => {
          image = dom.document.getElementById("tomaskitters").asInstanceOf[HTMLImageElement]
        }
        case _ => {
          println("This is an error")
        }
      }
      context.drawImage(image, x, y)
      context.font = "30px Arial";
      context.fillText(t.name, x + 15, y);

      count+=1
      x += 200
      if (count % 6 == 0) {
        x = 100
        y += 200
        count = 0
      } 
    }
  }

  def addPlayer(): Unit = {
    $.getJSON("/player", success = (o, s, j) => {
      val p = Json.parse(js.JSON.stringify(o)).as[PlayerData]
      Player.addData(p)
      var playerHead = $(s"<h2>${Player.username}'s Profile</h2>")
      val playerPar = $(s"<p class ='center'>Coins: ${Player.coins}, Debt: ${Player.debt}, Kills: ${Player.kills}, Deaths: ${Player.deaths}</p>")
      $("#profHeader").append(playerHead)
      $("#profHeader").append(playerPar)
    })

  }

  val str = """
<span>
	<body>
		<div class="topnav">
			<a id="profile" class="active">Profile</a> 
			<a id="store">Store</a> 
			<a id="help">Help</a>
    </div>

		<div id="profHeader"></div>
		<canvas id="petCenter" width="1400" height="600"
			style="border: 3px solid"></canvas>
		<br>
  <ul id="pets" class="center"> <h3> Tamago List (Click to Interact) </h3> </ul>
	</body>
</span>"""

}
