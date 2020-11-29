package com.gdx.dogs_and_dungeons;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.headless.HeadlessApplication;
import com.badlogic.gdx.backends.headless.HeadlessApplicationConfiguration;

// Clase de la que heredan los test para poder inicilizar la variable Gdx.app

public class GdxTest {

    private static class TestApplication extends ApplicationAdapter {

        // Clase que sirve como punto de entrada a una aplicación de libgdx

    }

    public GdxTest() {

        HeadlessApplicationConfiguration config = new HeadlessApplicationConfiguration();

        HeadlessApplication app = new HeadlessApplication(new TestApplication());

        Gdx.app = app;
    }
}
