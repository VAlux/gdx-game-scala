package com.alvo

import com.alvo.screens.GameScreen
import com.badlogic.gdx.{Game, Gdx}

/**
  * Created by alvo on 2/19/17.
  */
class MainstreamHater(val width: Int = 0, val height: Int = 0, val isDesktop: Boolean = true) extends Game {
  override def create(): Unit = {
    setScreen(GameScreen)
    Gdx.app.log("MainstreamHater", s"${if(isDesktop) "desktop" else "mobile"} game instance with params width: $width height: $height created.")
  }
}

object MainstreamHater {

  var gameInstance: Option[MainstreamHater] = None

  private def applyInstance(instance: MainstreamHater): MainstreamHater = {
    gameInstance = Some(instance)
    instance
  }

  private def getInstance(): MainstreamHater = {
    gameInstance.getOrElse(applyInstance(new MainstreamHater()))
  }

  def apply: MainstreamHater = {
    applyInstance(new MainstreamHater())
  }

  def apply(isDesktop: Boolean, width: Int, height: Int): MainstreamHater = {
    applyInstance(new MainstreamHater(width, height, isDesktop))
  }

  def width: Int = getInstance().width
  def height: Int = getInstance().height
  def isDesktop: Boolean = getInstance().isDesktop
}