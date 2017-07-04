package com.alvo.box2d

import com.alvo.screens.GameScreen
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType
import com.badlogic.gdx.physics.box2d._

/**
  * Created by alvo on 01.07.17.
  * Contains building mechanisms for creating and managing box2d primitives
  */
sealed trait Entity
final case class Circle(radius: Float) extends Entity
final case class Rectangle(width: Float, height: Float) extends Entity
final case class Polygon(vertices: Array[Float]) extends Entity

sealed trait Character extends Entity
case class Chief() extends Character
case class Food() extends Character

final case class PhysicalEntityProperties(
  density: Float = 10.0f,
  friction: Float = 10.0f,
  restitution: Float = 10.0f,
  position: Vector2 = new Vector2(0.0f, 0.0f)
)

trait EntityBuilder[A] {

  def build(entity: A, properties: PhysicalEntityProperties): Body

  protected def createBodyDef(bodyType: BodyType): BodyDef = {
    val bodyDef = new BodyDef
    bodyDef.`type` = bodyType
    bodyDef
  }

  protected def createFixtureDef(shape: Shape, properties: PhysicalEntityProperties): FixtureDef = {
    val fixtureDef = new FixtureDef
    fixtureDef.density = properties.density
    fixtureDef.friction = properties.friction
    fixtureDef.restitution = properties.restitution
    fixtureDef.shape = shape
    fixtureDef
  }
}

object EntityBuilderInstances {
  implicit val circleEntityBuilder = new EntityBuilder[Circle] {
    override def build(circle: Circle, properties: PhysicalEntityProperties): Body = {
      val circleBody = GameScreen.world.createBody(createBodyDef(BodyType.DynamicBody))
      val shape = new CircleShape
      circleBody.createFixture(createFixtureDef(shape, properties))
      circleBody.setTransform(properties.position, circleBody.getAngle)
      shape.setRadius(circle.radius)
      shape.dispose()
      circleBody
    }
  }

  implicit val rectangleEntityBuilder = new EntityBuilder[Rectangle] {
    override def build(rectangle: Rectangle, properties: PhysicalEntityProperties): Body = {
      val rectangleBody = GameScreen.world.createBody(createBodyDef(BodyType.DynamicBody))
      val shape = new PolygonShape()
      rectangleBody.createFixture(createFixtureDef(shape, properties))
      rectangleBody.setTransform(properties.position, rectangleBody.getAngle)
      shape.setAsBox(rectangle.height, rectangle.width, properties.position, 0.0f)
      shape.dispose()
      rectangleBody
    }
  }

  implicit val polygonEntityBuilder = new EntityBuilder[Polygon] {
    override def build(polygon: Polygon, properties: PhysicalEntityProperties): Body = {
      val polygonBody = GameScreen.world.createBody(createBodyDef(BodyType.DynamicBody))
      val shape = new PolygonShape()
      polygonBody.createFixture(createFixtureDef(shape, properties))
      polygonBody.setTransform(properties.position, polygonBody.getAngle)
      shape.set(polygon.vertices)
      shape.dispose()
      polygonBody
    }
  }

  implicit val chiefEntityBuilder = new EntityBuilder[Chief] {
    override def build(chief: Chief, properties: PhysicalEntityProperties): Body = {
      EntityBuilder.buildEntity(Circle(20.0f))(PhysicalEntityProperties())
    }
  }

  implicit val foodEntityBuilder = new EntityBuilder[Food] {
    override def build(food: Food, properties: PhysicalEntityProperties): Body = {
      EntityBuilder.buildEntity(Circle(10.0f))(PhysicalEntityProperties())
    }
  }
}

object EntityBuilder {
  def buildEntity[A](entity: A)
                    (properties: PhysicalEntityProperties)
                    (implicit builder: EntityBuilder[A]): Body = {
    Gdx.app.log("EntityBuilder", s"Created entity:  $entity with properties: $properties")
    builder.build(entity, properties)
  }
}