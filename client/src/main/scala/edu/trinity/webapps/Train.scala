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
import org.scalajs.dom.raw.HTMLImageElement

object Train {
 
  var canvas = null.asInstanceOf[Canvas]
  var ctx = null.asInstanceOf[dom.CanvasRenderingContext2D]
  
  //train homepage
  def pageSetup(t: TamagoData):Unit = { 
    $(canvas).remove()
    $("#main-body").empty()
    $("#main-body").append($(str))
    $("#trainAttack").click(() => openTrainAttack(t))
    $("#trainDefense").click(() => openTrainDefense(t))
    $("#trainSpeed").click(() => openTrainSpeed(t))
    $("#backToPet").click(() => {
      $(canvas).remove()
      CurrentPet.pageSetup(t)
    })
    canvas = dom.document.createElement("canvas").asInstanceOf[Canvas]
    ctx = canvas.getContext("2d").asInstanceOf[dom.CanvasRenderingContext2D]
    canvas.width = (1 * dom.window.innerWidth).toInt
    canvas.height = (1 * dom.window.innerHeight).toInt
  //  $("#main-body").append(canvas)
    dom.document.body.appendChild(canvas)
  }
  
  //attack game homepage
  def openTrainAttack(t: TamagoData):Unit = {
    $("#trainAttack").remove
    $("#trainDefense").remove
    $("#trainSpeed").remove
    $("#window").remove
    $("#header").text("Attack")
    $("#main-body").append($(openTrainAttackStr))
    $("#play").click(() => speedPlay(t))
    $("#back").click(() => pageSetup(t))
  }
  
  //defense game homepage
  def openTrainDefense(t: TamagoData):Unit = {
    $("#trainAttack").remove
    $("#trainDefense").remove
    $("#trainSpeed").remove
    $("#window").remove
    $("#header").text("Defense")
    $("#main-body").append($(openTrainDefenseStr))
    $("#play").click(() => defensePlay(t))
    $("#back").click(() => pageSetup(t))
  }
  
  //speed game homepage
  def openTrainSpeed(t: TamagoData):Unit = {
    $("#trainAttack").remove
    $("#trainDefense").remove
    $("#trainSpeed").remove
    $("#window").remove
    $("#header").text("Speed")
    $("#main-body").append($(openTrainSpeedStr))
    $("#play").click(() => attackPlay(t))
    $("#back").click(() => pageSetup(t))
  }
  

  
  var intervalCount = 1
  
  var canJump = true
  
  dom.window.onkeydown = (e: dom.KeyboardEvent) => {
	    if ((e.keyCode == 32) && (playAttack == true) && (canJump == true)) {  
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
  
  dom.window.onkeyup = (e: dom.KeyboardEvent) => {
    if ((e.keyCode == 37) && (playSpeed == true)) {  
      left = false
    }
    if((e.keyCode == 39) && (playSpeed == true)) {
      right = false
	  }
    if ((e.keyCode == 37) && (playDefense == true)) {  
      dLeft = false
    }
    if((e.keyCode == 39) && (playDefense == true)) {
      dRight = false
    }
  }
  
  def showTamago(t: TamagoData, x: Double, y: Double): Unit = {
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
        println("Error inside client/Train.scala")
      }
    }
    ctx.drawImage(image, x, y)
  }
  
  
  var r = scala.util.Random
  var rMax = (((dom.window.innerWidth).toInt)*0.75).toInt
  var rMin = (((dom.window.innerWidth).toInt)*0.25).toInt
  
  //DONE speed game (originally attack game but I switched them but didn't change names)
  var playAttack = false
  
  var space = false
  var up = false
  var attackScore = 0 
  
  var tamaY = 675 //tamago y position
  
  var obsOneX = (1 * dom.window.innerWidth).toDouble //obstacle one x position
  var oneIntersect = (1*dom.window.innerWidth).toDouble
  while (oneIntersect > 150) { oneIntersect -= 1.5 }
  
  var obsTwoX = (1 * dom.window.innerWidth).toDouble //obstacle two x position
  var twoIntersect = (1*dom.window.innerWidth).toDouble
  while (twoIntersect > 150) { twoIntersect -= 1.75 }

  var obsThreeX = (1 * dom.window.innerWidth).toDouble //obstacle three x position
  var threeIntersect = (1*dom.window.innerWidth).toDouble
  while (threeIntersect > 150) { threeIntersect -= 2 }
  
