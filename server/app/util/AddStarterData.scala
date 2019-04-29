package util

import slick.jdbc.MySQLProfile.api._
import java.util.concurrent.ConcurrentHashMap
import scala.concurrent.duration.Duration
import scala.concurrent.Await
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
import models._
import Tables._
import java.util.Date
import java.time.LocalDateTime
import java.time.LocalDateTime._
import edu.trinity.webapps.shared.SharedTables._

object AddStarterData extends App {
  LocalDateTime.now().toString()
  val db = Database.forURL("jdbc:mysql://localhost/tamagonline?user=tamagonline&password=BreakTheLegs&nullNamePatternMatchesAll=true&serverTimezone=UTC", user="tamagonline", password="BreakTheLegs", driver="com.mysql.cj.jdbc.Driver")
    Await.result(db.run(DBIO.seq(
      Player.delete,
      Tamago.delete,
      Post.delete,
      Postcomment.delete,
    )), Duration.Inf)
    Await.result(db.run(DBIO.seq(
      sqlu"ALTER TABLE player AUTO_INCREMENT = 1;",
      sqlu"ALTER TABLE tamago AUTO_INCREMENT = 1;"
    )), Duration.Inf)
    Await.result(db.run(DBIO.seq(
        Player += PlayerRow(1, "barnabus", "nutslap", 30, 0, 0, 0, 0,0,0),
        Player += PlayerRow(2, "dijon", "mustard", 30, 0, 0, 0, 0, 0, 0), 

        Tamago ++= Seq(
          TamagoRow(1, "bingo", 1, 10, 10, 10, 10, false, 1, true, true, 
              java.sql.Timestamp.valueOf(LocalDateTime.now()), 1, 1, 0),
          TamagoRow(1, "gizmo", 2, 10, 10, 10, 10, false, 1, true, true, 
              java.sql.Timestamp.valueOf(LocalDateTime.now()), 1, 1, 0),
          TamagoRow(1, "sophie", 1, 10, 10, 10, 10, false, 1, true, true, 
              java.sql.Timestamp.valueOf(LocalDateTime.now()),1, 1, 0))
    )), Duration.Inf)
    db.close()
}

