package com.alvo.screens

import com.alvo.box2d.{Circle, EntityBuilder, PhysicalEntityProperties}
import com.alvo.constants.Constants
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator
import com.badlogic.gdx.graphics.g2d.{BitmapFont, SpriteBatch}
import com.badlogic.gdx.graphics.{Color, GL20, OrthographicCamera, Texture}
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.physics.box2d._
import com.badlogic.gdx.utils.Array
import com.badlogic.gdx.{Gdx, Screen}

/**
  * Created by alvo on 17.05.17.
  */
object GameScreen extends Screen {

  import com.alvo.box2d.EntityBuilderInstances._

  Gdx.app.log("GAME SCREEN", "game screen created")

  val camera: OrthographicCamera = new OrthographicCamera(Gdx.graphics.getWidth, Gdx.graphics.getHeight)
  val debugRenderer: Box2DDebugRenderer = new Box2DDebugRenderer()
  val batch: SpriteBatch = new SpriteBatch()
  val gameFont: BitmapFont = createGameFont()
  val backgroundTexture = new Texture(Gdx.files.internal("SideElements/background.png"))
  val bodies = new Array[Body](Constants.DEFAULT_BODY_INITIAL_CAPACITY)
  val world: World = new World(new Vector2(0.0f, -9.8f), true)

  EntityBuilder.buildEntity(Circle(10.0f))(PhysicalEntityProperties(position = new Vector2(10.0f, 10.0f)))

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
    Gdx.gl.glClearColor(0.2f, 0.2f, 0.2f, 1)
    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)

    world.step(Constants.TIME_STEP, Constants.VELOCITY_ITERATIONS, Constants.POSITION_ITERATIONS)

    camera.update()
    batch.begin()
    batch.setProjectionMatrix(camera.combined)

    // some rendering goes here //

    batch.end()

    if (Constants.DEBUG_INFO_ENABLED) {
      debugRenderer.render(world, camera.combined)
    }
  }

  override def resize(width: Int, height: Int): Unit = {
    camera.update()
  }

  override def show(): Unit = {
    Gdx.app.log("GAME SCREEN", "show the screen")
  }

  override def resume(): Unit = {
    Gdx.app.log("GAME SCREEN", "resume the screen")
  }

  override def pause(): Unit = {
    Gdx.app.log("GAME SCREEN", "pause the screen")
  }

  override def hide(): Unit = {
    Gdx.app.log("GAME SCREEN", "hide the screen")
  }

  override def dispose(): Unit = {
    world.dispose()
    Gdx.app.log("GAME SCREEN", "game screen disposed")
  }
}
