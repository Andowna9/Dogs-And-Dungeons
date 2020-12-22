package com.gdx.dogs_and_dungeons.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.gdx.dogs_and_dungeons.*;
import com.gdx.dogs_and_dungeons.managers.CameraManager;
import com.gdx.dogs_and_dungeons.managers.GameStateManager;
import com.gdx.dogs_and_dungeons.managers.SpriteManager;
import com.gdx.dogs_and_dungeons.managers.RenderManager;
import com.gdx.dogs_and_dungeons.profiles.ProfileManager;

// Pantalla de juego

public class MainGameScreen implements Screen {

    private static final String TAG = MainGameScreen.class.getSimpleName();

    private DogsAndDungeons game_ref;

    private SpriteBatch batch;

    // Fuente para mostrar fps

    private BitmapFont font;

    private OrthogonalTiledMapRenderer mapRenderer;

    // Gestor de entidades

    private SpriteManager spriteManager;

    // Gestor de renderizado de entidades

    private RenderManager renderManager;

    // Gestor de cámara

    private CameraManager cameraManager;

    // Gestor de estados del juegi

    private GameStateManager gameStateManager;

    // Tiempos de pausa

    private long pauseTime;

    private long pauseStart;

    private ShapeRenderer shapeRenderer;

    private float alpha = 0f;

    public MainGameScreen(DogsAndDungeons game) {

        game_ref = game;

        shapeRenderer = new ShapeRenderer();

        batch = new SpriteBatch();

        font = new BitmapFont();

        spriteManager = new SpriteManager();

        renderManager = new RenderManager(spriteManager);

        gameStateManager = new GameStateManager(this,spriteManager);

        cameraManager = new CameraManager(renderManager, spriteManager);


    }


    @Override
    public void show() {

        alpha = 0;

        gameStateManager.setCurrentGameState(GameStateManager.GameState.PLAYING);

        spriteManager.init();

        spriteManager.getPlayerController().init();

        Gdx.input.setInputProcessor(spriteManager.getPlayerController());

    }


    @Override
    public void render(float delta) {

        delta = delta - pauseTime / 1000f;

        pauseTime = 0;

        // *LIMPIANDO PANTALLA*

        Gdx.gl.glClearColor(0, 0, 0, 1);

        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);


        // *ACTUALIZACIONES*

        cameraManager.updateCamera();

        // El GameStateManager actualiza el estado del juego si es necesario

        gameStateManager.updateState(delta);

        // Antes de renderizar ajustamos la vista del mapa, ya que hemos actualizado la cámara

        // *RENDERIZADO DE TEXTURAS (GRÁFICOS)*

        renderManager.render(delta);

        // Renderizado de texto para mostrar FPS en cada instante (con otro batch)

        batch.begin();

        font.draw(batch,String.valueOf(Gdx.graphics.getFramesPerSecond()),20,20);

        // Rectángulo negro que servirá para lograr fadeOut

        batch.end();

        Gdx.gl.glEnable(GL20.GL_BLEND);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);

        shapeRenderer.setColor(0,0,0,alpha);

        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);

        shapeRenderer.rect(0,0,Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        shapeRenderer.end();

    }

    public void switchScreen(Screen newScreen, float delta) {

        alpha += delta;

        if (alpha >= 1) {

            game_ref.setScreen(newScreen);

        }


    }


    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

        pauseStart = System.currentTimeMillis();

        Gdx.app.debug(TAG, "Juego Pausado");

    }

    @Override
    public void resume() {

        Gdx.app.debug(TAG, "Juego Reanudado");

        pauseTime = System.currentTimeMillis() - pauseStart;

        pauseStart = 0;

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

        ProfileManager.getInstance().saveProfile();
    }
}
