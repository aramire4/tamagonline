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

  val maxNumOfTamagos = 18
  val tamagoCost = 100

  def pageSetup(): Unit = {
    $("#main-body").empty()
    $("#main-body").append($(str))
    $("#loanButt").click(() => openLoanWindow())
    $("#payLoanButt").click(() => openPayLoanWindow())
    $("#buyTamago").click(() => openBuyTamagoWindow())
    $("#profile").click(() => ProfilePage.pageSetup())
    $("#help").click(() => Help.pageSetup())
    $("#logout").click(() => Login.pageSetup())
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
          $("#payMssg").text(s"Congrats, you've paid off ${amt.toInt} coins.")
          $("#amntPaid").text(s"${amt} coins have been removed from your account.")
        } else {
          $("#payMssg").text("You don't have enough money for that!")
          $("#amntPaid").text("")
        }
      })
    } else {
      $("#payMssg").text("Give me a positive integer value or get lost!")
      $("#amntPaid").text("")
    }
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
        $("#amntOwed").text(s"Congrats, you now owe me ${(amt.toInt * 1.40).ceil.toInt} coins (40% interest rate).")
        $("#amntAdded").text(s"${amt} coins have been added to you account.")
      })
    } else {
      $("#amntOwed").text("Give me a positive integer value or get lost!")
      $("#amntAdded").text("")
    }
    $("#loanAmount").value("")
  }

  def submitAdopt(): Unit = {
    if (Player.numberOfTamagos >= 18) {
      $("#succAdopt").text("You own too many tamagos already! Try getting one killed :p")
      $("#costAdopt").text("")
    }
    else {
      val name = $("#tamagoName").value()
      $.getJSON("/newTamago/" + name.trim(), success = (o, s, j) => {
        val isValid = Json.parse(js.JSON.stringify(o)).as[Option[TamagoData]]
        isValid match {
          case Some(td) => {
            $("#succAdopt").replaceWith($("<p>Congrats on your new baby.</p>"))
            $("#costAdopt").replaceWith($(s"<p>${tamagoCost} coins have been removed from you account</p>"))
            Player.tamagos ::= td
            Player.coins -= tamagoCost
          }
          case None => {
            $("#succAdopt").text("You're too poor to buy a tamago! Try getting a loan :)")
            $("#costAdopt").text("")
          }
        }
      })
    }
  }

  val adoptStr = """
    <div id="window">
      <h3> Adopt Tamago </h3> <br>
      <p>Bring home a new tamago! New Tamagos cost """ + tamagoCost + """ coins</p> <br>
      <p>What will you name it? <input type="text" id="tamagoName"></input></p>
      <br>

      <div class ="center">
        <button type="button" class="button inline" id="submitAdopt">Confirm Adoption</button>
        <button id="closeWindow" class="button inline">Close</button>
      </div>
      <p id="succAdopt"></p>
      <p id="costAdopt"></p>
    </div>"""

  val payLoanStr = """
    <div id="window">
      <h3> Pay Off Loan </h3> <br>
      <p>Finally ready to pay me back, eh?</p>
      <p>I'm the loan shark, mess wit me and I'll break your legs!</p>
      <p>How much of your debt shall you repay? $<input type="text" id="payAmount"></input></p>
      
      <p id="totalCoins"></p>
      <p id="totalDebt"></p>  
      <br>

      <div class ="center">
        <button type="button" class="button inline" id="submitLoan">Confirm Payment</button>
        <button id="closeWindow" class="button inline">Close</button>
      </div>
      <p id="payMssg"></p>
      <p id="amntPaid"></p>
    </div>"""

  val loanStr = """
    <div id="window" class="center">
      <h3> Get Loan </h3> <br>
      <p>Need some money, eh? Well you've come to the right place.</p>
      <p>I'm the loan shark, mess wit me and I'll break your legs!</p>
      <p>How many coins shall I loan you? $<input type="text" id="loanAmount"></input></p>
      
      <p id="totalCoins"></p>
      <p id="totalDebt"></p>
      <br>

      <div class ="center">
        <button type="button" id="submitLoan" class="button inline">Confirm Loan</button>
        <button id="closeWindow" class="button inline">Close</button>
  
      </div>
       <p id="amntOwed"></p>
      <p id="amntAdded"></p>
    </div>"""

  val str = """
	  <div class="topnav">
			<a id="profile">Profile</a> 
			<a id="store" class="active">Store</a> 
			<a id="help">Help</a>
      <a id="logout">Logout</a>
    </div>

    <div id="topDivPush"></div>

		<h2>Store</h2>
    
    <div class="center">
      <button id="buyTamago" class="button">Adopt Tamago</button> <br> <br>
      <button id="loanButt" class="button">Get Loan</button> <br> <br>
      <button id="payLoanButt" class="button">Pay Off Loan</button> 
    </div>
	<br>
	"""
}
