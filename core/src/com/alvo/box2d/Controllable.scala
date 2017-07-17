package com.alvo.box2d

import com.alvo.EntityStore.EntityWithBody
import com.alvo.constants.Constants
import com.badlogic.gdx.{Gdx, Input, InputProcessor}

/**
  * Created by alvo on 08.07.17.
  */
abstract class Controllable(val entity: EntityWithBody) extends InputProcessor {
  override def touchUp(screenX: Int, screenY: Int, pointer: Int, button: Int): Boolean = false

  override def touchDown(screenX: Int, screenY: Int, pointer: Int, button: Int): Boolean = false

  override def keyUp(keycode: Int): Boolean = false

  override def scrolled(amount: Int): Boolean = false

  override def touchDragged(screenX: Int, screenY: Int, pointer: Int): Boolean = false

  override def keyDown(keycode: Int): Boolean = false

  override def mouseMoved(screenX: Int, screenY: Int): Boolean = false

  override def keyTyped(character: Char): Boolean = false

  def handleInput(): Unit
}

trait AccelerometerControllable extends Controllable {
  val scaleVelocity: Float => Float = accValue => -accValue * Constants.ACCELEROMETER_SCALE
  
  override def handleInput(): Unit = {
    val scaledVelocityX = scaleVelocity(Gdx.input.getAccelerometerX)
    val scaledVelocityY = scaleVelocity(Gdx.input.getAccelerometerY)
    entity.body.setLinearVelocity(scaledVelocityX, scaledVelocityY + Constants.ACCELEROMETER_CALIBRATION_HEIGHT)
    Gdx.app.log("AccelerometerControllable", s"velocity: [x:$scaledVelocityX y:$scaledVelocityY]")
  }
}

trait KeyboardControllable extends Controllable {
  val entityImpulseForce = Constants.ENTITY_IMPULSE_FORCE

  override def handleInput(): Unit = {
    if (Gdx.input.isKeyPressed(Input.Keys.W)) {
      entity.body.applyLinearImpulse(0.0f, entityImpulseForce, 0.0f, 0.0f, true)
      Gdx.app.log("KeyboardControllable", "Key W pressed")
    }
    if (Gdx.input.isKeyPressed(Input.Keys.D)) {
      entity.body.applyLinearImpulse(entityImpulseForce, 0.0f, 0.0f, 0.0f, true)
      Gdx.app.log("KeyboardControllable", "Key D pressed")
    }
    if (Gdx.input.isKeyPressed(Input.Keys.A)) {
      entity.body.applyLinearImpulse(-entityImpulseForce, 0.0f, 0.0f, 0.0f, true)
      Gdx.app.log("KeyboardControllable", "Key A pressed")
    }
    if (Gdx.input.isKeyPressed(Input.Keys.S)) {
      entity.body.applyLinearImpulse(0.0f, -entityImpulseForce, 0.0f, 0.0f, true)
      Gdx.app.log("KeyboardControllable", "Key S pressed")
    }
  }
}