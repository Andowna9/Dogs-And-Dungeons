package com.gdx.dogs_and_dungeons.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.gdx.dogs_and_dungeons.DogsAndDungeons;

// Pantalla de juego

public class MainGameScreen implements Screen {

    //final DogsAndDungeons game; // Constant variable of the Game class

    public OrthographicCamera camera;

    public MainGameScreen () {


        camera = new OrthographicCamera();
        camera.setToOrtho(false, 1024, 768); //yDown false: normal graphic coordinates


    }
    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {

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
