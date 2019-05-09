package actors

import akka.actor.Actor
import akka.actor.ActorRef
import scala.collection.mutable.ArrayBuffer
import scala.collection.mutable.Map

class frolicManager extends Actor{
  import frolicManager._

  var moves = List[String]()
  var players = Map[ActorRef, (Int, Int)]()

  def receive = {
    case NewPlayer(np) => {
      players += (np -> (400, 300));
      self ! Send
    }

    case ClearPlayer(np) => {
      players -= np;
    }

    case NewMove(p, nm) => {
      var arr = nm.split(", ")
      var x = arr(0).toInt
      var y = arr(1).toInt
      players(p) = (x, y)
      self ! Send
    }

    case Send => {
      var str = ""
      for ((act, (num1, num2)) <- players) {
        str += (num1 + ", " + num2 + ", ")
      }

      for (p <- players.keys) {
        p ! frolicGameActor.SendMessage(str)
      }
    }

    case ClearAll => {
      players = Map.empty
    }

    case m => println("Unhandled move in socketGameManager: " + m)
  }
}

object frolicManager {
  case class NewPlayer(np: ActorRef)
  case class ClearPlayer(np: ActorRef)
  case class NewMove(p: ActorRef, c: String)
  case object Send
  case object ClearAll
}