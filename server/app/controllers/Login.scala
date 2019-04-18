package edu.trinity.webapps.controllers

import javax.inject._

import edu.trinity.webapps.shared.SharedMessages
import play.api.mvc._

@Singleton
class Login @Inject()(cc: ControllerComponents) extends AbstractController(cc) {

  def login = Action { implicit request =>
    Ok(views.html.login())
  }

}
