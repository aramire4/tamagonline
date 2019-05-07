package edu.trinity.webapps

import edu.trinity.webapps.shared.SharedTables._

object Player {
  
  var username = ""
  var coins = 0
  var debt = 0
  var kills = 0
  var deaths = 0
  var globalrank = 0
  var score = 0
  var numberOfTamagos = 0
  var tamagos:List[TamagoData] = List()
  
  def kd:Int = kills/deaths
  
  def addData(pd:PlayerData):Unit = {
   
    this.username = pd.username
    this.coins = pd.coins
    this.debt = pd.debt
    this.kills = pd.kills
    this.deaths = pd.deaths
    this.globalrank = pd.globalrank
    this.score = pd.score
    this.numberOfTamagos = pd.numberoftamagos
  }
}