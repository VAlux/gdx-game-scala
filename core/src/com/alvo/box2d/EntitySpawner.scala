package com.alvo.box2d

import com.alvo.EntityStore
import com.alvo.EntityStore.{EntityWithBody, EntityWithPhysicalProperties}

/**
  * Created by alvo on 08.07.17.
  */
object EntitySpawner {

  import com.alvo.box2d.EntityBuilderInstances._

  private type PropsToBodyEntityFunction[A <: Entity] = EntityWithPhysicalProperties[A] => EntityWithBody

  private def createChiefBody(chief: EntityWithPhysicalProperties[Chief]): EntityWithBody =
    EntityWithBody(chief.entity, EntityBuilder.buildEntity(chief.entity)(chief.properties))

  private def createFoodBody(food: EntityWithPhysicalProperties[Food]): EntityWithBody =
    EntityWithBody(food.entity, EntityBuilder.buildEntity(food.entity)(food.properties))

  private def createChainBody(chain: EntityWithPhysicalProperties[Chain]): EntityWithBody =
    EntityWithBody(chain.entity, EntityBuilder.buildEntity(chain.entity)(chain.properties))

  private def createWallBody(wall: EntityWithPhysicalProperties[Rectangle]): EntityWithBody =
    EntityWithBody(wall.entity, EntityBuilder.buildEntity(wall.entity)(wall.properties))

  private def spawnAndStore[A <: Entity](spawningFunction: PropsToBodyEntityFunction[A]): PropsToBodyEntityFunction[A] =
    spawningFunction andThen EntityStore.storeEntity

  val chiefSpawner: PropsToBodyEntityFunction[Chief] = spawnAndStore(createChiefBody)

  val foodSpawner: PropsToBodyEntityFunction[Food] = spawnAndStore(createFoodBody)

  val chainSpawner: PropsToBodyEntityFunction[Chain] = spawnAndStore(createChainBody)

  val wallSpawner: PropsToBodyEntityFunction[Rectangle] = spawnAndStore(createWallBody)
}