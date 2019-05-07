package edu.trinity.webapps.controllers

import javax.inject._
import play.api.mvc._
import scala.concurrent.Future
import models._
import edu.trinity.webapps.shared.SharedTables._

import play.api.db.slick.DatabaseConfigProvider
import play.api.db.slick.HasDatabaseConfigProvider

import slick.jdbc.JdbcProfile
import scala.concurrent.ExecutionContext

import slick.jdbc.MySQLProfile.api._ // This line determines what type of database you are connecting to.
import play.api.libs.json.Json

@Singleton
class DBController @Inject() (protected val dbConfigProvider: DatabaseConfigProvider, cc: ControllerComponents)(implicit ec: ExecutionContext)
  extends AbstractController(cc) with HasDatabaseConfigProvider[JdbcProfile] {

  def battle = Action {
    Ok(views.html.battle())
  }

  def care = Action {
    Ok(views.html.care())
  }

  def train = Action {
    Ok(views.html.train())
  }

  def frolic = Action {
    Ok(views.html.frolic())
  }
  
  def checkLogin(uname:String, pword:String) = Action.async { implicit request =>
    DatabaseQueries.checkLogin(db, uname, pword).map(opt => opt match {
      case Some(pid) => Ok(Json.toJson(true)).withSession("pid" -> pid.toString)
      case None => Ok(Json.toJson(false))
    })
  }
  
  def playerInfo() = Action.async { implicit request =>
    request.session.get("pid").map(pid => {
      DatabaseQueries.playerOfID(db, pid.toInt).map(pd => Ok(Json.toJson(pd)))
    }).getOrElse(Future(Ok(views.html.disconnected())))
  }
  
  def fetchPlayerData(field:String) = Action.async { implicit request =>
    request.session.get("pid").map(pid => {
      DatabaseQueries.fetchPlayerData(db, field, pid.toInt).map(data => Ok(Json.toJson(data)))
    }).getOrElse(Future(Ok(views.html.disconnected())))
  }
  
  def coins() = Action.async { implicit request =>
    request.session.get("pid").map(pid => {
      DatabaseQueries.coins(db, pid.toInt).map(data => Ok(Json.toJson(data)))
    }).getOrElse(Future(Ok(views.html.disconnected())))
  }
  
  def newPlayer(uname:String, pword:String) = Action.async { implicit request =>
    DatabaseQueries.newPlayer(db, uname, pword).map(opt => opt match {
      case Some(newId) => Ok(Json.toJson(true)).withSession("pid" -> newId.toString)
      case None => Ok(Json.toJson(false))
    })
  }
  
  def newTamago(name:String) = Action.async { implicit request =>
    request.session.get("pid").map(pid => {
      DatabaseQueries.newTamago(db, pid.toInt, name).map(b => Ok(Json.toJson(b)))
    }).getOrElse(Future(Ok(views.html.disconnected())))
  }
  
  def submitLoan(amt:Int) = Action.async { implicit request =>
    request.session.get("pid").map(pid => {
      DatabaseQueries.submitLoan(db, amt, pid.toInt).map(debt => Ok(Json.toJson(debt)))
    }).getOrElse(Future(Ok(views.html.disconnected())))
  }
  
  def submitLoanPayment(amt:Int) = Action.async { implicit request =>
    request.session.get("pid").map(pid => {
      DatabaseQueries.submitLoanPayment(db, amt, pid.toInt).map(valid => Ok(Json.toJson(valid)))
    }).getOrElse(Future(Ok(views.html.disconnected())))
  }
  
  def tamagoInfo() = Action.async { implicit request =>
    request.session.get("pid").map{ pid =>
      DatabaseQueries.tamagosOfPlayerID(db, pid.toInt).map(trs => Ok(Json.toJson(trs)))
    }.getOrElse(Future(Ok(views.html.disconnected())))
  }
  
  def startApp = Action { 
    Ok(views.html.scalajsTest())
  }
  
  //Don't worry about this
  

  def tesTime = Action.async { implicit request =>
    val res = DatabaseQueries.timetest(db)
    res.foreach(println)
    res.map(t => Ok(views.html.battle()))
  }

  def testTamagos = Action.async { implicit request =>
    val res = DatabaseQueries.tamagosOfPlayerID(db, 1)
    res.map(t => Ok(Json.toJson(t)))
  }

}

