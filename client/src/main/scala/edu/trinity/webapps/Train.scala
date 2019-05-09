package edu.trinity.webapps

import org.scalajs.dom
import scala.scalajs.js
import scala.util.Random
import org.scalajs.dom.html.Canvas
import org.querki.jquery._
import edu.trinity.webapps._
import scala.scalajs.js.annotation._
import play.api.libs.json._
import play.api.libs.json.Reads._
import play.api.libs.functional.syntax._
import edu.trinity.webapps.shared.SharedTables._
import scala.collection.mutable.ArrayBuffer

object Train {
 
  //train homepage
  def pageSetup():Unit = { 
    $("#main-body").empty()
    $("#main-body").append($(str))
    $("#trainAttack").click(() => openTrainAttack())
    $("#trainDefense").click(() => openTrainDefense())
    $("#trainSpeed").click(() => openTrainSpeed())
  }
  
  //attack game homepage
  def openTrainAttack():Unit = {
    $("#trainAttack").remove
    $("#trainDefense").remove
    $("#trainSpeed").remove
    $("#window").remove
    $("#header").text("Attack")
    $("#main-body").append($(openTrainAttackStr))
    $("#play").click(() => speedPlay())
    $("#back").click(() => pageSetup())
  }
  
  //defense game homepage
  def openTrainDefense():Unit = {
    $("#trainAttack").remove
    $("#trainDefense").remove
    $("#trainSpeed").remove
    $("#window").remove
    $("#header").text("Defense")
    $("#main-body").append($(openTrainDefenseStr))
    $("#play").click(() => defensePlay())
    $("#back").click(() => pageSetup())
  }
  
  //speed game homepage
  def openTrainSpeed():Unit = {
    $("#trainAttack").remove
    $("#trainDefense").remove
    $("#trainSpeed").remove
    $("#window").remove
    $("#header").text("Speed")
    $("#main-body").append($(openTrainSpeedStr))
    $("#play").click(() => attackPlay())
    $("#back").click(() => pageSetup())
  }
  
  val canvas = dom.document.createElement("canvas").asInstanceOf[Canvas]
  val ctx = canvas.getContext("2d").asInstanceOf[dom.CanvasRenderingContext2D]
  var tamago = null //TODO add tamago sprite?
  
  var intervalCount = 0
  
  dom.window.onkeydown = (e: dom.KeyboardEvent) => {
	    if ((e.keyCode == 32) && (playAttack == true)) {  
        space = true
	      up = true
	    }
      if ((e.keyCode == 37) && (playSpeed == true)) {  
        left = true
        right = false
	    }
	    if((e.keyCode == 39) && (playSpeed == true)) {
	      right = true
	      left = false
	    }
	    if((e.keyCode == 32) && (playSpeed == true)) {
	      speedSpace = true
	    }
	    if ((e.keyCode == 37) && (playDefense == true)) {  
        dLeft = true
        dRight = false
	    }
	    if((e.keyCode == 39) && (playDefense == true)) {
	      dRight = true
	      dLeft = false
	    }
  }
  
  def createGame() = {
    canvas.width = (1 * dom.window.innerWidth).toInt
    canvas.height = (1 * dom.window.innerHeight).toInt
    dom.document.body.appendChild(canvas)
  }
  
  var r = scala.util.Random
  var rMax = (((dom.window.innerWidth).toInt)*0.75).toInt
  var rMin = (((dom.window.innerWidth).toInt)*0.25).toInt
  
  //speed game (originally attack game but I switched them but didn't change names)
  var playAttack = false
  
  var space = false
  var up = false
  var attackScore = 0
  
  var tamaY = 750 //tamago y position
  
  var obsOneX = (1 * dom.window.innerWidth).toDouble //obstacle one x position
  var oneIntersect = (1*dom.window.innerWidth).toDouble
  while (oneIntersect > 100) { oneIntersect -= 1.5 }
  
  var obsTwoX = (1 * dom.window.innerWidth).toDouble //obstacle two x position
  var twoIntersect = (1*dom.window.innerWidth).toDouble
  while (twoIntersect > 100) { twoIntersect -= 1.75 }

  var obsThreeX = (1 * dom.window.innerWidth).toDouble //obstacle three x position
  var threeIntersect = (1*dom.window.innerWidth).toDouble
  while (threeIntersect > 100) { threeIntersect -= 2 }
  
