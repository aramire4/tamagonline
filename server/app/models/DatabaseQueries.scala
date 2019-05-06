package models

import slick.jdbc.MySQLProfile.api._
import scala.concurrent.duration.Duration
import scala.concurrent.Await
import scala.concurrent.Future
import Tables._
import scala.concurrent.ExecutionContext
import edu.trinity.webapps.shared.SharedTables._

object DatabaseQueries {

  //make a function that converts rows to datas, make sure the model only spits out shareable data
  def rowToData(tr: TamagoRow): TamagoData = {
    TamagoData(tr.name, tr.attack, tr.defense, tr.speed, tr.health, tr.kneesbroken, tr.level,
      tr.isclean, tr.isalive, tr.age, tr.respect, tr.timeskneesbroken)
  }

  def rowToData(pr: PlayerRow): PlayerData = {
    PlayerData(pr.id, pr.username, pr.password, pr.coins, pr.deaths, pr.kills, pr.deaths,
      pr.globalrank, pr.score, pr.numberoftamagos)
  }

  def tamagosOfPlayerID(db: Database, pid: Int)(implicit ec: ExecutionContext): Future[Seq[TamagoData]] = {
    db.run {
      (for (t <- Tamago; if t.ownerid === pid) yield t).result.map(_.map(tr => rowToData(tr)))
    }
  }

  def checkLogin(db: Database, uname: String, pword: String)(implicit ec: ExecutionContext): Future[Option[Int]] = {
    db.run {
      (for (p <- Player; if p.username === uname; if p.password === pword) yield p).result.map(s => {
        s.headOption match {
          case Some(p) => Some(p.id)
          case None => None
        }
      })
    }
  }

  def newPlayer(db: Database, uname: String, pword: String)(implicit ec: ExecutionContext): Future[Option[Int]] = {
    val fut = db.run {
      (for (p <- Player; if p.username === uname) yield p).result
    }
    fut.flatMap(s => {
      s.headOption match {
        case Some(_) => Future(None)
        case None => {
          val id = db.run {
            val p = PlayerRow(0, uname, pword, 30, 30, 0, 0, 0, 0, 0)
            val id = (Player returning Player.map(_.id)) += p
            id
          }
          id.flatMap(i => Future(Some(i)))
        }
      }
    })
  }

  def playerOfUsername(db: Database, uname: String)(implicit ec: ExecutionContext): Future[Seq[PlayerData]] = {
    db.run {
      (for (p <- Player; if p.username === uname) yield p).result.map(_.map(pr => rowToData(pr)))
    }
  }

  def timetest(db: Database)(implicit ec: ExecutionContext): Future[Seq[java.sql.Timestamp]] = {
    db.run {
      (for (t <- Tamago) yield t.lastfed).result
    }
  }
}