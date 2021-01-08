package com.gdx.dogs_and_dungeons.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.*;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.VerticalGroup;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.gdx.dogs_and_dungeons.DogsAndDungeons;
import com.gdx.dogs_and_dungeons.Utility;

public class MainScreen implements Screen {

    private Stage stage;
    private VerticalGroup vg;
    private TextButton buttonPlay;
    private TextButton buttonOptions;
    private TextButton buttonExit;
    private Label labelTitle;
    private SpriteBatch batch;
    private Texture backgroundTexture;
    private DogsAndDungeons game_ref;


    public MainScreen(DogsAndDungeons game) {

        game_ref = game;

        stage = new Stage();
        vg = new VerticalGroup();
        vg.setFillParent(true);
       // vg.setDebug(true);  // Para ver las líneas del layout
        batch = new SpriteBatch();
        backgroundTexture = new Texture(Gdx.files.internal("backgrounds/grassBackground.jpg"));

        // Buttons
        buttonPlay = new TextButton("Jugar", Utility.DEFAULT_SKIN);

        buttonPlay.addListener(new ClickListener() {

            @Override

            public void clicked(InputEvent event, float x, float y) {

                game_ref.setScreen(DogsAndDungeons.usersScreen);

            }


        });
        buttonOptions = new TextButton("Opciones", Utility.DEFAULT_SKIN);

        buttonOptions.addListener(new ClickListener() {

            @Override

            public void clicked(InputEvent event, float x, float y) {

                game_ref.setScreen(DogsAndDungeons.optionsScreen);

            }

        });

    buttonExit = new TextButton("Salir", Utility.DEFAULT_SKIN);

        buttonExit.addListener(new ClickListener() {

            @Override

            public void clicked(InputEvent event, float x, float y) {

                Gdx.app.exit();

            }

        });

        // Set layout font
        Label.LabelStyle labelStyle = new Label.LabelStyle();
        labelStyle.font = Utility.titleFont;

        labelTitle = new Label("Dogs and Dungeons", labelStyle);

        // Adding elements to layout
        vg.addActor(labelTitle);
        vg.addActor(buttonPlay);
        vg.addActor(buttonOptions);
        vg.addActor(buttonExit);
        vg.center();
        vg.space(50);
        stage.addActor(vg);


    }

    @Override
    public void show() {

        Gdx.input.setInputProcessor(stage);

    }

    @Override
    public void render(float delta) {

        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // Background
        batch.begin();
        batch.draw(backgroundTexture, 0, 0,Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        batch.end();

        // Scene2d UI
        stage.act(delta);
        stage.draw();



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

        stage.dispose();
    }

}
