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

import scala.collection.mutable.ArrayBuffer
import scala.math._
import scala.scalajs.js.timers.SetIntervalHandle

object fightNight {
  var plyImg = dom.document.getElementById("tomahat").asInstanceOf[HTMLImageElement]
  var enemyImg = plyImg
  var playLocX = 0.0
  var playLocY = 0.0
  var eneLocX = 0.0
  var eneLocY = 0.0

  var playerHealth = 10.0
  var enemyHealth = 100.0
  val maxEnemyHealth = enemyHealth

  var playerAtt = 10.0
  var playerDef = 10.0
  var playerSpeed = 10.0

  var enemyAtt = 10.0
  var enemyDef = 10.0
  var enemySpeed = 10.0

  var playerBulletSpeed = 10.0
  var enemyBulletSpeed = 10.0

  var timer = 30.0

  var width = 0
  var height = 0

  var playerBullets = ArrayBuffer[Bullet]()
  var enemyBullets = ArrayBuffer[Bullet]()

  var gameState = 0
  var gameRunning = true
  var isActive: Boolean = true

  var myTamago = null.asInstanceOf[TamagoData]

  def pageSetup(t: TamagoData, o: Int, enemyName: String): Unit = {
    $("#main-body").empty()
    $("#main-body").append($("<div id=\"fight\"></div>"))
    $("#fight").append($(str))
    $("#versus").append($(s"<h2>${t.name} VS. ${enemyName}</h2>"))
    playerHealth = t.health
    playerAtt = t.attack
    playerDef = t.defense
    playerSpeed = 5 + t.speed * .2
    if (t.kneesbroken) playerSpeed = playerSpeed / 2

    val canvas = dom.document.getElementById("fightNight").asInstanceOf[dom.raw.HTMLCanvasElement]
    val context = canvas.getContext("2d")
    width = canvas.width
    height = canvas.height
    playLocX = canvas.width / 2 - 300
    playLocY = canvas.height / 2
    eneLocX = canvas.width / 2 + 300
    eneLocY = canvas.height / 2
    playerBulletSpeed = 7 + (playerSpeed * .1)
    myTamago = t
    setTamago(t)
    setEnemy(o)
    isActive = true
    gameState = 0
    print("PageSetup")

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
        enemyAtt = 10.0
        enemyDef = 2.0
        enemySpeed = 5.0
      }
      case 2 => {
        enemyImg = dom.document.getElementById("tomamark").asInstanceOf[HTMLImageElement]
        enemyAtt = 8.0
        enemyDef = 4.0
        enemySpeed = 20.0
      }
      case 3 => {
        enemyImg = dom.document.getElementById("tamaChamp").asInstanceOf[HTMLImageElement]
        enemyAtt = 10.0
        enemyDef = 10.0
        enemySpeed = 10.0
      }
      case 4 => {
        enemyImg = dom.document.getElementById("StoreKeep").asInstanceOf[HTMLImageElement]
        enemyAtt = 2.0
        enemyDef = 1.0
        enemySpeed = 5.0
      }
      case 5 => {
        enemyImg = dom.document.getElementById("loanShark").asInstanceOf[HTMLImageElement]
        enemyAtt = 12.0
        enemyDef = 5.0
        enemySpeed = 15.0
      }
    }

  }

  def draw(canvas: Canvas) {
    val context = canvas.getContext("2d")
    context.clearRect(0, 0, canvas.width, canvas.height)
    context.drawImage(plyImg, playLocX, playLocY)
    context.drawImage(enemyImg, eneLocX, eneLocY)

    playerBullets.foreach(bull => {
      context.fillStyle = "#0000FF"
      context.beginPath();
      context.arc(bull.xPos, bull.yPos, 5, 0, 2 * math.Pi)
      context.stroke()
      context.fill()
    })

    enemyBullets.foreach(bull => {
      context.fillStyle = "#FF0000"
      context.beginPath();
      context.arc(bull.xPos, bull.yPos, 5, 0, 2 * math.Pi)
      context.stroke()
      context.fill()
    })
    context.fillStyle = "#FF0000" //red
    context.fillRect(0, 0, width / 2, 40)
    context.fillStyle = "#32CD32" //green
    context.fillRect(0, 0, (playerHealth / maxPlayerHealth) * (width / 2), 40)

    context.fillStyle = "#32CD32"
    context.fillRect(width / 2, 0, (width / 2), 40)
    context.fillStyle = "#FF0000"
    context.fillRect(width / 2, 0, (1 - (enemyHealth / maxEnemyHealth)) * (width / 2), 40)

    context.fillStyle = "#000000"
    context.fillRect(width / 2, 0, 10, 40)
  }
  val maxPlayerHealth = playerHealth

  /*if (playerHealth < -1) {
    gameState = 2
    gameRunning = false
  } else if (enemyHealth < -1) {
    gameState = 1
    gameRunning = false
  } else { gameState = 0; gameRunning = true; }
*/
  // if (gameRunning) {

  var hand: SetIntervalHandle = js.timers.setInterval(50) {

    if (isActive) {
      if (playerHealth < 0) {
        gameState = 2
      } else if (enemyHealth < 0) {
        gameState = 1
      } else {

        //if (playerHealth > -1 && enemyHealth > -1) {

        draw(dom.document.getElementById("fightNight").asInstanceOf[dom.raw.HTMLCanvasElement])

        dom.window.onkeydown = (e: dom.KeyboardEvent) => {
          if (e.keyCode == 38) {
            //up
            if (checkBounds(playLocX, playLocY - playerSpeed))
              playLocY -= playerSpeed
            //println("up")
          }
          if (e.keyCode == 40) {
            //down
            if (checkBounds(playLocX, playLocY + playerSpeed + 128))
              playLocY += playerSpeed
            //println("down")
          }
          if (e.keyCode == 37) {
            //left
            if (checkBounds(playLocX - playerSpeed, playLocY))
              playLocX -= playerSpeed
            //println("left")
          }
          if (e.keyCode == 39) {
            //right
            if (checkBounds(playLocX + playerSpeed + 128, playLocY))
              playLocX += playerSpeed
            //println("right")
          }
          if (e.keyCode == 87 && timer < 0) {
            //W shoot up
            makeBullet(playLocX + 64, playLocY + 64, 0, -playerBulletSpeed, true)
            timer = 50.0
          }
          if (e.keyCode == 83 && timer < 0) {
            //S shoot down
            makeBullet(playLocX + 64, playLocY + 64, 0, playerBulletSpeed, true)
            timer = 50.0
          }
          if (e.keyCode == 65 && timer < 0) {
            //A shoot left
            makeBullet(playLocX + 64, playLocY + 64, -playerBulletSpeed, 0, true)
            timer = 50.0
          }
          if (e.keyCode == 68 && timer < 0) {
            //D shoot right
            makeBullet(playLocX + 64, playLocY + 64, playerBulletSpeed, 0, true)
            timer = 50.0
          }

        }
      }

      updateBullets()
      val r = scala.util.Random
      if (r.nextInt(100) + 1 < 10 + enemySpeed) {
        var dir = r.nextInt(4) + 1
        if (dir == 1) makeBullet(eneLocX + 64, eneLocY + 64, enemyBulletSpeed, 0, false)
        else if (dir == 2) makeBullet(eneLocX + 64, eneLocY + 64, -enemyBulletSpeed, 0, false)
        else if (dir == 3) makeBullet(eneLocX + 64, eneLocY + 64, 0, enemyBulletSpeed, false)
        else makeBullet(eneLocX + 64, eneLocY + 64, 0, -enemyBulletSpeed, false)
      }

      val num = r.nextInt(4) + 1
      if (num == 1) {
        if (eneLocX < 1300) eneLocX += enemySpeed
      } else if (num == 2) {
        if (eneLocX > 50) eneLocX -= enemySpeed
      } else if (num == 3) {
        if (eneLocY < 500) eneLocY += enemySpeed
      } else {
        if (eneLocY > 50) eneLocY -= enemySpeed
      }

      timer -= playerSpeed
      changeGameState(hand)
      
    }
  }

  def changeGameState(hand: SetIntervalHandle): Unit = {
    if (gameState == 2) {

      openLossWindow()
      println(gameState)
      js.timers.clearInterval(hand)

    } else if (gameState == 1) {
      openWinWindow(myTamago)
      
      println(gameState)
      js.timers.clearInterval(hand)

    }
  }

  /* else {

    if (gameState == 2) {
      openLossWindow()
      println(gameState)
      println(gameRunning)
    } else if (gameState == 1) {
      openWinWindow(myTamago)
      println(gameState)
      println(gameRunning)
    }
  } */

  def updateBullets() {
    playerBullets.foreach(bull => {
      bull.xPos += bull.xVel
      bull.yPos += bull.yVel
      bull.isActive = checkBounds(bull.xPos, bull.yPos)
      if (!bull.isActive) { playerBullets -= bull }
      bull.intersects = collisionHandler(bull, eneLocX, eneLocY)
      if (bull.intersects) {
        playerBullets -= bull
        enemyHealth -= (playerAtt / (enemyDef / 2))
        println("enemy " + enemyHealth)
      }
    })

    enemyBullets.foreach(bull => {
      bull.xPos += bull.xVel
      bull.yPos += bull.yVel
      bull.isActive = checkBounds(bull.xPos, bull.yPos)
      if (!bull.isActive) { enemyBullets -= bull }
      bull.intersects = collisionHandler(bull, playLocX, playLocY)
      if (bull.intersects) {
        enemyBullets -= bull
        playerHealth -= (enemyAtt / (playerDef / 2))
        println("player " + playerHealth)
      }
    })
  }
  //true == player false == enemy
  def checkBounds(posX: Double, posY: Double): Boolean = {
    return (posX >= 0 && posX <= width && posY >= 0 && posY <= height)
  }

  def collisionHandler(bull: Bullet, xEne: Double, yEne: Double): Boolean = {
    if (bull.xPos >= xEne &&
      bull.xPos <= xEne + 128 &&
      bull.yPos >= yEne &&
      bull.yPos <= yEne + 128) {
      true
    } else { false }

  }

  def makeBullet(xp: Double, yp: Double, xv: Double, yv: Double, playerShot: Boolean) {

    if (playerShot) {
      playerBullets += shoot(xp, yp, xv, yv)
    } else {
      enemyBullets += shoot(xp, yp, xv, yv)
    }
  }

  class Bullet(var xPos: Double, var yPos: Double, var xVel: Double, var yVel: Double, var isActive: Boolean, var intersects: Boolean) {
    this.xPos = xPos
    this.yPos = yPos
    this.xVel = xVel
    this.yVel = yVel
    this.isActive = isActive
    this.intersects = intersects
  }
  def shoot(xp: Double, yp: Double, xv: Double, yv: Double): Bullet = {

    new Bullet(xp, yp, xv, yv, true, false)
  }

  def openWinWindow(t: TamagoData): Unit = {
    $("#main-body").append($("<div id=\"page-mask\"></div>"))
    $("#main-body").append($(winWindowStr))
    updateCoins(playerHealth.toInt + enemyAtt.toInt + enemyDef.toInt + enemySpeed.toInt, myTamago) 
    $("#fightAgain").click(() =>  Battle.pageSetup(t))
    $("#Quit").click(() => { closeWindow(); isActive = false; ProfilePage.pageSetup() })
  }

  def openLossWindow(): Unit = {
    $("#main-body").append($("<div id=\"page-mask\"></div>"))
    $("#main-body").append($(lossWindowStr))
    updateHealth(myTamago)
    $("#ToTheStore").click(() => Store.pageSetup())
    $("#Quit").click(() => { closeWindow(); isActive = false; ProfilePage.pageSetup() })
  }

  def closeWindow(): Unit = {
    $("#window").remove()
    $("#page-mask").remove()

  }
  
  def updateCoins(amt: Int,t:TamagoData) {
    $.getJSON("/updateCoins/" + amt, success = (o, s, j) => {
      Player.coins += amt
    })
    $("#window").append($(s"<p class='center'>${amt} coin(s) have been added to you account.</p>"))
    
  }
  
  def updateHealth(t:TamagoData) {
    $.getJSON("/updateHealth/" + t.id, success = (o, s, j) => {
      val tCopy = t
      val newT = TamagoData(t.id, t.name, t.attack,t.defense,t.speed, 1 , t.kneesbroken,t.level,t.isclean,t.isalive,t.age,t.respect,t.timeskneesbroken)
      Player.tamagos = Player.tamagos.filter(tg => t != tg)
      Player.tamagos ::= newT
      
    $("#window").append($(s"<p class='center'>Your Health is Depleted!.</p>"))
    })
  }
  

  val str = """
    <span>
  
      
    <div id = "versus"> </div>
		<canvas id="fightNight" width="1400" height="600"></canvas>
		<br>
		<br>
	
</span>
  """
  val winWindowStr = """
  <div id = "window" class = "center">
  <h3> YOU WON </h3>
  <p> Congratulations on proving that you are worth more than worthless scum. </p>
  <p id = "wonCoins"></p>
  <br>
  <div class = "center">
  <button type = "button" id = "fightAgain" class = "button inline">Fight Again</button>
  <button type = "button" id = "Quit" class = "button inline">Quit</button>
  """

  val lossWindowStr = """
  <div id = "window" class = "center">
  <h1> R.I.P </h1>
  <p> Youre tomago is severely injured. </p>
  <p> You must go to the shop and fix him or choose another tomago and attempt to get revenge </p>
  <br>
  <div class = "center">
  <button type = "button" id = "ToTheStore" class = "button inline">Store</button>
  <button type = "button" id = "Quit" class = "button inline">Quit</button>
  """
}
