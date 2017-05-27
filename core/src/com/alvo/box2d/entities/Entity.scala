package com.alvo.box2d.entities

import com.alvo.constants.Constants
import com.badlogic.gdx.{Gdx, Input, InputProcessor}
import com.badlogic.gdx.graphics.g2d.Sprite
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.physics.box2d._

/**
  * Created by alvo on 2/19/17.
  */
sealed trait Entity {
  def dispose(): Unit
}

sealed trait Controllable extends InputProcessor {
  def handleInput(): Unit
}

case class PhysicalEntityComponent(
  body: Body,
  fixture: Fixture,
  bodyDef: BodyDef,
  fixtureDef: FixtureDef,
  shape: Shape,
  sprite: Option[Sprite])

abstract class PhysicalEntity(val physicalEntityComponent: PhysicalEntityComponent) extends Entity {
  def setPosition(position: Vector2): Vector2 = physicalEntityComponent.bodyDef.position.set(position)

  override def dispose(): Unit = {
    physicalEntityComponent.shape.dispose()
  }
}

//primitives
sealed case class Circle(radius: Float)(component: PhysicalEntityComponent) extends PhysicalEntity(component)
sealed case class Rectangle(width: Int, height: Int)(component: PhysicalEntityComponent) extends PhysicalEntity(component)

//characters
sealed case class Chief(circle: Circle) extends PhysicalEntity(circle.physicalEntityComponent) with Controllable {
  override def handleInput(): Unit = {
    if (Gdx.input.isKeyPressed(Input.Keys.W))
      this.physicalEntityComponent.body.applyLinearImpulse(0.0f, Constants.CHIEF_IMPULSE_FORCE, 0.0f, 0.0f, true)
    if (Gdx.input.isKeyPressed(Input.Keys.D)) 
      this.physicalEntityComponent.body.applyLinearImpulse(Constants.CHIEF_IMPULSE_FORCE, 0.0f, 0.0f, 0.0f, true)
    if (Gdx.input.isKeyPressed(Input.Keys.A)) 
      this.physicalEntityComponent.body.applyLinearImpulse(-Constants.CHIEF_IMPULSE_FORCE, 0.0f, 0.0f, 0.0f, true)
    if (Gdx.input.isKeyPressed(Input.Keys.S)) 
      this.physicalEntityComponent.body.applyLinearImpulse(0.0f, -Constants.CHIEF_IMPULSE_FORCE, 0.0f, 0.0f, true)
  }

  override def keyUp(keycode: Int): Boolean = false
  override def keyTyped(character: Char): Boolean = false
  override def mouseMoved(screenX: Int, screenY: Int): Boolean = false
  override def touchUp(screenX: Int, screenY: Int, pointer: Int, button: Int): Boolean = false
  override def touchDown(screenX: Int, screenY: Int, pointer: Int, button: Int): Boolean = false
  override def scrolled(amount: Int): Boolean = false
  override def touchDragged(screenX: Int, screenY: Int, pointer: Int): Boolean = false
  override def keyDown(keycode: Int): Boolean = false
}

sealed case class Food(circle: Circle) extends PhysicalEntity(circle.physicalEntityComponent)