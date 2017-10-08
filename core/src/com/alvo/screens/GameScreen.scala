package com.alvo.screens

import com.alvo.box2d._
import com.alvo.constants.Constants
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.{GL20, OrthographicCamera, Texture}
import com.badlogic.gdx.physics.box2d._
import com.badlogic.gdx.{Gdx, Screen}

/**
  * Created by alvo on 17.05.17.
  */
object GameScreen extends Screen {

  Gdx.app.log("GAME SCREEN", "game screen created")

  val camera: OrthographicCamera = new OrthographicCamera(Gdx.graphics.getWidth, Gdx.graphics.getHeight)
  val debugRenderer: Box2DDebugRenderer = new Box2DDebugRenderer()
  val batch: SpriteBatch = new SpriteBatch()
  val backgroundTexture = new Texture(Gdx.files.internal(Constants.BACKGROUND_PATH))
  val world: World = new World(Constants.GRAVITY, true)
  val frame = Environment.createBoundingFrame()

  Gdx.input.setInputProcessor(Environment.chiefControllable)

  override def render(delta: Float): Unit = {
    Gdx.gl.glClearColor(0.2f, 0.2f, 0.2f, 1)
    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)

    Environment.chiefControllable.handleInput()

    world.step(Constants.TIME_STEP, Constants.VELOCITY_ITERATIONS, Constants.POSITION_ITERATIONS)

    camera.update()
    batch.begin()
    batch.setProjectionMatrix(camera.combined)

    // TODO: some rendering goes here ... //

    batch.end()

    if (Constants.DEBUG_INFO_ENABLED) {
      debugRenderer.render(world, camera.combined)
    }
  }

  override def resize(width: Int, height: Int): Unit = {
    camera.viewportWidth = width / Constants.VIEWPORT_SCALE
    camera.viewportHeight = height / Constants.VIEWPORT_SCALE
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
