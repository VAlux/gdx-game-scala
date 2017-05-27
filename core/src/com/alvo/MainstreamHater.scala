package com.alvo

import com.alvo.screens.GameScreen
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.{Game, Gdx}

/**
  * Created by alvo on 2/19/17.
  */
class MainstreamHater(val width: Int = 0, val height: Int = 0, val isDesktop: Boolean = true) extends Game {
  private[alvo] var batch: SpriteBatch = _
  private[alvo] var img: Texture = _

  override def create(): Unit = {

//    val circle = CircleBuilder(0.5f, new Vector2(0.0f, 0.0f), None).createEntity(0.5f, 0.8f, 0.4f)
//    val chief = ChiefBuilder(new Vector2(0.0f, 0.5f), None).createEntity(0.5f, 0.8f, 0.4f)
    setScreen(GameScreen)
  }

  override def render(): Unit = super.render()

  override def dispose(): Unit = super.dispose()
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
    Gdx.app.log("MainstreamHater", "default game instance created.")
    applyInstance(new MainstreamHater())
  }

  def apply(isDesktop: Boolean, width: Int, height: Int): MainstreamHater = {
    Gdx.app.log("MainstreamHater", s"${if(isDesktop) "desktop" else "mobile"} game instance with params width: $width height: $height created.")
    applyInstance(new MainstreamHater(width, height, isDesktop))
  }

  def width: Int = getInstance().width
  def height: Int = getInstance().height
  def isDesktop: Boolean = getInstance().isDesktop
}