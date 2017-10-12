package com.alvo.constants

import com.alvo.box2d.PhysicalEntityProperties
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType

/**
  * Created by alvo on 17.05.17.
  */
object Constants {

  // Graphics:
  val SCALE = 3.0f
  val PX_M_SCALE = 4f
  val FONT_CHARACTERS: String =
    "абвгдеёжзийклмнопрстуфхцчшщъыьэюя" +
    "АБВГДЕЁЖЗИЙКЛМНОПРСТУФХЦЧШЩЪЫЬЭЮЯ" +
    "abcdefghijklmnopqrstuvwxyz" +
    "ABCDEFGHIJKLMNOPQRSTUVWXYZ" +
    "0123456789" +
    "][_!$%#@|\\/?-+=()*&.:;,{}\"´`'<>"

  val FONT_SIZE: Int = (Constants.PX_M_SCALE * 1.5f).asInstanceOf[Int]
  val DEBUG_INFO_ENABLED: Boolean = true
  val VIEWPORT_SCALE: Float = 2.0f

  // Physics simulation:
  val GRAVITY = new Vector2(0.0f, -9.8f)
  val TIME_STEP: Float = 1.0f / 60.0f
  val VELOCITY_ITERATIONS = 6
  val POSITION_ITERATIONS = 3
  val CHIEF_DAMPING = 5.0f

  val CHIEF_PHYSICAL_PROPERTIES = PhysicalEntityProperties(
    gravityScale = 0.0f,
    linearDamping = CHIEF_DAMPING,
    angularDamping = CHIEF_DAMPING,
    fixedRotation = true
  )

  val BOUNDING_FRAME_PHYSICAL_PROPERTIES = PhysicalEntityProperties(
    friction = 0.5f,
    restitution = 0.6f,
    bodyType = BodyType.StaticBody
  )

  val WALL_PHYSICAL_PROPERTIES: PhysicalEntityProperties = PhysicalEntityProperties(
    friction = 0.7f,
    restitution = 0.1f
  )

  // Game-play
  val HELL_DOOR_SPEED = 9.0f
  val SCORE_INCREMENT = 10
  val SCORE_DECREMENT= 15
  val ENTITY_IMPULSE_FORCE = 25000.0f
  val EASY_FOOD_SPIN_THRESHOLD = 4.0f
  val HARD_FOOD_SPIN_THRESHOLD = 8.0f
  val DEFAULT_BODY_INITIAL_CAPACITY: Int = 8
  val FOOD_STATES_AMOUNT = 3

  // Metrics
  val HOLE_DEPTH = 5.0f

  // Collide Groups
  val GROUP_CHIEF_FRAME: Int = -1

  // Control
  val ACCELEROMETER_CALIBRATION_HEIGHT: Float = 70.0f
  val ACCELEROMETER_SCALE: Float = 16.0f

  // Resources
  val CHIEF_SPRITE_PATH = "Chiefs/NECHUPARA.png"
  val FOOD_TEST_DOODLE_SPRITE_PATH = "food/Games/Doodle/Doodle_01.png"
  val PACKED_FOOD_PACK_PATH = "Packed/food/MHCharacters.pack"
  val BACKGROUND_PATH: String = "SideElements/background.png"
}