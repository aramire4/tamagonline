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
  var pageActive = false

  def pageSetup(t: TamagoData): Unit = {
    $("#main-body").empty()
    $("#main-body").append($("<div id=\"CurrentPet\"></div>"))
    $("#CurrentPet").append($(str))
    $("#petName").text(t.name)
    $("#profile").click(() => { pageActive = false; ProfilePage.pageSetup() })
    $("#store").click(() => { pageActive = false; Store.pageSetup() })
    $("#help").click(() => { pageActive = false; Help.pageSetup() })
    $("#battle").click(() => { pageActive = false; Battle.pageSetup(t) })
    $("#frolic").click(() => { pageActive = false; Frolic.pageSetup(t) })
    $("#train").click(() => { pageActive = false; Train.pageSetup(t) })
    $("#heal").click(() => healPage(t))
    $("#logout").click(() => Login.pageSetup())
    addPlayer()
    getTamago(t)
    pageActive = true
  }

  js.timers.setInterval(50) {
    if (pageActive) {
      drawImage();
      update();
    }
  }

  def drawImage(): Unit = {
    val canvas = dom.document.getElementById("currentPet").asInstanceOf[dom.raw.HTMLCanvasElement]
    val context = canvas.getContext("2d")
    context.clearRect(0, 0, canvas.width, canvas.height);
    context.drawImage(img, tamagoX, tamagoY)
  }

  def update(): Unit = {
    val r = scala.util.Random
    val num = r.nextInt(4) + 1
    if (num == 1) {
      if (tamagoX < 1300) tamagoX += 10
    } else if (num == 2) {
      if (tamagoX > 50) tamagoX -= 10
    } else if (num == 3) {
      if (tamagoY < 500) tamagoY += 10
    } else {
      if (tamagoY > 50) tamagoY -= 10
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
    val canvas = dom.document.getElementById("currentPet").asInstanceOf[dom.raw.HTMLCanvasElement]
    val context = canvas.getContext("2d")
    $.getJSON("/tamagos", success = (o, s, j) => {
      val tds = Json.parse(js.JSON.stringify(o)).as[Array[TamagoData]]
      val newT = tds.filter(td => td.id == t.id).head
      val knees = if (t.kneesbroken) "broken" else "working"
      val stats = $(s"<p class='center'>Health: ${newT.health}, Knees: ${knees}, Attack: ${newT.attack}, Defense: ${newT.defense}, Speed: ${newT.speed}</p>")
      $("#petStats").append(stats)
      showTamago(newT)
    })
  }

  def showTamago(t: TamagoData): Unit = {
    val canvas = dom.document.getElementById("currentPet").asInstanceOf[dom.raw.HTMLCanvasElement]
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

  def healPage(t: TamagoData): Unit = {
    var localBool = t.kneesbroken
    $("#CurrentPet").append($(healStr))
    $("#health").click(() => {
      if (Player.coins < shared.SharedTables.healCost) { 
        $("#msg1").text("Not enough money")
        $("#msg2").text("")
        $("#msg3").text("")
      }
      else {
        $.getJSON("/heal/" + t.id, success = (o, s, j) => {
          $("#msg1").text(s"Congrats, you've healed your tamago by ${shared.SharedTables.healEffect}.")
          $("#msg2").text(s"${shared.SharedTables.healCost} have been removed from your account.")
          $("#msg3").text("")
          Player.coins -= shared.SharedTables.healCost
        })
      }
    })
    $("#knee").click(() => {
      if (Player.coins < shared.SharedTables.kneeSurgeryCost) {
        $("#msg1").text("Not enough money")
        $("#msg2").text("")
        $("#msg3").text("")
      } else {
      $.getJSON("/breakLegs/" + t.id, success = (o, s, j) => {
        $.getJSON("/removeCoins/" + (-shared.SharedTables.kneeSurgeryCost), success = (o, s, j) => {
          println("knee surgery logged")
          $("#msg1").text(s"Congrats, you've repaired your tamagos knees")
          $("#msg2").text(s"${shared.SharedTables.kneeSurgeryCost} have been removed from your account.")
          $("#msg3").text("")
          if (!localBool) {
            $("#msg1").text(s"Whoops! Your tamago's knees were actually working before the surgery.")
            $("#msg2").text(s"But the doctor messed up, and broke the knees during the surgery!")
            $("#msg3").text(s"${shared.SharedTables.kneeSurgeryCost} have been removed from your account.")
          }
          Player.tamagos = Player.tamagos.filter(td => td != t)
          Player.tamagos ::= TamagoData(t.id, t.name, t.attack, t.defense,
            t.speed, t.health, !t.kneesbroken, t.level, t.isclean, t.isalive,
            t.age, t.respect, t.timeskneesbroken + 1)
          Player.coins -= shared.SharedTables.kneeSurgeryCost
          localBool = !localBool
          
        })
      })}
    })
    $("#closeWindow").click(() => {
      $("#window").remove()
      $("#page-mask").remove()
    })
  }

  val healStr = s"""<div id="window" class="center">
      <div class="smallTop"> </div>
      <h3> Heal Your Tamago </h3>
      <p class="center">Fix up your broken pet!</p>
      <br>
      <div class ="center">
        <button type="button" id="health" class="button inline left center">Health</button> 
        <p class ="inline half right center"> Cost:${shared.SharedTables.healCost}, Heals your tamago by ${shared.SharedTables.healEffect}</p> 
      </div>
      <br><br>
      <div class ="center">
        <button type="button" id="knee" class="button inline left center">Knee Surgery</button> 
        <p class ="inline half right center">Cost:${shared.SharedTables.kneeSurgeryCost}, Repair your tamago's broken knees</p>
      </div>
      <br> <br> <br>
      <button id="closeWindow" class="button">Close</button>      

      <p id="msg1"></p>
      <p id="msg2"></p>
      <p id="msg3"></p>
    </div>"""

  val str = """
    <span>
		<div class="topnav">
			<a id="profile">Profile</a> 
			<a id="store">Store</a> 
			<a id="help">Help</a>
      <a id="logout">Logout</a>
    </div>

		</div>
    <h2 id="petName"></h2>
    <p id ="petStats"> </p>
    <p id ="petInfo"> </p>
		<canvas id="currentPet" width="1400" height="600"></canvas>
		<br>
		<br>
		<div class="center">
      <button type="button" class="button inline" id="battle">Battle</button>
      <button type="button" class="button inline" id="frolic">Frolic</button>
      <button type="button" class="button inline" id="train">Train</button>
      <button type="button" class="button inline" id="heal">Heal</button>
		</div>
</span>
  """

}