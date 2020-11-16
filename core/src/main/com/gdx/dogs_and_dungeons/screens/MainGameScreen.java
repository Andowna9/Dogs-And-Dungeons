package com.gdx.dogs_and_dungeons.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.gdx.dogs_and_dungeons.*;
import com.gdx.dogs_and_dungeons.managers.CameraManager;
import com.gdx.dogs_and_dungeons.managers.SpriteManager;
import com.gdx.dogs_and_dungeons.managers.RenderManager;

// Pantalla de juego

public class MainGameScreen implements Screen {

    private static final String TAG = MainGameScreen.class.getSimpleName();

    private SpriteBatch batch;

    // Fuente para mostrar fps

    private BitmapFont font;

    private OrthogonalTiledMapRenderer mapRenderer;

    // Referencia a la clase que extiende de game

    private DogsAndDungeons game_ref;

    // Gestor de entidades

    private SpriteManager spriteManager;

    // Gestor de renderizado de entidades

    private RenderManager renderManager;

    // Gestor de cámara

    private CameraManager cameraManager;

    // Tiempos de pausa

    private long pauseTime;

    private long pauseStart;


    public MainGameScreen(DogsAndDungeons game) {

        game_ref = game;

        batch = new SpriteBatch();

        font = new BitmapFont();

        spriteManager = new SpriteManager();

        renderManager = new RenderManager(spriteManager);

        cameraManager = new CameraManager(renderManager, spriteManager);

    }

    @Override
    public void show() {

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


        spriteManager.update(delta);

        // Antes de renderizar ajustamos la vista del mapa, ya que hemos actualizado la cámara

        // *RENDERIZADO DE TEXTURAS (GRÁFICOS)*

        renderManager.render();

        // Renderizado de texto para mostrar FPS en cada instante (con otro batch)

        batch.begin();

        font.draw(batch,String.valueOf(Gdx.graphics.getFramesPerSecond()),20,20);

        batch.end();


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

    }
}
