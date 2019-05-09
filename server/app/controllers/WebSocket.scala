package controllers

import javax.inject._
import play.api.mvc._
import play.api.libs.streams.ActorFlow
import akka.actor.ActorSystem
import akka.stream.Materializer
import actors._
import akka.actor.Props

class WebSocket @Inject()(cc: ControllerComponents)(implicit system: ActorSystem, mat: Materializer) extends AbstractController(cc) {
  val manager = system.actorOf(Props[frolicManager], "manager")
  /*
  def index = Action { implicit request =>
    Ok(views.html.SocketView())
  }
  */
  def socket = WebSocket.accept[String, String] { request =>
    ActorFlow.actorRef { out =>
      frolicGameActor.props(out, manager)
    }
  }
  /*
  def clear = Action {
    manager ! frolicManager.ClearAll
    Redirect(routes.WebSocket.index())
  }
  */
}