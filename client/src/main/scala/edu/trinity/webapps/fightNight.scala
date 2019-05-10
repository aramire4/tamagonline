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

object fightNight {
  var plyImg = dom.document.getElementById("tomahat").asInstanceOf[HTMLImageElement]
  var enemyImg = plyImg 
  var playLocX = 0
  var playLocY = 0
  var eneLocX = 0
  var eneLocY = 0
  var playerHealth = 10
  var enemyHealth = 100
  var playerAtt = 10
  var playerDef = 10
  var playerSpeed = 10

  

  def pageSetup(t: TamagoData, o: Int, enemyName: String): Unit = {
    $("#main-body").empty()
    $("#main-body").append($("<div id=\"fight\"></div>"))
    $("#fight").append($(str))
    $("#versus").append($(s"<h2>${t.name} VS. ${enemyName}</h2>"))
    playerHealth = t.health
    playerAtt = t.attack
    playerDef = t.defense
    playerSpeed = t.speed
    val canvas = dom.document.getElementById("fightNight").asInstanceOf[dom.raw.HTMLCanvasElement]
    val context = canvas.getContext("2d")
     playLocX = canvas.width/2 - 300
     playLocY = canvas.height / 2
    eneLocX = canvas.width/2 + 300
    eneLocY = canvas.height / 2
    setTamago(t)
    setEnemy(o)
    
  }
  
  
  def setTamago(t: TamagoData) {
    t.age match {
      case 1 => {
        plyImg = dom.document.getElementById("tomahat").asInstanceOf[HTMLImageElement]
      }
      case 2 => {
        plyImg = dom.document.getElementById("tomaflap").asInstanceOf[HTMLImageElement]
      }
      case 3 => {
        plyImg = dom.document.getElementById("tomamuscle").asInstanceOf[HTMLImageElement]
      }
      case 4 => {
        plyImg = dom.document.getElementById("tomamark").asInstanceOf[HTMLImageElement]
      }
      case 5 => {
        plyImg = dom.document.getElementById("tomasad").asInstanceOf[HTMLImageElement]
      }
      case 6 => {
        plyImg = dom.document.getElementById("tomastache").asInstanceOf[HTMLImageElement]
      }
      case 7 => {
        plyImg = dom.document.getElementById("tomagib").asInstanceOf[HTMLImageElement]
      }
      case 8 => {
        plyImg = dom.document.getElementById("tomagusta").asInstanceOf[HTMLImageElement]
      }
      case 9 => {
        plyImg = dom.document.getElementById("tomahawk").asInstanceOf[HTMLImageElement]
      }
      case 10 => {
        plyImg = dom.document.getElementById("tomasprout").asInstanceOf[HTMLImageElement]
      }
      case 11 => {
        plyImg = dom.document.getElementById("tomamystery").asInstanceOf[HTMLImageElement]
      }
      case 12 => {
        plyImg = dom.document.getElementById("tomaskitters").asInstanceOf[HTMLImageElement]
      }
      case _ => {
        println("This is an error")
      }
    }
  
  }

  def setEnemy(o: Int) {
    o match {
      case 1 => {
        enemyImg = dom.document.getElementById("tamaChad").asInstanceOf[HTMLImageElement]
      }
      case 2 => {
        enemyImg = dom.document.getElementById("tomamark").asInstanceOf[HTMLImageElement]
      }
      case 3 => {
        enemyImg = dom.document.getElementById("tamaChamp").asInstanceOf[HTMLImageElement]
      }
      case 4 => {
        enemyImg = dom.document.getElementById("StoreKeep").asInstanceOf[HTMLImageElement]
      }
      case 5 => {
        enemyImg = dom.document.getElementById("loanShark").asInstanceOf[HTMLImageElement]
      }
    }
   

  }
  
  def draw(canvas: Canvas) {
    val context = canvas.getContext("2d")
    context.clearRect(0, 0, canvas.width, canvas.height)
    context.drawImage(plyImg, playLocX,playLocY)
    context.drawImage(enemyImg, eneLocX, eneLocY)
  }
  

  js.timers.setInterval(50) {
     if(playerHealth > 0){
       draw(dom.document.getElementById("fightNight").asInstanceOf[dom.raw.HTMLCanvasElement])
       
     }

  }

  val str = """
    <span>
                 
    <div id = "versus"> </div>
		<canvas id="fightNight" width="1400" height="600"></canvas>
		<br>
		<br>
	
</span>
  """
}