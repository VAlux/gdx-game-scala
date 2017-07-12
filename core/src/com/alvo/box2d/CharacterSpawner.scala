package com.alvo.box2d

import com.alvo.EntityStore
import com.alvo.EntityStore.{EntityWithBody, EntityWithPhysicalProperties}

/**
  * Created by alvo on 08.07.17.
  */
object CharacterSpawner {

  import com.alvo.box2d.EntityBuilderInstances._

  private def createChiefBody(chief: EntityWithPhysicalProperties): EntityWithBody = chief match {
    case EntityWithPhysicalProperties(chiefEntity: Chief, chiefProperties) =>
      EntityWithBody(chiefEntity, EntityBuilder.buildEntity(chiefEntity)(chiefProperties))
  }

  private def createFoodBody(Food: EntityWithPhysicalProperties): EntityWithBody = Food match {
    case EntityWithPhysicalProperties(foodEntity: Food, foodProperties) =>
      EntityWithBody(foodEntity, EntityBuilder.buildEntity(foodEntity)(foodProperties))
  }
  
  val spawnChief: (EntityWithPhysicalProperties) => EntityWithBody = createChiefBody _ andThen EntityStore.storeEntity

  val spawnFood: (EntityWithPhysicalProperties) => EntityWithBody = createFoodBody _ andThen EntityStore.storeEntity
}
