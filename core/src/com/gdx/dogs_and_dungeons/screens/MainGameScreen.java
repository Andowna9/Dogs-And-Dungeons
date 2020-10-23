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

// Pantalla de juego

public class MainGameScreen implements Screen {

    SpriteBatch batch;

    BitmapFont font;

    OrthographicCamera camera;

    TiledMap tiledMap;

    TiledMapRenderer mapRenderer;

    @Override
    public void show() {

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
    public void render(float delta) {

        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        mapRenderer.render();

        batch.begin();

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
