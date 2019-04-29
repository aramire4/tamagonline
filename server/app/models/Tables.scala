package models
// AUTO-GENERATED Slick data model
import edu.trinity.webapps.shared.SharedTables._

/** Stand-alone Slick data model for immediate use */
object Tables extends {
  val profile = slick.jdbc.MySQLProfile
} with Tables

/** Slick data model trait for extension, choice of backend or usage in the cake pattern. (Make sure to initialize this late.) */
trait Tables {
  val profile: slick.jdbc.JdbcProfile
  import profile.api._
  import slick.model.ForeignKeyAction
  // NOTE: GetResult mappers for plain SQL are only generated for tables where Slick knows how to map the types of all columns.
  import slick.jdbc.{GetResult => GR}

  /** DDL for all tables. Call .create to execute. */
  lazy val schema: profile.SchemaDescription = Player.schema ++ Post.schema ++ Postcomment.schema ++ Tamago.schema
  @deprecated("Use .schema instead of .ddl", "3.0")
  def ddl = schema

 
  /** GetResult implicit for fetching PlayerRow objects using plain SQL queries */
  implicit def GetResultPlayerRow(implicit e0: GR[Int], e1: GR[String]): GR[PlayerRow] = GR{
    prs => import prs._
    PlayerRow.tupled((<<[Int], <<[String], <<[String], <<[Int], <<[Int], <<[Int], <<[Int], <<[Int], <<[Int], <<[Int]))
  }
  /** Table description of table player. Objects of this class serve as prototypes for rows in queries. */
  class Player(_tableTag: Tag) extends profile.api.Table[PlayerRow](_tableTag, Some("tamagonline"), "player") {
    def * = (id, username, password, coins, debt, kills, deaths, globalrank, score, numberoftamagos) <> (PlayerRow.tupled, PlayerRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = ((Rep.Some(id), Rep.Some(username), Rep.Some(password), Rep.Some(coins), Rep.Some(debt), Rep.Some(kills), Rep.Some(deaths), Rep.Some(globalrank), Rep.Some(score), Rep.Some(numberoftamagos))).shaped.<>({r=>import r._; _1.map(_=> PlayerRow.tupled((_1.get, _2.get, _3.get, _4.get, _5.get, _6.get, _7.get, _8.get, _9.get, _10.get)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))

    /** Database column id SqlType(INT), AutoInc, PrimaryKey */
    val id: Rep[Int] = column[Int]("id", O.AutoInc, O.PrimaryKey)
    /** Database column username SqlType(VARCHAR), Length(200,true) */
    val username: Rep[String] = column[String]("username", O.Length(200,varying=true))
    /** Database column password SqlType(VARCHAR), Length(200,true) */
    val password: Rep[String] = column[String]("password", O.Length(200,varying=true))
    /** Database column coins SqlType(INT) */
    val coins: Rep[Int] = column[Int]("coins")
    /** Database column debt SqlType(INT) */
    val debt: Rep[Int] = column[Int]("debt")
    /** Database column kills SqlType(INT) */
    val kills: Rep[Int] = column[Int]("kills")
    /** Database column deaths SqlType(INT) */
    val deaths: Rep[Int] = column[Int]("deaths")
    /** Database column globalRank SqlType(INT) */
    val globalrank: Rep[Int] = column[Int]("globalRank")
    /** Database column score SqlType(INT) */
    val score: Rep[Int] = column[Int]("score")
    /** Database column numberOfTamagos SqlType(INT) */
    val numberoftamagos: Rep[Int] = column[Int]("numberOfTamagos")
  }
  /** Collection-like TableQuery object for table Player */
  lazy val Player = new TableQuery(tag => new Player(tag))

  
  /** GetResult implicit for fetching PostRow objects using plain SQL queries */
  implicit def GetResultPostRow(implicit e0: GR[Int], e1: GR[java.sql.Timestamp], e2: GR[String]): GR[PostRow] = GR{
    prs => import prs._
    PostRow.tupled((<<[Int], <<[Int], <<[java.sql.Timestamp], <<[String]))
  }
  /** Table description of table post. Objects of this class serve as prototypes for rows in queries. */
  class Post(_tableTag: Tag) extends profile.api.Table[PostRow](_tableTag, Some("tamagonline"), "post") {
    def * = (id, playerid, time, messsage) <> (PostRow.tupled, PostRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = ((Rep.Some(id), Rep.Some(playerid), Rep.Some(time), Rep.Some(messsage))).shaped.<>({r=>import r._; _1.map(_=> PostRow.tupled((_1.get, _2.get, _3.get, _4.get)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))

    /** Database column id SqlType(INT), AutoInc, PrimaryKey */
    val id: Rep[Int] = column[Int]("id", O.AutoInc, O.PrimaryKey)
    /** Database column playerID SqlType(INT) */
    val playerid: Rep[Int] = column[Int]("playerID")
    /** Database column time SqlType(TIMESTAMP) */
    val time: Rep[java.sql.Timestamp] = column[java.sql.Timestamp]("time")
    /** Database column messsage SqlType(VARCHAR), Length(240,true) */
    val messsage: Rep[String] = column[String]("messsage", O.Length(240,varying=true))

    /** Foreign key referencing Player (database name post_ibfk_1) */
    lazy val playerFk = foreignKey("post_ibfk_1", playerid, Player)(r => r.id, onUpdate=ForeignKeyAction.NoAction, onDelete=ForeignKeyAction.Cascade)
  }
  /** Collection-like TableQuery object for table Post */
  lazy val Post = new TableQuery(tag => new Post(tag))

  
  /** GetResult implicit for fetching PostcommentRow objects using plain SQL queries */
  implicit def GetResultPostcommentRow(implicit e0: GR[Int], e1: GR[String], e2: GR[java.sql.Timestamp]): GR[PostcommentRow] = GR{
    prs => import prs._
    PostcommentRow.tupled((<<[Int], <<[String], <<[java.sql.Timestamp], <<[Int], <<[Int]))
  }
  /** Table description of table postComment. Objects of this class serve as prototypes for rows in queries. */
  class Postcomment(_tableTag: Tag) extends profile.api.Table[PostcommentRow](_tableTag, Some("tamagonline"), "postComment") {
    def * = (id, message, time, playerid, postid) <> (PostcommentRow.tupled, PostcommentRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = ((Rep.Some(id), Rep.Some(message), Rep.Some(time), Rep.Some(playerid), Rep.Some(postid))).shaped.<>({r=>import r._; _1.map(_=> PostcommentRow.tupled((_1.get, _2.get, _3.get, _4.get, _5.get)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))

    /** Database column id SqlType(INT), AutoInc, PrimaryKey */
    val id: Rep[Int] = column[Int]("id", O.AutoInc, O.PrimaryKey)
    /** Database column message SqlType(VARCHAR), Length(240,true) */
    val message: Rep[String] = column[String]("message", O.Length(240,varying=true))
    /** Database column time SqlType(TIMESTAMP) */
    val time: Rep[java.sql.Timestamp] = column[java.sql.Timestamp]("time")
    /** Database column playerID SqlType(INT) */
    val playerid: Rep[Int] = column[Int]("playerID")
    /** Database column postID SqlType(INT) */
    val postid: Rep[Int] = column[Int]("postID")

    /** Foreign key referencing Player (database name postComment_ibfk_1) */
    lazy val playerFk = foreignKey("postComment_ibfk_1", playerid, Player)(r => r.id, onUpdate=ForeignKeyAction.NoAction, onDelete=ForeignKeyAction.Cascade)
    /** Foreign key referencing Post (database name postComment_ibfk_2) */
    lazy val postFk = foreignKey("postComment_ibfk_2", postid, Post)(r => r.id, onUpdate=ForeignKeyAction.NoAction, onDelete=ForeignKeyAction.Cascade)
  }
  /** Collection-like TableQuery object for table Postcomment */
  lazy val Postcomment = new TableQuery(tag => new Postcomment(tag))

  
  /** GetResult implicit for fetching TamagoRow objects using plain SQL queries */
  implicit def GetResultTamagoRow(implicit e0: GR[Int], e1: GR[String], e2: GR[Boolean], e3: GR[java.sql.Timestamp]): GR[TamagoRow] = GR{
    prs => import prs._
    TamagoRow.tupled((<<[Int], <<[String], <<[Int], <<[Int], <<[Int], <<[Int], <<[Int], <<[Boolean], <<[Int], <<[Boolean], <<[Boolean], <<[java.sql.Timestamp], <<[Int], <<[Int], <<[Int]))
  }
  /** Table description of table tamago. Objects of this class serve as prototypes for rows in queries. */
  class Tamago(_tableTag: Tag) extends profile.api.Table[TamagoRow](_tableTag, Some("tamagonline"), "tamago") {
    def * = (id, name, ownerid, attack, defense, speed, health, kneesbroken, level, isclean, isalive, lastfed, age, respect, timeskneesbroken) <> (TamagoRow.tupled, TamagoRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = ((Rep.Some(id), Rep.Some(name), Rep.Some(ownerid), Rep.Some(attack), Rep.Some(defense), Rep.Some(speed), Rep.Some(health), Rep.Some(kneesbroken), Rep.Some(level), Rep.Some(isclean), Rep.Some(isalive), Rep.Some(lastfed), Rep.Some(age), Rep.Some(respect), Rep.Some(timeskneesbroken))).shaped.<>({r=>import r._; _1.map(_=> TamagoRow.tupled((_1.get, _2.get, _3.get, _4.get, _5.get, _6.get, _7.get, _8.get, _9.get, _10.get, _11.get, _12.get, _13.get, _14.get, _15.get)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))

    /** Database column id SqlType(INT), AutoInc, PrimaryKey */
    val id: Rep[Int] = column[Int]("id", O.AutoInc, O.PrimaryKey)
    /** Database column name SqlType(VARCHAR), Length(20,true) */
    val name: Rep[String] = column[String]("name", O.Length(20,varying=true))
    /** Database column ownerID SqlType(INT) */
    val ownerid: Rep[Int] = column[Int]("ownerID")
    /** Database column attack SqlType(INT) */
    val attack: Rep[Int] = column[Int]("attack")
    /** Database column defense SqlType(INT) */
    val defense: Rep[Int] = column[Int]("defense")
    /** Database column speed SqlType(INT) */
    val speed: Rep[Int] = column[Int]("speed")
    /** Database column health SqlType(INT) */
    val health: Rep[Int] = column[Int]("health")
    /** Database column kneesBroken SqlType(BIT) */
    val kneesbroken: Rep[Boolean] = column[Boolean]("kneesBroken")
    /** Database column level SqlType(INT) */
    val level: Rep[Int] = column[Int]("level")
    /** Database column isClean SqlType(BIT) */
    val isclean: Rep[Boolean] = column[Boolean]("isClean")
    /** Database column isAlive SqlType(BIT) */
    val isalive: Rep[Boolean] = column[Boolean]("isAlive")
    /** Database column lastfed SqlType(TIMESTAMP) */
    val lastfed: Rep[java.sql.Timestamp] = column[java.sql.Timestamp]("lastfed")
    /** Database column age SqlType(INT) */
    val age: Rep[Int] = column[Int]("age")
    /** Database column respect SqlType(INT) */
    val respect: Rep[Int] = column[Int]("respect")
    /** Database column timesKneesBroken SqlType(INT) */
    val timeskneesbroken: Rep[Int] = column[Int]("timesKneesBroken")

    /** Foreign key referencing Player (database name tamago_ibfk_1) */
    lazy val playerFk = foreignKey("tamago_ibfk_1", ownerid, Player)(r => r.id, onUpdate=ForeignKeyAction.NoAction, onDelete=ForeignKeyAction.Cascade)
  }
  /** Collection-like TableQuery object for table Tamago */
  lazy val Tamago = new TableQuery(tag => new Tamago(tag))
}
