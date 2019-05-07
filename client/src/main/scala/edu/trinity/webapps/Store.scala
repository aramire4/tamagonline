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

  /*
   *Ask dr. lewis about storing all the data as a var, and loading the data into that var 
   */
  
  
  def pageSetup(): Unit = {
    $("#main-body").empty()
    $("#main-body").append($(str))
    $("#loanButt").click(() => openLoanWindow())
  }

  def openLoanWindow(): Unit = {
    $("#main-body").append($("<div id=\"page-mask\"></div>"))
    $("#main-body").append($(loanStr))
    $.getJSON("/coins", success = (o, s, j) => {
      val coins = Json.parse(js.JSON.stringify(o)).as[Int]
      $("#totalCoins").text("Total coins: " + coins)
    })
    $("#submitLoan").click(() => submitLoan())
    $("#closeWindow").click(() => closeLoanWindow())
  }

  def closeLoanWindow(): Unit = {
    $("#window").remove()
    $("#page-mask").remove()
  }

  def submitLoan(): Unit = {
    val amt = $("#loanAmount").value().toString.trim
    if (amt.matches("""\d+""") && amt.toInt > 0) {
      $.getJSON("/submitLoan/" + amt, success = (o, s, j) => {
        val totalDebt = Json.parse(js.JSON.stringify(o)).as[Int]
        $("#totalDebt").text("Total debt: " + totalDebt)
        $("#window").append {
          s"<p>Congrats, you now owe me ${(amt.toInt * 1.40).ceil.toInt} coins (40% interest rate).</p>"
        }
        $("#window").append($(s"<p>${amt} coins have been added to you account.</p>"))
      })

    } else $("#window").append("<p>Give me a positive integer value or get lost!</p>")
    $("#loanAmount").value("")
  }

  //<canvas id="petCenter" width="1400" height="600"style="border: 3px solid"></canvas>

  val loanStr = """
    <div id="window">
      <p>Need some money, eh? Well you've come to the right place.</p>
      <p>I'm the loan shark, fucc wit me and I'll break your legs!</p>
      <p>Hopefully load a loan shark img inside here.</p>
      <p>How many coins shall I loan you? $<input type="text" id="loanAmount"></input></p>
      <p><button type="button" id="submitLoan">Confirm Loan</button></p>
      <p id="totalCoins"></p>
      <p id="totalDebt"></p>
      <button id="closeWindow">Close</button>
    </div>"""

  val str = """<body>
		<div class="topnav">
			<a href="profile">Profile</a> 
			<a href="playCenter">Current Pet</a> 
			<a href="shop" class="active">Shop</a> 
			<a href="help">Help</a>
		</div>
		<h2>Store</h2>
		<button id="loanButt">Get Loan</button>
		<br>
	</body>"""
}