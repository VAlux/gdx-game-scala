package com.alvo.screens

import com.alvo.box2d.entities.{Chief, Controllable}
import com.alvo.box2d.world.GameWorld
import com.alvo.constants.Constants
import com.alvo.utils.MetricsHelper
import com.badlogic.gdx.{Gdx, Screen}
import com.badlogic.gdx.graphics.{Color, GL20, OrthographicCamera, Texture}
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator
import com.badlogic.gdx.graphics.g2d.{BitmapFont, Sprite, SpriteBatch}
import com.badlogic.gdx.math.{MathUtils, Vector2}
import com.badlogic.gdx.physics.box2d.{Body, Box2DDebugRenderer}
import com.badlogic.gdx.utils.Array

/**
  * Created by alvo on 17.05.17.
  */
object GameScreen extends Screen {

  Gdx.app.log("GAME SCREEN", "game screen created")

  val camera: OrthographicCamera = new OrthographicCamera(Gdx.graphics.getWidth, Gdx.graphics.getHeight)
  val freshCamera: OrthographicCamera = new OrthographicCamera(Gdx.graphics.getWidth, Gdx.graphics.getHeight)
  val debugRenderer: Box2DDebugRenderer = new Box2DDebugRenderer()
  val batch: SpriteBatch = new SpriteBatch()
  val gameFont: BitmapFont = createGameFont()
  val backgroundTexture = new Texture(Gdx.files.internal("SideElements/background.png"))
  val bodies = new Array[Body](Constants.DEFAULT_BODY_INITIAL_CAPACITY)
  val chief = GameWorld.spawnChief(new Vector2(0.0f, -5.0f))

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

  override def render(delta: Float): Unit = {
    Gdx.gl.glClearColor(0, 0, 0, 1)
    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)

    processInputForEntity(chief)

    GameWorld.physicalWorldComponent.step(Constants.TIME_STEP, Constants.VELOCITY_ITERATIONS, Constants.POSITION_ITERATIONS)

    // world.checkSpawnFood()
    // world.getCurrentFood.update()

    freshCamera.update()
    camera.update()
    batch.begin()
    batch.setProjectionMatrix(freshCamera.combined)
    batch.draw(
      backgroundTexture,
      -MetricsHelper.halfWidthInMeter * Constants.PX_M_SCALE,
      -MetricsHelper.halfHeightInMeter * Constants.PX_M_SCALE,
       MetricsHelper.halfWidthInMeter * Constants.PX_M_SCALE * 2,
       MetricsHelper.halfHeightInMeter * Constants.PX_M_SCALE * 2)

    batch.setProjectionMatrix(camera.combined)

    GameWorld.entitiesWithSprites.foreach { entityWithSprite =>
      val body = entityWithSprite.physicalEntityComponent.body
      val sprite = body.getUserData.asInstanceOf[Sprite]
      sprite.setPosition(body.getPosition.x - sprite.getWidth / 2, body.getPosition.y - sprite.getHeight / 2)
      sprite.setOrigin(sprite.getWidth / 2, sprite.getHeight / 2)
      sprite.setRotation(body.getAngle * MathUtils.radiansToDegrees)
      sprite.draw(batch)
    }

    batch.end()

    if (Constants.DEBUG_INFO_ENABLED) {
      debugRenderer.render(GameWorld.physicalWorldComponent, camera.combined)
    }
  }

  def processInputForEntity(controllable: Controllable): Unit = controllable.handleInput()

  override def resize(width: Int, height: Int): Unit = {
    camera.viewportWidth = width * Constants.SCALE
    camera.viewportHeight = height * Constants.SCALE
    camera.update()
  }

  override def show(): Unit = {}

  override def resume(): Unit = {}

  override def pause(): Unit = {}

  override def hide(): Unit = {}

  override def dispose(): Unit = {
    GameWorld.dispose()
    Gdx.app.log("GAME SCREEN", "world disposed")
  }
}