  def attackPlay(t: TamagoData):Unit = {
    $("#play").remove
    $("#back").remove
    $("#instructions").remove
    $("#gameOver").remove
    $("#window").remove
    playAttack = true
    tamaY = 675
    obsOneX = (1 * dom.window.innerWidth).toDouble
    obsTwoX = (1 * dom.window.innerWidth).toDouble
    obsThreeX = (1 * dom.window.innerWidth).toDouble
    space = false
    up = false
    attackScore = 0
    intervalCount += 1
    dom.window.setInterval(() => attack(t), 3)
  }
   
  def endAttack(t: TamagoData) = {
    dom.window.clearInterval(intervalCount)
  	ctx.clearRect(0, 0, canvas.width, canvas.height)
  	$("#main-body").append($(gameOverStr))
  	$("#gameOver").text("final score: " + attackScore)
  	updateCoins(attackScore, t)
  	updateSpeed(attackScore, t)
  	$("#play").click(() => attackPlay(t))
    $("#back").click(() => pageSetup(t))
  }
  
  def attack(t: TamagoData) = {
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
	    showTamago(t, 50, tamaY)
  	  if (space == true && up == true && tamaY <= 675 && tamaY > 575) { 
  	    tamaY -= 1 
  	  } 
    	else if (tamaY == 575) {
    		up = false
    		tamaY += 1
    	} 
    	else if (space == true && up == false && tamaY > 350 && tamaY != 675) { tamaY += 1; } 
    	else if (space == true && tamaY == 676) {
    		tamaY = 675
  		  space = false
  	  }	
	    
	    if (tamaY > 670) { canJump = true }
	    else canJump = false 
  	  
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
      	
    	//intersection
    	if (tamaY >= 625 && ((obsOneX < oneIntersect && obsOneX > oneIntersect-50) || (obsTwoX == twoIntersect) || (obsThreeX == threeIntersect))) {
  		  playAttack = false
    	}
	  } else if (playAttack == false) {
	      endAttack(t)
	  }
  }

  
  //DONE defense game
  var playDefense = false
  
  var defenseScore = 0
  
  var dLeft = false
  var dRight = false
  var dTamaX = ((dom.window.innerWidth).toDouble)/2
  
  var dObsOneY = -10.0
  var dObsOneXTwo = r.nextInt(rMax-rMin)+rMin
  var dObsOneDead = false
  
  var dObsTwoY = -100.0
  var dObsTwoXTwo = r.nextInt(rMax-rMin)+rMin
  var dObsTwoDead = false
  
  var dObsThreeY = -150.0
  var dObsThreeXTwo = r.nextInt(rMax-rMin)+rMin
  var dObsThreeDead = false
  
  def defensePlay(t: TamagoData):Unit = {
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
    dObsTwoY = -100.0
    dObsThreeDead = false
    dObsThreeY = -150.0
    dom.window.setInterval(() => defense(t), 3)
  }
  
  def endDefense(t: TamagoData) = {
      dom.window.clearInterval(intervalCount)
    	ctx.clearRect(0, 0, canvas.width, canvas.height)
    	$("#main-body").append($(gameOverStr))
    	$("#gameOver").text("final score: " + defenseScore)
    	updateCoins(defenseScore, t)
    	updateDefense(defenseScore, t)
    	$("#play").click(() => defensePlay(t))
      $("#back").click(() => pageSetup(t))
   }
  
  def defense(t: TamagoData) = {
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
  	  showTamago(t, dTamaX, 675)
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
        if (dTamaX-30 <= dObsOneXTwo && dTamaX+120 >= dObsOneXTwo && dObsOneY >= 700) { 
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
          if (dTamaX-30 <= dObsTwoXTwo && dTamaX+120 >= dObsTwoXTwo && dObsTwoY >= 700) { 
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
          if (dTamaX-30 <= dObsThreeXTwo && dTamaX+120 >= dObsThreeXTwo && dObsThreeY >= 700) { 
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
      endDefense(t)
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
  
  def speedPlay(t: TamagoData):Unit = {
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
    dom.window.setInterval(() => speed(t), 3)
  }
  
  
  def endSpeed(t: TamagoData) = {
    dom.window.clearInterval(intervalCount)
  	ctx.clearRect(0, 0, canvas.width, canvas.height)
  	$("#main-body").append($(gameOverStr))
    $("#gameOver").text("final score: " + speedScore)
    updateCoins(speedScore, t)
    updateAttack(speedScore, t)
    $("#play").click(() => speedPlay(t))
    $("#back").click(() => pageSetup(t))
  }
  
  var weaponLoc = 0.0
  var weaponCount = 0
  
  def speed(t: TamagoData) = {
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
      if (left == true && tamaX > 0) { tamaX -= 1 } 
      if (right == true && tamaX < ((dom.window.innerWidth).toDouble)-50) { tamaX += 1 }
      if (speedSpace == true) { 
        ctx.beginPath
        ctx.fillStyle = "#F0F3BD"
        if (weaponLoc == 0) { weaponLoc = 700 }
        weaponCount += 1
        ctx.fillRect(tamaX, weaponLoc, 125, 100)
        if (weaponCount == 100 && weaponLoc != 0) { 
           speedSpace = false
           weaponLoc = 0
           weaponCount = 0
        }
      }
      showTamago(t, tamaX, 675)
      
      //obstacle one
      if (obsOneDead == false) {
        ctx.beginPath
      	ctx.arc(obsOneXTwo, obsOneY, 20, 0, Math.PI*2, false)
      	ctx.fillStyle = "#EF8354"
      	ctx.fill
      	ctx.closePath
      	if (obsOneY < 780) { 
      	  obsOneY += 1.5 
      	} else {
      	  if (tamaX > obsOneXTwo) { obsOneXTwo += 2 }
      	  else obsOneXTwo -=2
      	}
        if (weaponLoc != 0 && tamaX-10 <= obsOneXTwo && tamaX+125 >= obsOneXTwo && obsOneY >= 690) { 
      	  speedScore += 1 
      	  obsOneDead = true
        }    	
      }
      if (obsOneDead == true || obsOneY > (dom.window.innerHeight).toInt) {
        obsOneY = -50.0
        obsOneXTwo = r.nextInt(rMax-rMin)+rMin
        obsOneDead = false
      }
      if (tamaX-10 <= obsOneXTwo && tamaX+125 >= obsOneXTwo && weaponLoc == 0 && obsOneY >= 700) { playSpeed = false }
      
      //obstacle two
      if (speedScore >= 2) {
      	if (obsTwoDead == false) {
      	  ctx.beginPath
        	ctx.arc(obsTwoXTwo, obsTwoY, 20, 0, Math.PI*2, false)
        	ctx.fillStyle = "#EF8354"
        	ctx.fill
        	ctx.closePath
        	if (obsTwoY < 780) { 
        	  obsTwoY += 1.5 
        	} else {
        	  if (tamaX > obsTwoXTwo) { obsTwoXTwo += 2 }
        	  else obsTwoXTwo -= 2
        	}
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
      	if (tamaX-10 <= obsTwoXTwo && tamaX+125 >= obsTwoXTwo && weaponLoc == 0 && obsTwoY >= 700) { playSpeed = false }
      }
      
      //obstacle three
      if (speedScore >= 5) {
      	if (obsThreeDead == false) {
      	  ctx.beginPath
        	ctx.arc(obsThreeXTwo, obsThreeY, 20, 0, Math.PI*2, false)
        	ctx.fillStyle = "#EF8354"
        	ctx.fill
        	ctx.closePath
        	if (obsThreeY < 780) { 
        	  obsThreeY += 1.5 
        	} else {
        	  if (tamaX > obsThreeXTwo) { obsThreeXTwo += 2 }
        	  else obsThreeXTwo -= 2
        	}
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
        	if (tamaX-10 <= obsThreeXTwo && tamaX+125 >= obsThreeXTwo && weaponLoc == 0 && obsThreeY >= 700) { playSpeed = false }
        	}
        } else if (playSpeed == false) {
          endSpeed(t)
      }
    }
  
  
  def updateCoins(amt: Int, t:TamagoData) {
    $.getJSON("/updateCoins/" + amt, success = (o, s, j) => {
        Player.coins += amt
    })
    $("#window").append($(s"<p class='center'>${amt} coin(s) have been added to you account.</p>"))
    //$("#window").append($(s"<p class='center'>Your tamago's attack has increased by ${(amt/2).toInt}</p>"))
  }
  
  def updateAttack(amt: Int, t:TamagoData) {
    $.getJSON("/updateAttack/" + t.id + "/" + (amt/2).toInt, success = (o, s, j) => {
      val tCopy = t
      val newT = TamagoData(t.id, t.name, t.attack+(amt/2).toInt, t.defense, 
      t.speed, t.health, 
      t.kneesbroken, t.level, t.isclean, 
      t.isalive, t.age, t.respect, t.timeskneesbroken)
      Player.tamagos = Player.tamagos.filter(tg => t != tg)
      Player.tamagos ::= newT
      val p = s"<p class='center'>Your tamago's attack has increased by ${(amt/2).toInt}</p>"
      $("#window").append($(p))
    })
  }
  
  def updateDefense(amt: Int, t:TamagoData) {
    $.getJSON("/updateDefense/" + t.id + "/" + (amt/2).toInt, success = (o, s, j) => {
      val tCopy = t
      val newT = TamagoData(t.id, t.name, t.attack, t.defense, 
      t.speed+(amt/2).toInt, t.health, 
      t.kneesbroken, t.level, t.isclean, 
      t.isalive, t.age, t.respect, t.timeskneesbroken)
      Player.tamagos = Player.tamagos.filter(tg => t != tg)
      Player.tamagos ::= newT
      $("#window").append($(s"<p class='center'>Your tamago's defense has increased by ${(amt/2).toInt}</p>"))
    })
  }
  
  def updateSpeed(amt: Int, t:TamagoData) {
    $.getJSON("/updateSpeed/" + t.id + "/" + (amt/2).toInt, success = (o, s, j) => {
      val tCopy = t
      val newT = TamagoData(t.id, t.name, t.attack, t.defense, 
      t.speed+(amt/2).toInt, t.health, 
      t.kneesbroken, t.level, t.isclean, 
      t.isalive, t.age, t.respect, t.timeskneesbroken)
      Player.tamagos = Player.tamagos.filter(tg => t != tg)
      Player.tamagos ::= newT
      $("#window").append($(s"<p class='center'>Your tamago's speed has increased by ${(amt/2).toInt}</p>"))
    })
  }
  
  
  //homepage html
  val str = """
  <h2 id="header">Train</h2>
    <div id="window" class="center">
      <div class="smallTop"> </div>
      <h3> Select a Stat to Train </h3> <br>
      
      <div class="center">
        <button id="trainAttack" class="button inline">Train Attack</button>
        <button id="trainDefense" class="button inline">Train Defense</button>
        <button id="trainSpeed" class="button inline">Train Speed</button>
      </div> <br>
      <button id="backToPet" class="button">Back to Profile</button>
    </div>
	<br>
	"""
  
  //attack home html
  val openTrainAttackStr = """
    <div id="window" class="center">
      <div class="smallTop"> </div>
      <h3> Attack Training </h3> <br>
      <p id="instructions" class="center">Instructions: Use arrow keys to move! Use spacebar to attack!</p> <br>
      
      <div class="center">
        <button id="play" class="button inline">Play</button> 
        <button id="back" class="button inline">Back</button>   
      </div>
    </div>
  """
  
  //defense home html
  val openTrainDefenseStr = """
    <div id="window" class="center">
      <div class="smallTop"> </div>
      <h3> Defense Training </h3>
      <p id="instructions" class="center"">Instructions: Catch all of the falling things! Use arrow keys to move!</p> <br> 
      
      <div class="center">
        <button id="play" class="button inline">Play</button>
        <button id="back" class="button inline">Back</button>    
      </div>
    </div>
  """
  
  //speed home html
  val openTrainSpeedStr = """
    <div id="window" class="center">
      <div class="smallTop"> </div>
      <h3> Speed Training </h3>
      <p id="instructions" class="center">Instructions: Don't get hit! Use spacebar to jump!</p> <br>

      <div class="center">
        <button id="play" class="button inline">Play</button>
        <button id="back" class="button inline">Back</button>
      </div>
    </div>
  """
  
  val gameOverStr = """
    <div id="window" class="center">
    <div class="smallTop"> </div>
    <h3>Game Over</h3> <br>

      <button id="play" class="button inline">Play Again</button>
      <button id="back" class="button inline">Back to Train</button>
    </div>
  """
  //<div class = center
}




