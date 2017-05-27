package com.alvo.box2d.entities

import com.badlogic.gdx.graphics.g2d.Sprite
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType
import com.badlogic.gdx.physics.box2d._

/**
  * Created by alvo on 31.03.17.
  */
abstract class EntityBuilder[A <: Entity] {

  import com.alvo.box2d.world.GameWorld.physicalWorldComponent

  protected val world: World = implicitly[World]
  
  def createEntity(density: Float, friction: Float, restitution: Float): A

  def createEntity(): A = createEntity(0.0f, 0.0f, 0.0f)

  def createBodyDefinition(position: Vector2, bodyType: BodyType): BodyDef = {
    val bodyDefinition = new BodyDef
    bodyDefinition.position.set(position)
    bodyDefinition.`type` = bodyType
    bodyDefinition
  }

  def createFixtureDefinition(shape: Shape, density: Float, friction: Float, restitution: Float): FixtureDef = {
    val fixtureDefinition = new FixtureDef
    fixtureDefinition.shape = shape
    fixtureDefinition.density = density
    fixtureDefinition.friction = friction
    fixtureDefinition.restitution = restitution
    fixtureDefinition
  }

  def createBody(world: World, bodyDef: BodyDef): Body = {
    world.createBody(bodyDef)
  }

  def createFixture(world: World, body: Body, fixtureDef: FixtureDef): Fixture = {
    body.createFixture(fixtureDef)
  }
}

sealed case class CircleBuilder(radius: Float, position: Vector2, sprite: Option[Sprite]) extends EntityBuilder[Circle] {
  override def createEntity(density: Float, friction: Float, restitution: Float): Circle = {
    val shape = new CircleShape()
    val fixtureDef = createFixtureDefinition(shape, density, friction, restitution)
    val bodyDef = createBodyDefinition(position, BodyDef.BodyType.DynamicBody)
    val body = createBody(world, bodyDef)
    val fixture = createFixture(world, body, fixtureDef)

    sprite foreach body.setUserData

    Circle(radius)(PhysicalEntityComponent(body, fixture, bodyDef, fixtureDef, shape, sprite))
  }
}

sealed case class RectangleBuilder(position: Vector2, width: Int, height: Int, sprite: Option[Sprite]) extends EntityBuilder[Rectangle] {
  override def createEntity(density: Float, friction: Float, restitution: Float): Rectangle = {
    val shape = new PolygonShape()
    val fixtureDef = createFixtureDefinition(shape, density, friction, restitution)
    val bodyDef = createBodyDefinition(position, BodyDef.BodyType.DynamicBody)
    val body = createBody(world, bodyDef)
    val fixture = createFixture(world, body, fixtureDef)

    sprite foreach body.setUserData

    Rectangle(width, height)(PhysicalEntityComponent(body, fixture, bodyDef, fixtureDef, shape, sprite))
  }
}

sealed case class ChiefBuilder(position: Vector2, sprite: Option[Sprite]) extends EntityBuilder[Chief]  {
  override def createEntity(density: Float, friction: Float, restitution: Float): Chief = {
    val circle = CircleBuilder(4.5f, position, sprite).createEntity(density, friction, restitution)
    //chief - specific physical body settings:
    circle.physicalEntityComponent.bodyDef.fixedRotation = true
    circle.physicalEntityComponent.bodyDef.gravityScale = 0.0f
    circle.physicalEntityComponent.body.setLinearDamping(0.5f)
    circle.physicalEntityComponent.body.setAngularDamping(0.5f)

    Chief(circle)
  }
}