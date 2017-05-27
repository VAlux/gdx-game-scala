package com.alvo.box2d.world

import com.alvo.box2d.entities.{Chief, ChiefBuilder, Controllable, PhysicalEntity}
import com.alvo.constants.Constants
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.Sprite
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.physics.box2d.World

/**
  * Created by alvo on 06.04.17.
  */
object GameWorld {
  implicit val physicalWorldComponent = new World(new Vector2(0.0f, -9.8f), true)

  private var physicalEntities: Vector[PhysicalEntity] = Vector[PhysicalEntity]()

  //TODO: non-pure function, refactor / decompose later...
  def spawnChief(position: Vector2): Chief = {
    val chiefSprite = new Sprite(new Texture(Gdx.files.internal(Constants.CHIEF_SPRITE_PATH)))
    val chief = ChiefBuilder(new Vector2(0.0f, 0.5f), Some(chiefSprite)).createEntity(0.5f, 0.8f, 0.4f)
    chief.setPosition(position)
    Gdx.input.setInputProcessor(chief)
    applyPhysicalEntity(chief)
    chief
  }

  def applyPhysicalEntity(entity: PhysicalEntity): PhysicalEntity = {
    physicalEntities = physicalEntities :+ entity
    entity
  }

  def allEntities: Vector[PhysicalEntity] = physicalEntities

  def entitiesWithSprites: Vector[PhysicalEntity] =
    physicalEntities.filter(_.physicalEntityComponent.body.getUserData.isInstanceOf[Sprite])

  def dispose(): Unit = {
    physicalWorldComponent.dispose()
    physicalEntities.foreach(_.dispose())
  }
}
