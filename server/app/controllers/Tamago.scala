package edu.trinity.webapps.controllers

import javax.inject._

import edu.trinity.webapps.shared.SharedMessages
import play.api.mvc._

@Singleton
class Tamago @Inject()(cc: ControllerComponents) extends AbstractController(cc) {

  def login = Action {
    Ok(views.html.login())
  }
  
  def playCenter = Action {
    Ok(views.html.playCenter())
  }
  
  def profile = Action {
    Ok(views.html.profile())
  }

}
