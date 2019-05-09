package models

import slick.jdbc.MySQLProfile.api._
import scala.concurrent.duration.Duration
import scala.concurrent.Await
import scala.concurrent.Future
import Tables._
import scala.concurrent.ExecutionContext
import edu.trinity.webapps.shared.SharedTables._
import java.time.LocalDateTime

object DatabaseQueries {

  val tamagoCost = 100

  //make a function that converts rows to datas, make sure the model only spits out shareable data
  def rowToData(tr: TamagoRow): TamagoData = {
    TamagoData(tr.id, tr.name, tr.attack, tr.defense, tr.speed, tr.health, tr.kneesbroken, tr.level,
      tr.isclean, tr.isalive, tr.age, tr.respect, tr.timeskneesbroken)
  }

  def rowToData(pr: PlayerRow): PlayerData = {
    PlayerData(pr.id, pr.username, pr.coins, pr.debt, pr.kills, pr.deaths,
      pr.globalrank, pr.score, pr.numberoftamagos)
  }

  def fetchPlayerData(db: Database, field: String, uid: Int)(implicit ec: ExecutionContext): Future[String] = {
    val futP = db.run((for (p <- Player; if p.id === uid) yield p).result.head)

    val res = futP.map(p => field match {
      case "coins" => p.coins
      case "debt" => p.debt
      case _ => println("error in fetchPlayerData field matching")
    }).map(_.toString)
    res
  }

  def updateCoins(db:Database, amt:Int, pid:Int)(implicit ec:ExecutionContext) = {
    val currentCoins = db.run {
      (for (p <- Player; if p.id === pid) yield p.coins).result.head
    }
    currentCoins.map(c => {
      val newCoins = c - amt
      db.run {
        val pc = (for (p <- Player; if p.id === pid) yield p.coins)
        pc.update(newCoins)
      }
    })
  }
  
  def submitLoan(db: Database, amt: Int, pid: Int)(implicit ec: ExecutionContext): Future[Int] = {
    val currentData = db.run {
      (for (p <- Player; if p.id === pid) yield (p.debt, p.coins)).result.head
    }
    currentData.map(tup => {
      val newDebt = tup._1 + ((amt * 1.40).ceil).toInt
      val newCoins = tup._2 + (amt)
      db.run {
        val pd = (for (p <- Player; if p.id === pid) yield p.debt)
        pd.update(newDebt)
      }
      db.run {
        val pc = (for (p <- Player; if p.id === pid) yield p.coins)
        pc.update(newCoins)
      }
      newDebt
    })
  }

  def submitLoanPayment(db: Database, amt: Int, pid: Int)(implicit ec: ExecutionContext): Future[Boolean] = {
    val currentDebt = db.run {
      (for (p <- Player; if p.id === pid) yield (p.debt, p.coins)).result.head
    }
    currentDebt.map(tup => {
      if (amt <= tup._2) {
        val newDebt = tup._1 - amt
        val newCoins = tup._2 - amt
        db.run {
          val pd = (for (p <- Player; if p.id === pid) yield p.debt)
          pd.update(newDebt)
        }
        db.run {
          val pc = (for (p <- Player; if p.id === pid) yield p.coins)
          pc.update(newCoins)
        }
        true
      } else false
    })
  }

  def coins(db: Database, uid: Int)(implicit ec: ExecutionContext): Future[Int] = {
    val c = db.run((for (p <- Player; if p.id === uid) yield p.coins).result.head)
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
            val p = PlayerRow(0, uname, pword, tamagoCost, tamagoCost, 0, 0, 0, 0, 0)
            val id = (Player returning Player.map(_.id)) += p
            id
          }
          id.flatMap(i => Future(Some(i)))
        }
      }
    })
  }

  def newTamago(db: Database, pid: Int, name: String)(implicit ec: ExecutionContext): Future[Option[TamagoData]] = {
    val coins = db.run {
      (for (p <- Player; if p.id === pid) yield p.coins).result.head
    }
    coins.flatMap(c => {
      if (c < tamagoCost) Future(None.asInstanceOf[Option[TamagoData]])
      else {
          updateCoins(db, tamagoCost, pid)
          val tid = db.run { (Tamago returning Tamago.map(_.id)) += TamagoRow(0, name, pid, 10, 10, 10, 10, false, 1, true, true,
            java.sql.Timestamp.valueOf(LocalDateTime.now()), 1+scala.util.Random.nextInt(12), 1, 0) }
          val tr = tid.flatMap(id => db.run {
            (for (t <- Tamago; if t.id === id) yield t).result.head
          })
        tr.map(tr => Some(rowToData(tr)))
      }
    })
  }

  def playerOfID(db: Database, pid: Int)(implicit ec: ExecutionContext): Future[PlayerData] = {
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