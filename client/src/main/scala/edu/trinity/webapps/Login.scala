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

  def pageSetup(): Unit = {
    $("#main-body").empty()
    $("#main-body").append($(s"<div id=${"login-page"}></div>"))
    $("#login-page").append($(str))
    $("#login").click(() => checkLogin())
    $("#create").click(() => createPlayer())
  }

  def checkLogin(): Unit = {
    $.getJSON("/checkLogin/" + $("#usernameBox").value() + "/" + $("#passwordBox").value(), success = (o, s, j) => {
      val isValid = Json.parse(js.JSON.stringify(o)).as[Boolean]
      if (isValid) ProfilePage.pageSetup()
      else $("#main-body").append($("<p class=\"center\">Incorrect username or password</p>"))
    })
  }

   def createPlayer():Unit = {
      $.getJSON("/newPlayer/" + $("#usernameBox").value() + "/" + $("#passwordBox").value, success = (o, s, j) => {
      val b = Json.parse(js.JSON.stringify(o)).as[Boolean]
      if (b) ProfilePage.pageSetup()
      else $("#main-body").append("<p class=\"center\">Sorry, that username is already in use.</p>")
    }) 
   }

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
				<br> 
			</form>
      <button type="button" class="button inline" id="login">Login</button>
      <button type="button" class="button inline" id="create">Create Account</button>
		</div>
	  </body>
  </span>"""

}