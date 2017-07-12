package com.alvo.box2d

import com.badlogic.gdx.InputProcessor

/**
  * Created by alvo on 08.07.17.
  */
abstract class Controllable(val entity: Entity) extends InputProcessor {
  override def touchUp(screenX: Int, screenY: Int, pointer: Int, button: Int): Boolean = false

  override def touchDown(screenX: Int, screenY: Int, pointer: Int, button: Int): Boolean = false

  override def keyUp(keycode: Int): Boolean = false

  override def scrolled(amount: Int): Boolean = false

  override def touchDragged(screenX: Int, screenY: Int, pointer: Int): Boolean = false

  override def keyDown(keycode: Int): Boolean = false

  override def mouseMoved(screenX: Int, screenY: Int): Boolean = false

  def handleInput(): Unit
}

trait AccelerometerControllable extends Controllable {
  override def handleInput(): Unit = {
  }
}