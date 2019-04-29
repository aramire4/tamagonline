package models

import slick.jdbc.MySQLProfile.api._
import scala.concurrent.duration.Duration
import scala.concurrent.Await
import scala.concurrent.Future
import Tables._
import scala.concurrent.ExecutionContext
//import edu.trinity.webapps.shared.SharedTables._

//should this be a class or an object?
object DatabaseQueries {
  def tamagosOfPlayerID(db: Database, pid: Int)
  (implicit ec: ExecutionContext): Future[Seq[TamagoRow]] = {
    db.run {
      (for (t <- Tamago; if t.ownerid === pid) yield t).result
    }
  }
  
  
  def playerOfUsername(db: Database, uname: String)(implicit ec: ExecutionContext): Future[Seq[PlayerRow]] = {
    db.run {
      (for (p <- Player; if p.username === uname) yield p).result
    }
  }
}