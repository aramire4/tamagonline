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

  def pageSetup(): Unit = {
    $("#main-body").empty()
    $("#main-body").append($(str))
    $("#loanButt").click(() => openLoanWindow())
    $("#payLoanButt").click(() => openPayLoanWindow())
    $("#buyTamago").click(() => openBuyTamagoWindow())
    $("#profile").click(() => ProfilePage.pageSetup())
    $("#help").click(() => Help.pageSetup())

  }

  def openBuyTamagoWindow(): Unit = {
    $("#main-body").append($("<div id=\"page-mask\"></div>"))
    $("#main-body").append($(adoptStr))
    $("#submitAdopt").click(() => submitAdopt())
    $("#closeWindow").click(() => closeWindow())
  }

  def openPayLoanWindow(): Unit = {
    $("#main-body").append($("<div id=\"page-mask\"></div>"))
    $("#main-body").append($(payLoanStr))
    $("#totalCoins").text("Total coins: " + Player.coins)
    $("#totalDebt").text("Total debt:" + Player.debt)
    $("#submitLoan").click(() => submitLoanPayment())
    $("#closeWindow").click(() => closeWindow())
  }

  def openLoanWindow(): Unit = {
    $("#main-body").append($("<div id=\"page-mask\"></div>"))
    $("#main-body").append($(loanStr))
    $("#totalCoins").text("Total coins: " + Player.coins)
    $("#totalDebt").text("Total debt:" + Player.debt)
    $("#submitLoan").click(() => submitLoan())
    $("#closeWindow").click(() => closeWindow())
  }

  def closeWindow(): Unit = {
    $("#window").remove()
    $("#page-mask").remove()
  }

  def submitLoanPayment(): Unit = {
    val amt = $("#payAmount").value().toString.trim
    if (amt.matches("""\d+""") && amt.toInt > 0) {
      $.getJSON("/submitLoanPayment/" + amt, success = (o, s, j) => {
        val isValid = Json.parse(js.JSON.stringify(o)).as[Boolean]
        if (isValid) {
          Player.debt -= amt.toInt
          Player.coins -= amt.toInt
          $("#totalDebt").text("Total debt: " + Player.debt)
          $("#totalCoins").text("Total coins: " + Player.coins)
          $("#window").append {
            s"<p>Congrats, you've paid off ${amt.toInt} coins.</p>"
          }
          $("#window").append($(s"<p>${amt} coins have been removed from your account.</p>"))
        } else $("#window").append($("<p> You don't have enough money for that!</p>"))
      })
    } else $("#window").append("<p>Give me a positive integer value or get lost!</p>")
    $("#payAmount").value("")
  }

  def submitLoan(): Unit = {
    val amt = $("#loanAmount").value().toString.trim
    if (amt.matches("""\d+""") && amt.toInt > 0) {
      $.getJSON("/submitLoan/" + amt, success = (o, s, j) => {
        //val totalDebt = Json.parse(js.JSON.stringify(o)).as[Int]
        Player.debt += (amt.toInt * 1.40).ceil.toInt
        Player.coins += amt.toInt
        $("#totalDebt").text("Total debt: " + Player.debt)
        $("#totalCoins").text("Total coins: " + Player.coins)
        $("#window").append {
          s"<p>Congrats, you now owe me ${(amt.toInt * 1.40).ceil.toInt} coins (40% interest rate).</p>"
        }
        $("#window").append($(s"<p>${amt} coins have been added to you account.</p>"))
      })
    } else $("#window").append("<p>Give me a positive integer value or get lost!</p>")
    $("#loanAmount").value("")
  }

  def submitAdopt(): Unit = {
    val name = $("#tamagoName").value()
    $.getJSON("/newTamago/" + name.trim(), success = (o, s, j) => {
      val isValid = Json.parse(js.JSON.stringify(o)).as[Boolean]
      if (isValid) {
        $("#window").append($("<p>Congrats on your new baby.</p>"))
        //TODO MAKE SURE THIS CHANGES THE DATABASE
        $("#window").append($("<p>30 coins have been removed from you account</p>"))
      } else $("#window").append($("<p>You're too poor to buy a tamago! Try getting a loan :)</p>"))
    })
  }

  //<canvas id="petCenter" width="1400" height="600"style="border: 3px solid"></canvas>

  val adoptStr = """
    <div id="window">
      <p>Bring home a new tamago!</p>
      <p>Costs 30 coins</p>
      <p>What will you name it? $<input type="text" id="tamagoName"></input></p>
      <p><button type="button" id="submitAdopt">Confirm Adoption</button></p>
      <button id="closeWindow">Close</button>
    </div>"""

  val payLoanStr = """
    <div id="window">
      <p>Finally ready to pay me back, eh?</p>
      <p>I'm the loan shark, fucc wit me and I'll break your legs!</p>
      <p>Hopefully load a loan shark img inside here.</p>
      <p>How much of your debt shall you repay? $<input type="text" id="payAmount"></input></p>
      <p><button type="button" id="submitLoan">Confirm Payment</button></p>
      <p id="totalCoins"></p>
      <p id="totalDebt"></p>
      <button id="closeWindow">Close</button>
    </div>"""

  val loanStr = """
    <div id="window" class="center">
      <h3> Loan Shark </h3>
      <p>Need some money, eh? Well you've come to the right place.</p>
      <p>I'm the loan shark, fucc wit me and I'll break your legs!</p>
      <p>Hopefully load a loan shark img inside here.</p> <br>
      <p>How many coins shall I loan you? $<input type="text" id="loanAmount"></input></p>
      <p><button type="button" id="submitLoan" class="button">Confirm Loan</button></p>
      <p id="totalCoins"></p>
      <p id="totalDebt"></p>
      <button id="closeWindow" class="button">Close</button>
    </div>"""

  val str = """
	<div class="topnav">
			<a id="profile">Profile</a> 
			<a id="store" class="active">Store</a> 
			<a id="help">Help</a>
    </div>
		<h2>Store</h2>
    <button id="payLoanButt">Pay Off Loan</button>
    <button id="buyTamago">Adopt <s>slave</s> Tamago</button>
    <button id="loanButt" class="button center">Get Loan</button>
	<br>
	"""
}
