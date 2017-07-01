package com.alvo.box2d

import com.alvo.screens.GameScreen
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType
import com.badlogic.gdx.physics.box2d._

/**
  * Created by alvo on 01.07.17.
  */
sealed trait Entity
final case class Circle(radius: Float) extends Entity
final case class Rectangle(width: Float, height: Float) extends Entity
case object EmptyEntity extends Entity

sealed trait Character extends Entity
case object Chief extends Character

trait EntityBuilder[A] {

  protected val density: Float = 1.0f
  protected val friction: Float = 1.0f
  protected val restitution: Float = 1.0f

  def build(entity: A): Body

  protected def createBodyDef(bodyType: BodyType): BodyDef = {
    val bodyDef = new BodyDef
    bodyDef.`type` = bodyType
    bodyDef
  }

  protected def createFixtureDef(shape: Shape): FixtureDef = {
    val fixtureDef = new FixtureDef
    fixtureDef.density = density
    fixtureDef.friction = friction
    fixtureDef.restitution = restitution
    fixtureDef.shape = shape
    fixtureDef
  }
}

object EntityBuilderInstances {
  implicit val circleEntityBuilder = new EntityBuilder[Circle] {

    override val density = 10.0f
    override val friction = 10.0f
    override val restitution = 10.0f

    override def build(circle: Circle): Body = {
      val circleBody = GameScreen.world.createBody(createBodyDef(BodyType.DynamicBody))
      val shape = new CircleShape
      shape.setRadius(circle.radius)
      circleBody.createFixture(createFixtureDef(shape))
      shape.dispose()
      circleBody
    }
  }

  implicit val rectangleEntityBuilder = new EntityBuilder[Rectangle] {

    override val density = 10.0f
    override val friction = 10.0f
    override val restitution = 10.0f

    override def build(rectangle: Rectangle): Body = {
      val rectangleBody = GameScreen.world.createBody(createBodyDef(BodyType.DynamicBody))
      val shape = new PolygonShape()
      shape.setAsBox(rectangle.height, rectangle.width)
      rectangleBody.createFixture(createFixtureDef(shape))
      shape.dispose()
      rectangleBody
    }
  }
}

object EntityBuilder {
  def buildEntity[A](entity: A)(implicit builder: EntityBuilder[A]): Body = {
    builder.build(entity)
  }
}