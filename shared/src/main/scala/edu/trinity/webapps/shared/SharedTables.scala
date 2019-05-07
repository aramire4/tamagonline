package edu.trinity.webapps.shared

import play.api.libs.json._
import play.api.libs.functional.syntax._
import play.api.libs.json.Json._
import java.sql._
import java.util._
import org.joda.time.DateTime
import java.text.SimpleDateFormat


object SharedTables {
  
  case class TamagoData(name: String, attack: Int, defense: Int, 
      speed: Int, health: Int, 
      kneesbroken: Boolean, level: Int, isclean: Boolean, 
      isalive: Boolean, age: Int, respect: Int, timeskneesbroken: Int)
  
       /** Entity class storing rows of table Player
   *  @param id Database column id SqlType(INT), AutoInc, PrimaryKey
   *  @param username Database column username SqlType(VARCHAR), Length(200,true)
   *  @param password Database column password SqlType(VARCHAR), Length(200,true)
   *  @param coins Database column coins SqlType(INT)
   *  @param debt Database column debt SqlType(INT)
   *  @param kills Database column kills SqlType(INT)
   *  @param deaths Database column deaths SqlType(INT)
   *  @param globalrank Database column globalRank SqlType(INT)
   *  @param score Database column score SqlType(INT)
   *  @param numberoftamagos Database column numberOfTamagos SqlType(INT) */
  case class PlayerData(id: Int, username: String, coins: Int, 
      debt: Int, kills: Int, deaths: Int, 
      globalrank: Int, score: Int, numberoftamagos: Int)
      
  /** Entity class storing rows of table Post
   *  @param id Database column id SqlType(INT), AutoInc, PrimaryKey
   *  @param playerid Database column playerID SqlType(INT)
   *  @param time Database column time SqlType(TIMESTAMP)
   *  @param messsage Database column messsage SqlType(VARCHAR), Length(240,true) */
  case class PostData(id:Int, uname:String, time: String, messsage: String)
  
  /** Entity class storing rows of table Postcomment
   *  @param id Database column id SqlType(INT), AutoInc, PrimaryKey
   *  @param message Database column message SqlType(VARCHAR), Length(240,true)
   *  @param time Database column time SqlType(TIMESTAMP)
   *  @param playerid Database column playerID SqlType(INT)
   *  @param postid Database column postID SqlType(INT) */
  case class PostcommentData(uname:String, message: String, time: String, postid: Int)
  
   /**
   * This allows for the reading of option types.
   */
  implicit def optionFormat[T: Format]: Format[Option[T]] = new Format[Option[T]] {
    override def reads(json: JsValue): JsResult[Option[T]] = json.validateOpt[T]

    override def writes(o: Option[T]): JsValue = o match {
      case Some(t) ⇒ implicitly[Writes[T]].writes(t)
      case None ⇒ JsNull
    }
  }
  
  implicit object timestampFormat extends Format[Timestamp] {
    val format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SS'Z'")
    def reads(json: JsValue) = {
      val str = json.as[String]
      JsSuccess((new Timestamp(format.parse(str).getTime)))
    }
    def writes(ts: Timestamp) = JsString(format.format(ts))
  }
  
  implicit def timeStampFormat: Format[java.sql.Timestamp] = new Format[java.sql.Timestamp] {
    override def reads(json: JsValue): JsResult[java.sql.Timestamp] = json.validate[Timestamp]
    
    override def writes(t: java.sql.Timestamp): JsValue = implicitly[Writes[String]].writes(t.toString())
    
  }
      
  
/*
  implicit val tamagoWrites = Json.writes[TamagoRow]
  implicit val tamagoReads = Json.reads[TamagoRow]
  implicit val playerReads = Json.reads[PlayerRow]
  implicit val playerWrites = Json.writes[PlayerRow]
*/
  implicit val playerRowWrites: Writes[PlayerData] = (
    (JsPath \ "id").write[Int] and
    (JsPath \ "username").write[String] and
    (JsPath \ "coins").write[Int] and
    (JsPath \ "debt").write[Int] and
    (JsPath \ "kills").write[Int] and
    (JsPath \ "deaths").write[Int] and
    (JsPath \ "globalrank").write[Int] and
    (JsPath \ "score").write[Int] and
    (JsPath \ "numberoftamagos").write[Int])(unlift(PlayerData.unapply))

  implicit val playerRowReads: Reads[PlayerData] = (
    (JsPath \ "id").read[Int] and
    (JsPath \ "username").read[String] and
    (JsPath \ "coins").read[Int] and
    (JsPath \ "debt").read[Int] and
    (JsPath \ "kills").read[Int] and
    (JsPath \ "deaths").read[Int] and
    (JsPath \ "globalrank").read[Int] and
    (JsPath \ "score").read[Int] and
    (JsPath \ "numberoftamagos").read[Int])(PlayerData.apply _)

  implicit val tamagoRowWrites: Writes[TamagoData] = (
    (JsPath \ "name").write[String] and
    (JsPath \ "attack").write[Int] and
    (JsPath \ "defense").write[Int] and
    (JsPath \ "speed").write[Int] and
    (JsPath \ "health").write[Int] and
    (JsPath \ "kneesbroken").write[Boolean] and
    (JsPath \ "level").write[Int] and
    (JsPath \ "isclean").write[Boolean] and
    (JsPath \ "isalive").write[Boolean] and
    (JsPath \ "age").write[Int] and
    (JsPath \ "respect").write[Int] and
    (JsPath \ "timeskneesbroken").write[Int])(unlift(TamagoData.unapply))

  implicit val tamagoRowReads: Reads[TamagoData] = (
    (JsPath \ "name").read[String] and
    (JsPath \ "attack").read[Int] and
    (JsPath \ "defense").read[Int] and
    (JsPath \ "speed").read[Int] and
    (JsPath \ "health").read[Int] and
    (JsPath \ "kneesbroken").read[Boolean] and
    (JsPath \ "level").read[Int] and
    (JsPath \ "isclean").read[Boolean] and
    (JsPath \ "isalive").read[Boolean] and
    (JsPath \ "age").read[Int] and
    (JsPath \ "respect").read[Int] and
    (JsPath \ "timeskneesbroken").read[Int])(TamagoData.apply _)
    

}