  def attackPlay():Unit = {
    $("#play").remove
    $("#back").remove
    $("#instructions").remove
    $("#gameOver").remove
    $("#window").remove
    playAttack = true
    tamaY = 750
    obsOneX = (1 * dom.window.innerWidth).toDouble
    obsTwoX = (1 * dom.window.innerWidth).toDouble
    obsThreeX = (1 * dom.window.innerWidth).toDouble
    space = false
    up = false
    attackScore = 0
    intervalCount += 1
    createGame
    dom.window.setInterval(() => attack, 3)
  }
   
  def endAttack() = {
    dom.window.clearInterval(intervalCount)
  	ctx.clearRect(0, 0, canvas.width, canvas.height)
  	$("#main-body").append($(gameOverStr))
  	$("#gameOver").text("final score: " + attackScore)
  	$("#play").click(() => attackPlay())
    $("#back").click(() => pageSetup())
  }
  
  def attack() = {
	  if (playAttack == true) {
      ctx.clearRect(0, 0, canvas.width, canvas.height)
    	//background
    	ctx.fillStyle = "#F0F3BD"
    	ctx.fillRect(0, 0, (1 * dom.window.innerWidth).toInt, (1 * dom.window.innerHeight).toInt)
        
    	//line
      ctx.beginPath
      ctx.fillStyle = "#000000"
  	  ctx.moveTo(0, 800)
  	  ctx.lineTo((1 * dom.window.innerWidth).toDouble, 800)
  	  ctx.stroke
  	  ctx.closePath
  	  
  	  //score
     	ctx.font = "32px Arial";
    	ctx.fillStyle = "#000000";
	    ctx.fillText("score: " + attackScore, ((1 * dom.window.innerWidth).toInt)-200, ((1 * dom.window.innerHeight).toInt)-50);              
  	  
  	  //tamago
  	  ctx.beginPath
  	  ctx.fillStyle = "#000000"
  	  ctx.fillRect(50,tamaY,50,50) //TODO tamago
  	  ctx.closePath
  	  if (space == true && up == true && tamaY <= 750 && tamaY > 650) { tamaY -= 1 } 
    	else if (tamaY == 650) {
    		up = false
    		tamaY += 1
    	} 
    	else if (space == true && up == false && tamaY > 350 && tamaY != 750) { tamaY += 1; } 
    	else if (space == true && tamaY == 751) {
    		tamaY = 750
  		  space = false
  	  }	
  	  
  	  //obstacle one
  	  ctx.beginPath
    	ctx.arc(obsOneX, 780, 20, 0, Math.PI*2, false)
    	ctx.fillStyle = "#EF8354"
    	ctx.fill
    	ctx.closePath
    	obsOneX -= 1.5
      if (obsOneX == oneIntersect-1.5) { attackScore += 1 }    	
    	if (obsOneX <= 0) { obsOneX = (1 * dom.window.innerWidth).toDouble } 
    	
    	//obstacle two
    	if (attackScore >= 2) {
      	ctx.beginPath
      	ctx.arc(obsTwoX, 780, 20, 0, Math.PI*2, false)
      	ctx.fillStyle = "#EF8354"
      	ctx.fill
      	ctx.closePath
      	obsTwoX -= 1.75
      	if (obsTwoX == twoIntersect-1.75) { attackScore += 1 }
      	if (obsTwoX <= 0) { obsTwoX = (1 * dom.window.innerWidth).toDouble }
    	}
    	
    	//obstacle three
    	if (attackScore >= 5) {
      	ctx.beginPath
      	ctx.arc(obsThreeX, 780, 20, 0, Math.PI*2, false)
      	ctx.fillStyle = "#EF8354"
      	ctx.fill
      	ctx.closePath
      	obsThreeX -= 2
      	if (obsThreeX == threeIntersect-2) { attackScore += 1 }
      	if (obsThreeX <= 0) { obsThreeX = (1 * dom.window.innerWidth).toDouble }
    	}
      	
    	if (tamaY >= 700 && (obsOneX == oneIntersect || obsTwoX == twoIntersect || obsThreeX == threeIntersect)) {
  		  playAttack = false
    	}
	  } else if (playAttack == false) {
	      endAttack
	  }
  }

  
  //defense game
  var playDefense = false
  
  var defenseScore = 0
  
  var dLeft = false
  var dRight = false
  var dTamaX = ((dom.window.innerWidth).toDouble)/2
  
  var dObsOneY = -10.0
  var dObsOneXTwo = r.nextInt(rMax-rMin)+rMin
  var dObsOneDead = false
  
