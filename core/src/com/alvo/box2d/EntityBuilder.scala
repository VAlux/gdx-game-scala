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
final case class Chain(vertices: Array[Vector2]) extends Entity
final case class Empty() extends Entity

sealed trait Character extends Entity
final case class Chief() extends Character
final case class Food() extends Character

final case class PhysicalEntityProperties(
  density: Float = 1.0f,
  friction: Float = 1.0f,
  restitution: Float = 1.0f,
  position: Vector2 = new Vector2(0.0f, 0.0f),
  bodyType: BodyType = BodyType.DynamicBody,
  gravityScale: Float = 1.0f,
  linearDamping: Float = 0.0f,
  angularDamping: Float = 0.0f,
  fixedRotation: Boolean = false
)

trait EntityBuilder[A] {

  def build(entity: A, properties: PhysicalEntityProperties): Body

  protected def createBody(properties: PhysicalEntityProperties): Body =
    GameScreen.world.createBody(createBodyDef(properties))

  protected def createBodyDef(properties: PhysicalEntityProperties): BodyDef = {
    val bodyDef = new BodyDef
    bodyDef.`type` = properties.bodyType
    bodyDef.gravityScale = properties.gravityScale
    bodyDef.linearDamping = properties.linearDamping
    bodyDef.angularDamping = properties.angularDamping
    bodyDef.fixedRotation = properties.fixedRotation
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
      val circleBody = createBody(properties)
      val shape = new CircleShape
      shape.setRadius(circle.radius)
      circleBody.createFixture(createFixtureDef(shape, properties))
      circleBody.setTransform(properties.position, circleBody.getAngle)
      shape.dispose()
      circleBody
    }
  }

  implicit val rectangleEntityBuilder = new EntityBuilder[Rectangle] {
    override def build(rectangle: Rectangle, properties: PhysicalEntityProperties): Body = {
      val rectangleBody = createBody(properties)
      val shape = new PolygonShape
      shape.setAsBox(rectangle.width, rectangle.height, properties.position, 0.0f)
      rectangleBody.createFixture(createFixtureDef(shape, properties))
      rectangleBody.setTransform(properties.position, rectangleBody.getAngle)
      shape.dispose()
      rectangleBody
    }
  }

  implicit val polygonEntityBuilder = new EntityBuilder[Polygon] {
    override def build(polygon: Polygon, properties: PhysicalEntityProperties): Body = {
      val polygonBody = createBody(properties)
      val shape = new PolygonShape
      polygonBody.createFixture(createFixtureDef(shape, properties))
      polygonBody.setTransform(properties.position, polygonBody.getAngle)
      shape.set(polygon.vertices)
      shape.dispose()
      polygonBody
    }
  }

  implicit val chainShapeBuilder = new EntityBuilder[Chain] {
    override def build(entity: Chain, properties: PhysicalEntityProperties): Body = {
      val chainShapeBody = createBody(properties)
      val shape = new ChainShape
      shape.createLoop(entity.vertices)
      chainShapeBody.createFixture(createFixtureDef(shape, properties))
      chainShapeBody.setTransform(properties.position, chainShapeBody.getAngle)
      shape.dispose()
      chainShapeBody
    }
  }

  implicit val chiefEntityBuilder = new EntityBuilder[Chief] {
    override def build(chief: Chief, properties: PhysicalEntityProperties): Body = {
      EntityBuilder.buildEntity(Circle(20.0f))(properties)
    }
  }

  implicit val foodEntityBuilder = new EntityBuilder[Food] {
    override def build(food: Food, properties: PhysicalEntityProperties): Body = {
      EntityBuilder.buildEntity(Circle(10.0f))(properties)
    }
  }

  implicit val emptyEntityBuilder = new EntityBuilder[Empty] {
    override def build(entity: Empty, properties: PhysicalEntityProperties): Body = {
      createBody(properties)
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