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
    PlayerData(pr.id, pr.username, pr.coins, pr.debt, pr.kills, pr.deaths,
      pr.globalrank, pr.score, pr.numberoftamagos)
  }
  
  def fetchPlayerData(db:Database, field:String, uid:Int)(implicit ec:ExecutionContext):Future[String] = {
    val futP = db.run((for(p <- Player; if p.id === uid) yield p).result.head)
        
    val res = futP.map(p => field match {
      case "coins" => p.coins
      case "debt" => p.debt
      case _ => println("error in fetchPlayerData field matching")
    }).map(_.toString)
    res
  }

  def submitLoan(db:Database, amt:Int, pid:Int)(implicit ec:ExecutionContext):Future[Int] = {
    val currentDebt = db.run { 
      (for (p<-Player; if p.id===pid) yield p.debt).result.head
    }
    currentDebt.map(cd => {
      val newDebt = cd + ((amt*1.40).ceil).toInt
      db.run{
        val pd = (for (p<-Player; if p.id===pid) yield p.debt)
        pd.update(newDebt)
      }
      newDebt
    })
  }
  
  def coins(db:Database, uid:Int)(implicit ec:ExecutionContext):Future[Int] = {
    val c = db.run((for(p <- Player; if p.id === uid) yield p.coins).result.head)
    c
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

  def playerOfID(db: Database, pid:Int)(implicit ec: ExecutionContext): Future[PlayerData] = {
    db.run {
      (for (p <- Player; if p.id === pid) yield p).result.head
    }.map(pr => rowToData(pr))
  }

  def timetest(db: Database)(implicit ec: ExecutionContext): Future[Seq[java.sql.Timestamp]] = {
    db.run {
      (for (t <- Tamago) yield t.lastfed).result
    }
  }
}