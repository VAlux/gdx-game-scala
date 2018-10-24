package com.alvo.box2d

import com.alvo.EntityStore.{EntityWithBody, EntityWithPhysicalProperties}
import com.alvo.constants.Constants
import com.alvo.utils.{Log, MetricsTranslator}
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType

object Environment {

  lazy val chief: EntityWithBody = Log.info("Environment", "Creating chief entity...") {
    EntitySpawner.chiefSpawner(EntityWithPhysicalProperties(Chief(), Constants.CHIEF_PHYSICAL_PROPERTIES))
  }

  lazy val chiefControllable: Controllable with KeyboardControllable =
    Log.info("Environment", "Creating Chief controllable instance...") {
      new Controllable(chief) with KeyboardControllable
    }

  lazy val gameFont: BitmapFont = Log.info("Environment", "Creating game font...") {
    createGameFont()
  }

  lazy val mainBondingFrame: EntityWithBody = Log.info("Environment", "Creating main bounding frame...") {
    createBoundingFrame()
  }

  lazy val secondaryBondingFrame: EntityWithBody = Log.info("Environment", "Creating secondary bounding frame...") {
    createBoundingFrame(Constants.HOLE_DEPTH * 4.0f, Constants.HOLE_DEPTH * 4.0f)
  }

  lazy val topWall: EntityWithBody = Log.info("Environment", "Creating bottom wall...") {
    createWall(
      MetricsTranslator.widthInMeter - Constants.WALL_WIDTH,
      MetricsTranslator.halfHeightInMeter,
      Constants.WALL_WIDTH,
      MetricsTranslator.halfHeightInMeter
    )
  }

  lazy val bottomWall: EntityWithBody = Log.info("Environment", "Creating top wall...") {
    createWall(
      MetricsTranslator.widthInMeter - Constants.WALL_WIDTH,
      -MetricsTranslator.halfHeightInMeter,
      Constants.WALL_WIDTH,
      MetricsTranslator.halfHeightInMeter
    )
  }

  /**
    * Internal method for generating the game bitmap font.
    * Main font properties and available characters are container in the Constants object
    *
    * @return generated BitmapFont
    */
  private def createGameFont(): BitmapFont = {
    val fontGenerator = new FreeTypeFontGenerator(Gdx.files.internal("Font/rocked.ttf"))
    val fontParameter = new FreeTypeFontGenerator.FreeTypeFontParameter()
    fontParameter.size = Constants.FONT_SIZE
    fontParameter.characters = Constants.FONT_CHARACTERS

    val font: BitmapFont = fontGenerator.generateFont(fontParameter)
    font.setColor(Color.WHITE)
    fontGenerator.dispose()
    font
  }

  private def createBoundingFrame(dx: Float = 0.0f, dy: Float = 0.0f): EntityWithBody = {
    val topLeftX: Float = MetricsTranslator.widthInMeter + dx
    val topLeftY: Float = MetricsTranslator.heightInMeter + dy

    val chainPoints: Array[Vector2] = Array(
      new Vector2(-topLeftX, -topLeftY),
      new Vector2(topLeftX, -topLeftY),
      new Vector2(topLeftX, topLeftY),
      new Vector2(-topLeftX, topLeftY))

    EntitySpawner.chainSpawner(EntityWithPhysicalProperties(Chain(chainPoints), Constants.BOUNDING_FRAME_PHYSICAL_PROPERTIES))
  }

  private def createWall(x: Float, y: Float, width: Float, height: Float): EntityWithBody = {
    def createWallProperties: PhysicalEntityProperties =
      PhysicalEntityProperties(
        friction = 0.7f,
        restitution = 0.1f,
        bodyType = BodyType.StaticBody,
        position = new Vector2(x * 0.5f, y * 0.5f) // TODO: figure out, why this is needed to be divided by 2...
      )

    EntitySpawner.wallSpawner(EntityWithPhysicalProperties(Rectangle(width, height), createWallProperties))
  }
}