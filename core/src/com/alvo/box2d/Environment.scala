package com.alvo.box2d

import com.alvo.EntityStore.{EntityWithBody, EntityWithPhysicalProperties}
import com.alvo.constants.Constants
import com.alvo.utils.{Log, MetricsTranslator}
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator
import com.badlogic.gdx.math.Vector2

object Environment {

  lazy val chief: EntityWithBody = Log.info("Environment", "Creating chief entity...") {
    EntitySpawner.chiefSpawner(EntityWithPhysicalProperties(Chief(), Constants.CHIEF_PHYSICAL_PROPERTIES))
  }

  lazy val chiefControllable: Controllable with KeyboardControllable = Log.info("Environment", "Creating Chief controllable instance...") {
    new Controllable(chief) with KeyboardControllable
  }

  lazy val gameFont: BitmapFont = Log.info("Environment", "Creating game font...") {
    createGameFont()
  }

  lazy val bondingFrame: EntityWithBody = Log.info("Environment", "Creating bounding frame...") {
    createBoundingFrame()
  }

  /**
    * Internal method for generating the game bitmap font.
    * Main font properties and available characters are container in the Constants object
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

  def createBoundingFrame(): EntityWithBody = {
    val topLeftX: Float = MetricsTranslator.widthInMeter + Constants.HOLE_DEPTH * 2.0f
    val topLeftY: Float = MetricsTranslator.heightInMeter + Constants.HOLE_DEPTH * 2.0f

    val chainPoints: Array[Vector2] = Array(
      new Vector2(-topLeftX, -topLeftY),
      new Vector2(topLeftX, -topLeftY),
      new Vector2(topLeftX, topLeftY),
      new Vector2(-topLeftX, topLeftY))

    EntitySpawner.chainSpawner(EntityWithPhysicalProperties(Chain(chainPoints), Constants.BOUNDING_FRAME_PHYSICAL_PROPERTIES))
  }
}