  var dObsTwoY = -30.0
  var dObsTwoXTwo = r.nextInt(rMax-rMin)+rMin
  var dObsTwoDead = false
  
  var dObsThreeY = -50.0
  var dObsThreeXTwo = r.nextInt(rMax-rMin)+rMin
  var dObsThreeDead = false
  
  def defensePlay():Unit = {
    $("#play").remove
    $("#back").remove
    $("#instructions").remove
    $("#gameOver").remove
    $("#window").remove
    playDefense = true
    defenseScore = 0
    intervalCount += 1
    dLeft = false
    dRight = false
    dObsOneDead = false
    dObsOneY = -10.0
    dObsTwoDead = false
    dObsTwoY = -30.0
    dObsThreeDead = false
    dObsThreeY = -50.0
    createGame
    dom.window.setInterval(() => defense, 3)
  }
  
  def endDefense() = {
      dom.window.clearInterval(intervalCount)
    	ctx.clearRect(0, 0, canvas.width, canvas.height)
    	$("#main-body").append($(gameOverStr))
    	$("#gameOver").text("final score: " + defenseScore)
    	$("#play").click(() => defensePlay())
      $("#back").click(() => pageSetup())
   }
  
  def defense() = {
    if (playDefense == true){
      ctx.clearRect(0, 0, canvas.width, canvas.height)
    	//background
    	ctx.fillStyle = "#F0F3BD"
    	ctx.fillRect(0, 0, (dom.window.innerWidth).toInt, (dom.window.innerHeight).toInt)
        
    	//line
      ctx.beginPath
      ctx.fillStyle = "#000000"
  	  ctx.moveTo(0, 800)
  	  ctx.lineTo((dom.window.innerWidth).toDouble, 800)
  	  ctx.stroke
  	  ctx.closePath
  	  
  	  //score
     	ctx.font = "32px Arial";
    	ctx.fillStyle = "#000000";
	    ctx.fillText("score: " + defenseScore, ((dom.window.innerWidth).toInt)-200, ((dom.window.innerHeight).toInt)-50);              
  	  
  	  //tamago
  	  ctx.beginPath
  	  ctx.fillStyle = "#000000"
  	  ctx.fillRect(dTamaX, 750, 50,50) //TODO tamago
  	  ctx.closePath
  	  if (dLeft == true && dTamaX > 0) { dTamaX -= 1 } 
      if (dRight == true && dTamaX < ((dom.window.innerWidth).toDouble)-50) { dTamaX += 1 }
  	  
  	  //obstacle one
      if (dObsOneDead == false) {
        ctx.beginPath
      	ctx.arc(dObsOneXTwo, dObsOneY, 20, 0, Math.PI*2, false)
      	ctx.fillStyle = "#EF8354"
      	ctx.fill
      	ctx.closePath
      	dObsOneY += 1.5
        if (dTamaX-30 <= dObsOneXTwo && dTamaX+30 >= dObsOneXTwo && dObsOneY >= 700) { 
      	  defenseScore += 1 
      	  dObsOneDead = true
        }    	
      }
      if (dObsOneDead == true) {
        dObsOneY = -50.0
        dObsOneXTwo = r.nextInt(rMax-rMin)+rMin
        dObsOneDead = false
      }
      if (dObsOneY >= 800) { playDefense = false }
      
  	  //obstacle two
      if (defenseScore >= 2){
        if (dObsTwoDead == false) {
          ctx.beginPath
        	ctx.arc(dObsTwoXTwo, dObsTwoY, 20, 0, Math.PI*2, false)
        	ctx.fillStyle = "#EF8354"
        	ctx.fill
        	ctx.closePath
        	dObsTwoY += 1.5
          if (dTamaX-30 <= dObsTwoXTwo && dTamaX+30 >= dObsTwoXTwo && dObsTwoY >= 700) { 
        	  defenseScore += 1 
        	  dObsTwoDead = true
          }    	
        }
        if (dObsTwoDead == true) {
          dObsTwoY = -50.0
          dObsTwoXTwo = r.nextInt(rMax-rMin)+rMin
          dObsTwoDead = false
        }
        if (dObsTwoY >= 800) { playDefense = false }
      }
      
      //obstacle three
      if (defenseScore >= 5) {
        if (dObsThreeDead == false) {
          ctx.beginPath
        	ctx.arc(dObsThreeXTwo, dObsThreeY, 20, 0, Math.PI*2, false)
        	ctx.fillStyle = "#EF8354"
        	ctx.fill
        	ctx.closePath
        	dObsThreeY += 1.5
          if (dTamaX-30 <= dObsThreeXTwo && dTamaX+30 >= dObsThreeXTwo && dObsThreeY >= 700) { 
        	  defenseScore += 1 
        	  dObsThreeDead = true
          }    	
        }
        if (dObsThreeDead == true) {
          dObsThreeY = -50.0
          dObsThreeXTwo = r.nextInt(rMax-rMin)+rMin
          dObsThreeDead = false
        }
        if (dObsThreeY >= 800) { playDefense = false }
      }
      
    } else if (playDefense == false) {
      endDefense
    }
  }

  
  //speed game
  var playSpeed = false
  
