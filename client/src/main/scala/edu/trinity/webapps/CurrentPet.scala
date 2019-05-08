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

object CurrentPet {
  def pageSetup(t: TamagoData): Unit = {
    $("#main-body").empty()
    $("#main-body").append($("<div id=\"CurrentPet\"></div>"))
    $("#CurrentPet").append($(str))
    $("#petName").text(t.name)
    $("#store").click(() => Store.pageSetup())
    addPlayer()
    getTamago(t)
  }

  def addPlayer(): Unit = {
    $.getJSON("/player", success = (o, s, j) => {
      val p = Json.parse(js.JSON.stringify(o)).as[PlayerData]
      Player.addData(p)
      val playerPar = $(s"<p>${Player.username}, coins ${Player.coins}, debt: ${Player.debt}</p>")
      $("#profile-page").append(playerPar)
    })

  }

  def getTamago(t : TamagoData): Unit = {
    val canvas = dom.document.getElementById("petCenter").asInstanceOf[dom.raw.HTMLCanvasElement]
    val context = canvas.getContext("2d")
    $.getJSON("/tamagos", success = (o, s, j) => {
        val tamaPar = $(s"<p>name: ${t.name}, age: ${t.age}, health: ${t.health}</p>")
        $("#CurrentPet").append(tamaPar)
    })
    //var image = dom.document.getElementById("tomahat").asInstanceOf[HTMLImageElement]
    //context.drawImage(image, 50, 50)
    showTamago(t)
  }

  def showTamago(t: TamagoData): Unit = {
    val canvas = dom.document.getElementById("petCenter").asInstanceOf[dom.raw.HTMLCanvasElement]
    val context = canvas.getContext("2d")
    context.clearRect(0, 0, canvas.width, canvas.height);
    val r = scala.util.Random
    var x = r.nextInt(900)+50
    var y = r.nextInt(400)+50
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
  }

  val str = """
    <span>
		<div class="topnav">
			<a href="profile">Profile</a> 
			<a href="playCenter" class="active">Current Pet</a> 
			<a href="shop">Shop</a> 
			<a href="help">Help</a>

		</div>
    <h2 id="petName"></h2>
		<canvas id="petCenter" width="1400" height="600"></canvas>
		<br>
		<br>
		<div class="center">
			<form action="battle" method="get" class="center inline">
				<input type="submit" value="Battle" class="button"></input>
			</form>
			<form action="train" method="get" class="center inline">
				<input type="submit" value="Train" class="button"></input>
			</form>
			<form action="care" method="get" class="center inline">
				<input type="submit" value="Care" class="button"></input>
			</form>
			<form action="frolic" method="get" class="center inline">
				<input type="submit" value="Frolic" class="button"></input>
			</form>
		</div>
</span>
  """

}