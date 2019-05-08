package edu.trinity.webapps

import org.scalajs.dom
import scala.scalajs.js
import org.querki.jquery._

import scala.scalajs.js.annotation._
import play.api.libs.json._
import play.api.libs.json.Reads._
import play.api.libs.functional.syntax._
import edu.trinity.webapps.shared.SharedTables._

//note to self - query stuff is a post/get, meaning you can keep sessions with that?!!??

object Help {

  def pageSetup(): Unit = {
    $("#main-body").empty()
    $("#main-body").append($(str))
    $("#profile").click(() => ProfilePage.pageSetup())
    $("#store").click(() => Store.pageSetup())
  }

  val str = """
<span>
	<body>
		<div class="topnav">
			<a id="profile">Profile</a> 
			<a id="store">Store</a> 
			<a id="help" class="active">Help</a>
    </div>

		<h2>Help</h2> <br>
		<div class="ex1 center">
			<h3>What is Tamagonline</h3> 
			<p class="center">Tamagonline is a website in which you can look
				after virtual pets called Tamagos! Tamagos can do a variety of fun
				activities including battling, frolicing, training, and pooping
				themselves! Look after and train your tamago to make it stronger and
				rise up the leaderboard!</p> <br> <br>
			
      <h3> Getting Started </h3> 
			<p class="center"> If you don't have any tamagos currently, go check out the store! There you can purchase tamago eggs, buy food for your pets, and fix broken kneecaps.
			If you don't have any money you can always take out a loan, but be sure to pay it back! </p> <br> <br>
			
      <h3> Not Seeing What You're Looking For? </h3> 
			<p class="center"> You're in luck! We have 24/7 support! For assistance, contact Tomark-Lewis who is avaialbe by email (mlewis(at)trinity.edu) or by phone (210-999-7022). Remember
			to practice online safety and never talk to strangers. Feel free to destroy their tamagos and burn their ashes. </p> <br>		
		</div> 
	</body>
</span>"""

}