package com.alvo

import com.alvo.box2d.{Entity, PhysicalEntityProperties}
import com.badlogic.gdx.physics.box2d.Body

import scala.collection.mutable

/**
  * Created by alvo on 08.07.17.
  */
object EntityStore {

  final case class EntityWithBody(entity: Entity, body: Body)
  final case class EntityWithPhysicalProperties(entity: Entity, properties: PhysicalEntityProperties = PhysicalEntityProperties())

  private[this] val store: mutable.Map[Entity, Body] = mutable.Map[Entity, Body]()
  
  def storeEntity(entityWithBody: EntityWithBody): EntityWithBody = entityWithBody match {
    case EntityWithBody(entity, body) => store += entity -> body
    entityWithBody
  }
  
  def retrieveBody(entity: Entity): Body = store(entity)
}
