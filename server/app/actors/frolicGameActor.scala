package actors

import akka.actor.Props
import akka.actor.ActorRef
import akka.actor.Actor

class frolicGameActor(out: ActorRef, manager: ActorRef) extends Actor {
  import frolicGameActor._

  manager ! frolicManager.NewPlayer(self)
  private var isSetup = false

  def receive = {
    case s: String => {
      //if (isSetup)
      println(s)  
      //else 
      //println("receive Actor: " + s)
      manager ! frolicManager.NewMove(self, s)
    }
    case SendMessage(m) => {
      //println("send Actor: " + m)
      out ! m
    }
    //sender
  }
}

object frolicGameActor {
  def props(out: ActorRef, manager: ActorRef) = Props(new frolicGameActor(out, manager))

  case class SendMessage(m: String)
  //case class SendMessage(m:String)
}