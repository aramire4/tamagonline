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
import scala.collection.mutable.ArrayBuffer


object Login {
 
  def pageSetup():Unit = {
   
   val str = """
    <span>  
      <body>
		<div id="topDivPush"></div>
		<div class="center">
			<h1>Tamagonline</h1>
			<form method="get" class="center" id="login-form">
				<!-- "post" -->
				<div class="center">
					<h3 class="inline">Username</h3>
					<input type="text" name="username" id="usernameBox" class="inline">
				</div>
				<br>
				<div class="center">
					<h3 class="inline">Password</h3>
					<input type="password" name="password" id="passwordBox"
						class="inline"><br>
				</div>
				<br> <input type="submit" name="Submit" id="submitButton"
					class="button inline">
			</form>
      <button type="button" class="button inline" id="button">Click here</button>
		</div>
	  </body>
  </span>"""    
   def func():Unit = {
     $.getJSON("/player/"+$("#usernameBox").value(), success = (o,s,j) => {
       for (p <- Json.parse(js.JSON.stringify(o)).as[Array[PlayerData]]) {
         if (p.password == $("#passwordBox").value()) ProfilePage.pageSetup()//$("#topDivPush").append("<div>HEHEHEHE</div>")
         else $("#login-page").append("<h1>Idiot</h1>")
       }
     }) $.j
     
   }
   
   $("#login-page").append($(str))
   $("#button").click(() => func()) 
  }
  
}