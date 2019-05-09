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

object Care {
  
  def pageSetup():Unit = { 
    $("#main-body").empty()
    $("#main-body").append($(str))
  }
  
  //homepage html
  val str = """
	<div class="topnav">
			<a id="profile">Profile</a> 
			<a id="store">Store</a>
      <a id="train">Train</a>
      <a id="care" class="active">Care</a> 
			<a id="help">Help</a>
  </div>
  <h2 id="header">Care</h2>
    
	<br>
	"""
}