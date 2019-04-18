package edu.trinity.webapps.controllers

import javax.inject._

import edu.trinity.webapps.shared.SharedMessages
import play.api.mvc._

@Singleton
class Application @Inject()(cc: ControllerComponents) extends AbstractController(cc) {

  def login = Action {
    Ok(views.html.login())
  }
  
  def playCenter = Action {
    Ok(views.html.playCenter())
  }
  
  def profile = Action {
    Ok(views.html.profile())
  }
  
  def help = Action {
    Ok(views.html.help())
  }
  
  def shop = Action {
    Ok(views.html.shop())
  }

}
