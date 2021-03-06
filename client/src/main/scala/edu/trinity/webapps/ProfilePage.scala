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
    $("#logout").click(() => Login.pageSetup())
    addPlayer()
    getTamagos()
  }

  def checkDebt(): Unit = {
    val debts = (Player.debt / shared.SharedTables.debtThresh) //how many tamagos should get knees broken
    val numBrokes = Player.tamagos.filter(t => t.kneesbroken).length //+ (for (t<-Player.tamagos) yield t.timeskneesbroken).sum
    if (debts != numBrokes) {
      var affected = List[TamagoData]()
      for (i <- 1 to (debts - numBrokes)) {
        val unbrokens = Player.tamagos.filter(t => !t.kneesbroken)
        if (unbrokens.length > 0) {
          unbrokens.foreach(t => println("unbroken: " + t.name + " " + t.id))
          val i = (scala.util.Random.nextInt(unbrokens.length))
          val victim = unbrokens(i)
          val updatedTama = TamagoData(victim.id, victim.name, victim.attack, victim.defense,
            victim.speed, victim.health, true, victim.level, victim.isclean, victim.isalive,
            victim.age, victim.respect, victim.timeskneesbroken + 1)
          Player.tamagos = Player.tamagos.filter(t => t != victim)
          Player.tamagos ::= updatedTama
          affected ::= updatedTama
          println(updatedTama.name + " " + updatedTama.id + " got knees broken in client")
        }
      }
      affected.foreach(t => println(t.name))
      var windowUp = false
      for (victim <- affected) {
        $.getJSON("/breakLegs/" + victim.id, success = (o, s, j) => {
          val updatedTama = TamagoData(victim.id, victim.name, victim.attack, victim.defense,
            victim.speed, victim.health, true, victim.level, victim.isclean, victim.isalive,
            victim.age, victim.respect, victim.timeskneesbroken + 1)
          Player.tamagos = Player.tamagos.filter(t => t != victim)
          Player.tamagos ::= updatedTama
          println(updatedTama.name + " " + updatedTama.id + " got knees broken in server")
          if (!windowUp) {
            $("#profile-page").append($("<div id=\"page-mask\"></div>"))
            $("#profile-page").append($("<div id=\"window\"></div>"))
            val butt = "<button class=\"button\" id=\"kneeClose\">Close</button>"
            $("#window").append($(butt))
            $("#kneeClose").click(() => {
              $("#window").remove()
              $("#page-mask").remove()
            })
            $("#window").append($("<h3>You're in debt! >:(</h3> <br>"))
            $("#window").append($("<p class='center'>Because of your strenous debt, the loan shark has broken the knees of:</p>"))
            $("#window").append($(s"<p class='center'>${victim.name}</p>"))
            windowUp = true
          } else {
            $("#window").append($(s"<p class='center'>${victim.name}</p>"))
          }
        })
      }

    }
  }

  def getTamagos(): Unit = {
    val canvas = dom.document.getElementById("petCenter").asInstanceOf[dom.raw.HTMLCanvasElement]
    val context = canvas.getContext("2d")
    context.clearRect(0, 0, canvas.width, canvas.height);
    $.getJSON("/tamagos", success = (o, s, j) => {
      var count = 1
      for (t <- Json.parse(js.JSON.stringify(o)).as[Array[TamagoData]]) {
        Player.tamagos ::= t
      }
      checkDebt()
      for (t <- Player.tamagos) {
        val tamaPar = $(s"<li id = 't$count' class='hoverPet'> ${t.name}</li>")
        val knees = if (t.kneesbroken) "broken" else "working"
        val tamaStat = $(s"<li id = 'stat$count'> Health: ${t.health}, Knees: ${knees}, Attack: ${t.attack}, Defense: ${t.defense}, Speed: ${t.speed}</li>")
        //put a link on name to CurrentPet
        $("#pets").append(tamaPar)
        $("#stats").append(tamaStat)
        $("#t" + count).click(() => {
          CurrentPet.pageSetup(t)
        })
        $("#stat" + count).click(() => {
          CurrentPet.pageSetup(t)
        })
        count += 1
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
      context.font = "20px Arial";
      context.fillText(t.name, x + 15, y);

      count += 1
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
      <a id="logout">Logout</a>
    </div>

		<div id="profHeader"></div>
		<canvas id="petCenter" width="1400" height="600"
			style="border: 3px solid"></canvas>
		<br>
  <div class="center">
			<div class = "inline half left center">
        <ul id="pets" class="center"> <h3> Tamago List </h3> </ul>
      </div>
      <div class = "inline half right center">
        <ul id="stats" class="center"> <h3> Stats </h3> </ul>
      </div>
  </div>
	</body>
</span>"""

}