  var speedScore = 0
  
  var left = false
  var right = false
  var speedSpace = false
  var tamaX = ((dom.window.innerWidth).toDouble)/2
  
  var obsOneY = -10.0
  var obsOneXTwo = r.nextInt(rMax-rMin)+rMin
  var obsOneDead = false
  
  var obsTwoY = -30.0
  var obsTwoXTwo = r.nextInt(rMax-rMin)+rMin
  var obsTwoDead = false
  
  var obsThreeY = -50.0
  var obsThreeXTwo = r.nextInt(rMax-rMin)+rMin
  var obsThreeDead = false
  
  def speedPlay():Unit = {
    $("#play").remove
    $("#back").remove
    $("#instructions").remove
    $("#gameOver").remove
    $("#window").remove
    playSpeed = true
    intervalCount += 1
    speedScore = 0
    left = false
    right = false
    speedSpace = false
    obsOneDead = false
    obsOneY = -10.0
    obsTwoDead = false
    obsTwoY = -30.0
    obsThreeDead = false
    obsThreeY = -50.0
    weaponLoc = 0.0
    createGame
    dom.window.setInterval(() => speed, 3)
  }
  
  
  def endSpeed() = {
    dom.window.clearInterval(intervalCount)
  	ctx.clearRect(0, 0, canvas.width, canvas.height)
  	$("#main-body").append($(gameOverStr))
    $("#gameOver").text("final score: " + speedScore)
    $("#play").click(() => speedPlay())
    $("#back").click(() => pageSetup())
  }
  
  var weaponLoc = 0.0
  
