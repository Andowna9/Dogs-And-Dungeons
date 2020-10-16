package com.gdx.dogs_and_dungeons.screens;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.FPSLogger;


// Pantalla de juego

public class MainGameScreen implements Screen {

    FPSLogger fps;

    @Override
    public void show() {

        fps = new FPSLogger();


    }

    @Override
    public void render(float delta) {

        fps.log();

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
