package com.alvo.box2d

import com.alvo.EntityStore
import com.alvo.EntityStore.{EntityWithBody, EntityWithPhysicalProperties}

/**
  * Created by alvo on 08.07.17.
  */
object EntitySpawner {

  import com.alvo.box2d.EntityBuilderInstances._

  private type PropsToBodyEntityFunction = EntityWithPhysicalProperties => EntityWithBody

  private def createChiefBody(chief: EntityWithPhysicalProperties): EntityWithBody = chief match {
    case EntityWithPhysicalProperties(chiefEntity: Chief, chiefProperties) =>
      EntityWithBody(chiefEntity, EntityBuilder.buildEntity(chiefEntity)(chiefProperties))
    case _ => createEmptyEntity
  }

  private def createFoodBody(food: EntityWithPhysicalProperties): EntityWithBody = food match {
    case EntityWithPhysicalProperties(foodEntity: Food, foodProperties) =>
      EntityWithBody(foodEntity, EntityBuilder.buildEntity(foodEntity)(foodProperties))
    case _ => createEmptyEntity
  }

  private def createChainBody(chain: EntityWithPhysicalProperties): EntityWithBody = chain match {
    case EntityWithPhysicalProperties(chainEntity: Chain, chainProperties) =>
      EntityWithBody(chainEntity, EntityBuilder.buildEntity(chainEntity)(chainProperties))
    case _ => createEmptyEntity
  }

  private def createWallBody(wall: EntityWithPhysicalProperties): EntityWithBody = wall match {
    case EntityWithPhysicalProperties(wallEntity: Rectangle, wallProperties) =>
      EntityWithBody(wallEntity, EntityBuilder.buildEntity(wallEntity)(wallProperties))
    case _ => createEmptyEntity
  }

  private def createEmptyEntity: EntityWithBody = {
    val emptyEntity = Empty()
    EntityWithBody(emptyEntity, EntityBuilder.buildEntity(emptyEntity)(PhysicalEntityProperties()))
  }

  private def spawnAndStore(spawningFunction: PropsToBodyEntityFunction): PropsToBodyEntityFunction =
    spawningFunction andThen EntityStore.storeEntity

  val chiefSpawner: PropsToBodyEntityFunction = spawnAndStore(createChiefBody)

  val foodSpawner: PropsToBodyEntityFunction = spawnAndStore(createFoodBody)

  val chainSpawner: PropsToBodyEntityFunction = spawnAndStore(createChainBody)

  val wallSpawner: PropsToBodyEntityFunction = spawnAndStore(createWallBody)
}
