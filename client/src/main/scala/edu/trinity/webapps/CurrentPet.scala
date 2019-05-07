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
  def pageSetup(t:TamagoData): Unit = {
    $("#main-body").empty()
    $("#main-body").append($("<div id=\"CurrentPet\"></div>"))
    $("#CurrentPet").append($(str))
    $("#petName").text(t.name)
    $("#store").click(() => Store.pageSetup())
    addPlayer()
    getTamago()
  }

  def addPlayer(): Unit = {
    $.getJSON("/player", success = (o, s, j) => {
      val p = Json.parse(js.JSON.stringify(o)).as[PlayerData]
      Player.addData(p)
      val playerPar = $(s"<p>${Player.username}, coins ${Player.coins}, debt: ${Player.debt}</p>")
      $("#profile-page").append(playerPar)
    })

  }

  def getTamago(): Unit = {
    val canvas = dom.document.getElementById("petCenter").asInstanceOf[dom.raw.HTMLCanvasElement]
    val context = canvas.getContext("2d")
    $.getJSON("/tamagos", success = (o, s, j) => {
      for (t <- Json.parse(js.JSON.stringify(o)).as[Array[TamagoData]]) {
        val tamaPar = $(s"<p>name: ${t.name}, age: ${t.age}, health: ${t.health}</p>")
        $("#profile-page").append(tamaPar)
        Player.tamagos ::= t
        context.fillRect(50, 50, 50, 50)
        context.fillText("name: " + t.name, 200, 200)

      }
    })
    var image = dom.document.getElementById("tomahat").asInstanceOf[HTMLImageElement]
    context.drawImage(image, 50, 50)
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
		<canvas id="petCenter"></canvas>
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