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
  var tamagoX = 0
  var tamagoY = 0
  var img = dom.document.getElementById("tomahat").asInstanceOf[HTMLImageElement]
  var moveTimer = 50
  
  def pageSetup(t: TamagoData): Unit = {
    $("#main-body").empty()
    $("#main-body").append($("<div id=\"CurrentPet\"></div>"))
    $("#CurrentPet").append($(str))
    $("#petName").text(t.name)
    $("#profile").click(() => ProfilePage.pageSetup())
    $("#store").click(() => Store.pageSetup())
    $("#help").click(() => Help.pageSetup())
    $("#battle").click(() => Battle.pageSetup())
    $("#frolic").click(() => Frolic.pageSetup())
    addPlayer()
    getTamago(t)
  }

  js.timers.setInterval(50) {
    drawImage();
    update();
    
  }

  def drawImage(): Unit = {
    val canvas = dom.document.getElementById("petCenter").asInstanceOf[dom.raw.HTMLCanvasElement]
    val context = canvas.getContext("2d")
    context.clearRect(0, 0, canvas.width, canvas.height);
    context.drawImage(img, tamagoX, tamagoY)
  }

  def update(): Unit = {
    val r = scala.util.Random
    val num = r.nextInt(4)+1
    if(num == 1){
      if(tamagoX < 1300)tamagoX += 5
    }
    else if(num == 2){
      if(tamagoX > 50)tamagoX -= 5
    }
    else if(num == 3){
      if(tamagoY < 500)tamagoY += 5
    }
    else{
      if(tamagoY > 50)tamagoY -= 5
    }
  }

  def addPlayer(): Unit = {
    $.getJSON("/player", success = (o, s, j) => {
      val p = Json.parse(js.JSON.stringify(o)).as[PlayerData]
      Player.addData(p)
      val playerPar = $(s"<p>${Player.username}, coins ${Player.coins}, debt: ${Player.debt}</p>")
      $("#profile-page").append(playerPar)
    })

  }

  def getTamago(t: TamagoData): Unit = {
    val canvas = dom.document.getElementById("petCenter").asInstanceOf[dom.raw.HTMLCanvasElement]
    val context = canvas.getContext("2d")
    $.getJSON("/tamagos", success = (o, s, j) => {
      val stats = $(s"<p class='center'>Health: ${t.health}, Attack: ${t.attack}, Defense: ${t.defense}, Speed: ${t.speed}</p>")
      val info = $(s"<p class='center'>How Much dis Fucker Respect Me: ${t.respect}</p>")
      $("#petStats").append(stats)
      $("#petInfo").append(info)
    })
    showTamago(t)
  }

  def showTamago(t: TamagoData): Unit = {
    val canvas = dom.document.getElementById("petCenter").asInstanceOf[dom.raw.HTMLCanvasElement]
    val context = canvas.getContext("2d")
    context.clearRect(0, 0, canvas.width, canvas.height);
    val r = scala.util.Random
    var x = r.nextInt(900) + 50
    var y = r.nextInt(400) + 50
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
    img = image
    tamagoX = x
    tamagoY = y

    context.drawImage(image, x, y)
  }

  val str = """
    <span>
		<div class="topnav">
			<a id="profile">Profile</a> 
			<a id="store">Store</a> 
			<a id="help">Help</a>
    </div>

		</div>
    <h2 id="petName"></h2>
    <p id ="petStats"> </p>
    <p id ="petInfo"> </p>
		<canvas id="petCenter" width="1400" height="600"></canvas>
		<br>
		<br>
		<div class="center">
      <button type="button" class="button inline" id="battle">Battle</button>
      <button type="button" class="button inline" id="frolic">Frolic</button>
			<form action="train" method="get" class="center inline">
				<input type="submit" value="Train" class="button"></input>
			</form>
			<form action="care" method="get" class="center inline">
				<input type="submit" value="Care" class="button"></input>
			</form>
		</div>
</span>
  """

}