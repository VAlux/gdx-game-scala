package com.alvo.constants

import com.badlogic.gdx.math.Vector2

/**
  * Created by alvo on 17.05.17.
  */
object Constants {
  //Graphics:
  val SCALE = 3.0f
  val PX_M_SCALE = 16f
  val FONT_CHARACTERS = "абвгдеёжзийклмнопрстуфхцчшщъыьэюяАБВГДЕЁЖЗИЙКЛМНОПРСТУФХЦЧШЩЪЫЬЭЮЯabcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789][_!$%#@|\\/?-+=()*&.:;,{}\"´`'<>"
  val FONT_SIZE: Int = (Constants.PX_M_SCALE * 1.5f).asInstanceOf[Int] // TODO: consider fix this type casting thing...
  val DEBUG_INFO_ENABLED: Boolean = true

  //Physics simulation:
  val GRAVITY = new Vector2(0.0f, -9.8f)
  val TIME_STEP: Float = 1.0f / 60.0f
  val VELOCITY_ITERATIONS = 6
  val POSITION_ITERATIONS = 3
  val CHIEF_DAMPING = 0.5f

  //Gameplay
  val HELL_DOOR_SPEED = 9.0f
  val SCORE_INCREMENT = 10
  val SCORE_DECREMENT= 15
  val CHIEF_IMPULSE_FORCE = 200.0f
  val EASY_FOOD_SPIN_THRESHOLD = 4.0f
  val HARD_FOOD_SPIN_THRESHOLD = 8.0f
  val DEFAULT_BODY_INITIAL_CAPACITY: Int = 8

  // Metrics
  val HOLE_DEPTH = 5.0f

  // Collide Groups
  val GROUP_CHIEF_FRAME: Int = -1

  //TODO: this should be loaded from config.
  val FOOD_STATES_AMOUNT = 3
  val CHIEF_SPRITE_PATH = "Chiefs/NECHUPARA.png"
  val FOOD_TEST_DOODLE_SPRITE_PATH = "food/Games/Doodle/Doodle_01.png"
  val PACKED_FOOD_PACK_PATH = "Packed/food/MHCharacters.pack"
}