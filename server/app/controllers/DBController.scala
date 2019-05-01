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
  
  def playerInfo(uname:String) = Action.async { implicit request =>
    val res = DatabaseQueries.playerOfUsername(db, uname)
    res.map(p => Ok(Json.toJson(p)))
  }
  
  def tamagoInfo(pid:Int) = Action.async { implicit request =>
    val res = DatabaseQueries.tamagosOfPlayerID(db, pid)
    res.map(t => Ok(Json.toJson(t)))
  }
  
  def startApp = Action { 
    Ok(views.html.scalajsTest())
  }
  
  //Don't worry about this
  def testPlayers = Action.async { implicit request =>
    val res = DatabaseQueries.playerOfUsername(db, "barnabus")
    res.map(prods => Ok(Json.toJson(prods)))
  }

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

