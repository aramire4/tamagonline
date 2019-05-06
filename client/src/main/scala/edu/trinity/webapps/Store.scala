package edu.trinity.webapps

import org.scalajs.dom
import scala.scalajs.js
import org.querki.jquery._
import edu.trinity.webapps._
import scala.scalajs.js.annotation._
import play.api.libs.json._
import play.api.libs.json.Reads._
import play.api.libs.functional.syntax._
import edu.trinity.webapps.shared.SharedTables._

object Store {
  
  def pageSetup():Unit = {
    $("#main-body").empty()
    $("#main-body").append($(str))
  }
  
  
  
  val str = """<body>
		<div class="topnav">
			<a href="profile">Profile</a> 
			<a href="playCenter">Current Pet</a> 
			<a href="shop" class="active">Shop</a> 
			<a href="help">Help</a>
		</div>
		<h2>Shop</h2>
		<canvas id="petCenter" width="1400" height="600"
			style="border: 3px solid"></canvas>
		<br>
	</body>"""
}