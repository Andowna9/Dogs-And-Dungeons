package com.gdx.dogs_and_dungeons.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.gdx.dogs_and_dungeons.DogsAndDungeons;
import com.gdx.dogs_and_dungeons.Entity;
import com.gdx.dogs_and_dungeons.PlayerController;

// Pantalla de juego

public class MainGameScreen implements Screen {

    SpriteBatch batch;

    // Fuente para mostrar fps

    BitmapFont font;

    OrthographicCamera camera;

    TiledMap tiledMap;

    TiledMapRenderer mapRenderer;

    // Jugador

    Entity player;

    PlayerController playerController;

    // Referencia a la clase que extiende de game

    DogsAndDungeons game;


    public MainGameScreen(DogsAndDungeons game) {

        this.game = game;

        player = new Entity(48,48);

        player.setPosition(100,100);

        playerController = new PlayerController(player);

        float h = Gdx.graphics.getHeight();

        float w = Gdx.graphics.getWidth();

        batch = new SpriteBatch();

        font = new BitmapFont();

        camera = new OrthographicCamera();

        camera.setToOrtho(false,w,h);

        camera.position.set(800,750,0); // Centramos la posición de la ventana de acuerdo con el tamaño del tilemap (1600 x 1600 px)

        camera.update(); // Cada vez que la cámara es manipulada, hay que actualizarla manualmente

        tiledMap = new TmxMapLoader().load("tilemaps/test.tmx");

        mapRenderer = new OrthogonalTiledMapRenderer(tiledMap);

        mapRenderer.setView(camera);


    }

    @Override
    public void show() {

        Gdx.input.setInputProcessor(playerController);

    }

    @Override
    public void render(float delta) {

        playerController.processInput(delta);

        player.update(delta);

        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        mapRenderer.render();

        batch.begin();

        batch.draw(player.getCurrentTexture(),player.getCurrentPosition().x,player.getCurrentPosition().y);

        font.draw(batch,String.valueOf(Gdx.graphics.getFramesPerSecond()),20,20);

        batch.end();

    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
