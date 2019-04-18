package edu.trinity.webapps.controllers

import javax.inject._

import edu.trinity.webapps.shared.SharedMessages
import play.api.mvc._

@Singleton
class Tamago @Inject()(cc: ControllerComponents) extends AbstractController(cc) {

   def battle = Action {
    Ok(views.html.battle())
  }
  
  def care = Action {
    Ok(views.html.care())
  }
  
  def train = Action {
    Ok(views.html.train())
  }
  
  def frolic = Action {
    Ok(views.html.frolic())
  }
}
