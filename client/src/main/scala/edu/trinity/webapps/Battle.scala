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

object Battle {

  def pageSetup(t: TamagoData): Unit = {
    Player.clearData()
    $("#main-body").empty()
    $("#main-body").append($(str))
    $("#profile").click(() => ProfilePage.pageSetup())
    $("#store").click(() => Store.pageSetup())
    $("#help").click(() => Help.pageSetup())
    $("#toma-chad").click(() => fightNight.pageSetup(t, 1,"Toma-Chad"))
    $("#toma-mark").click(() => fightNight.pageSetup(t, 2,"Toma-Mark Lewis"))
    $("#toma-champ").click(() => fightNight.pageSetup(t, 3, "Toma-Champ"))
    $("#storeKeeper").click(() => fightNight.pageSetup(t, 4, "The Store Keeper"))
    $("#LoanShark").click(() => fightNight.pageSetup(t, 5, "Loan Shark"))
    
        
  }
  
  val str = """
<span>
	<body>
		<div class="topnav">
			<a id="profile">Profile</a> 
			<a id="store">Store</a> 
			<a id="help">Help</a>
    </div>
    <div id="topDivPush"></div>

		<h2>CHOOSE YOUR OPPONENT</h2>

		<ul class = "center">
      <li id = "toma-chad"> toma-chad </li>
      <li id = "toma-mark"> toma-mark </li>
      <li id = "storeKeeper"> The Store Keeper </li>
      <li id = "toma-champ"> Toma-Champ </li>
      <li id = "LoanShark"> Loan Shark </li>
    </ul>
	</body>
</span>"""

}