  def speed() = {
    if (playSpeed == true) {
      ctx.clearRect(0, 0, canvas.width, canvas.height)
    	//background
    ctx.fillStyle = "#F0F3BD"
    ctx.fillRect(0, 0, (dom.window.innerWidth).toInt, (dom.window.innerHeight).toInt)
      
    //line
    ctx.beginPath
    ctx.fillStyle = "#000000"
    ctx.moveTo(0, 800)
    ctx.lineTo((dom.window.innerWidth).toDouble, 800)
    ctx.stroke
    ctx.closePath
    
    //score
    ctx.font = "32px Arial";
    ctx.fillStyle = "#000000";
    ctx.fillText("score: " + speedScore, ((dom.window.innerWidth).toInt)-200, ((dom.window.innerHeight).toInt)-50);              
    
    //tamago
    ctx.beginPath
    ctx.fillStyle = "#000000"
    ctx.fillRect(tamaX, 750, 50,50) //TODO tamago
    ctx.closePath
    if (left == true && tamaX > 0) { tamaX -= 0.5 } 
    if (right == true && tamaX < ((dom.window.innerWidth).toDouble)-50) { tamaX += 0.5 }
    if (speedSpace == true) { 
      ctx.beginPath
      ctx.fillStyle = "#000000"
      if (weaponLoc == 0) { weaponLoc = 725 }
      weaponLoc -= 0.5
      ctx.fillRect(tamaX, weaponLoc, 3, 50)
      if (weaponLoc <= 700 && weaponLoc != 0) { 
         speedSpace = false
         weaponLoc = 0
      }
    }
    
    //obstacle one
    if (obsOneDead == false) {
      ctx.beginPath
    	ctx.arc(obsOneXTwo, obsOneY, 20, 0, Math.PI*2, false)
    	ctx.fillStyle = "#EF8354"
    	ctx.fill
    	ctx.closePath
    	obsOneY += 1.5
      if (weaponLoc <= obsOneY+10 && weaponLoc >= obsOneY-10 && weaponLoc != 0 && tamaX-30 <= obsOneXTwo && tamaX+30 >= obsOneXTwo) { 
    	  speedScore += 1 
    	  obsOneDead = true
      }    	
    }
    if (obsOneDead == true || obsOneY > (dom.window.innerHeight).toInt) {
      obsOneY = -50.0
      obsOneXTwo = r.nextInt(rMax-rMin)+rMin
      obsOneDead = false
    }
    if (tamaX-30 <= obsOneXTwo && tamaX+30 >= obsOneXTwo && weaponLoc == 0 && obsOneY >= 700) { playSpeed = false }
    
    //obstacle two
    if (speedScore >= 2) {
    	if (obsTwoDead == false) {
    	  ctx.beginPath
      	ctx.arc(obsTwoXTwo, obsTwoY, 20, 0, Math.PI*2, false)
      	ctx.fillStyle = "#EF8354"
      	ctx.fill
      	ctx.closePath
      	obsTwoY += 1.5
        if (weaponLoc <= obsTwoY+10 && weaponLoc >= obsTwoY-10 && weaponLoc != 0 && tamaX-30 <= obsTwoXTwo && tamaX+30 >= obsTwoXTwo) { 
      	  speedScore += 1 
      	  obsTwoDead = true
        }    	
    	}
    	if (obsTwoDead == true || obsTwoY > (dom.window.innerHeight).toInt) {
    	  obsTwoY = -50.0
        obsTwoXTwo = r.nextInt(rMax-rMin)+rMin
    	  obsTwoDead = false
    	}
    	if (tamaX-30 <= obsTwoXTwo && tamaX+30 >= obsTwoXTwo && weaponLoc == 0 && obsTwoY >= 700) { playSpeed = false }
  }
  
  //obstacle three
  if (speedScore >= 5) {
  	if (obsThreeDead == false) {
  	  ctx.beginPath
    	ctx.arc(obsThreeXTwo, obsThreeY, 20, 0, Math.PI*2, false)
    	ctx.fillStyle = "#EF8354"
        	ctx.fill
        	ctx.closePath
        	obsThreeY += 1.5
          if (weaponLoc <= obsThreeY+10 && weaponLoc >= obsThreeY-10 && weaponLoc != 0 && tamaX-30 <= obsThreeXTwo && tamaX+30 >= obsThreeXTwo) { 
        	  speedScore += 1 
        	  obsThreeDead = true
          }    	
      	}
      	if (obsThreeDead == true || obsThreeY > (dom.window.innerHeight).toInt) {
      	  obsThreeY = -50.0
    	    obsThreeXTwo = r.nextInt(rMax-rMin)+rMin
      	  obsThreeDead = false
      	}
      	if (tamaX-30 <= obsThreeXTwo && tamaX+30 >= obsThreeXTwo && weaponLoc == 0 && obsThreeY >= 700) { playSpeed = false }
    	}
    } else if (playSpeed == false) {
      endSpeed
    }
  }
  
  //homepage html
  val str = """
	<div class="topnav">
			<a id="profile">Profile</a> 
			<a id="store">Store</a>
      <a id="train" class="active">Train</a> 
      <a id="care">Care</a>
			<a id="help">Help</a>
  </div>
  <h2 id="header">Train</h2>
    <div id="window">
      <button id="trainAttack" class="button">Train Attack</button>
      <br>
      <button id="trainDefense" class="button">Train Defense</button>
      <br>
      <button id="trainSpeed" class="button">Train Speed</button>
    </div>
	<br>
	"""
  
  //attack home html
  val openTrainAttackStr = """
    <div id="window">
      <button id="play" class="button">Play</button>
      <button id="back" class="button">Back</button>
      <p id="instructions" class="center">Game instructions</p>
    </div>
  """
  
  //defense home html
  val openTrainDefenseStr = """
    <div id="window">
      <button id="play" class="button">Play</button>
      <button id="back" class="button">Back</button>
      <p id="instructions" class="center">Game instructions</p>
    </div>
  """
  
  //speed home html
  val openTrainSpeedStr = """
    <div id="window">
      <button id="play" class="button">Play</button>
      <button id="back" class="button">Back</button>
      <p id="instructions" class="center">Game instructions</p>
    </div>
  """
  
  val gameOverStr = """
    <div id="window">
    <p>Game Over</p>
    <p id="gameOver"></p>
    <button id="play" class="button">Play Again</button>
    <br>
    <button id="back" class="button">Back to Train</button>
    </div>
  """
  
}




