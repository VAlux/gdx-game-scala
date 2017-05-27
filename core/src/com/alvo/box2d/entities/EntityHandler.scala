package com.alvo.box2d.entities

import com.badlogic.gdx.graphics.g2d.Sprite

/**
  * Created by alvo on 09.05.17.
  */
sealed trait EntityHandler[A <: Entity] {
  def process(): Unit
}

final case class SpritePack(normal: Sprite, spinEasy: Sprite, spinHard: Sprite)

class FoodHandler(val food: Food, spritePack: SpritePack) extends EntityHandler[Food] {
  override def process(): Unit = {
    food.physicalEntityComponent.sprite.foreach(_.set(spritePack.normal))
  }
}