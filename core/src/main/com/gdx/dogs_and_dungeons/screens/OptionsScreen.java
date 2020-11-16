package com.gdx.dogs_and_dungeons.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.gdx.dogs_and_dungeons.DogsAndDungeons;
import com.gdx.dogs_and_dungeons.Utility;

public class OptionsScreen implements Screen {

    private Stage stage;

    private Table table;

    private Label label;

    private Label volumeLabel;

    private Label musicLabel;

    private CheckBox musicBox;

    private Label fpsLabel;

    private SelectBox<Integer> fpsLimiter;

    private TextButton backButton;

    private TextButton applyButton;

    private DogsAndDungeons game_ref;

    private Texture backgroundTexture;

    private SpriteBatch batch;


    public OptionsScreen(DogsAndDungeons game) {

        game_ref = game;
        stage = new Stage();


        // La tabla es un layout muy flexible que permite colocar actores
        table = new Table();
        // table.setDebug(true); // Mostramos cómo se posicionan los elemnetos en la tabla
        // Hacemos que la tabla ocupe toda la pantalla
        table.setFillParent(true);

        Label.LabelStyle labelStyle = new Label.LabelStyle();
        labelStyle.font = Utility.mainFont;
        label = new Label("Opciones",labelStyle);
        volumeLabel = new Label("Volumen", labelStyle);
        musicLabel = new Label("Música",labelStyle);

        // Cargamos la skin, que contine texturas para todos los widgets (elementos UI)
        Slider slider = new Slider(0,10,1,false, Utility.DEFAULT_SKIN);
        musicBox = new CheckBox("",Utility.DEFAULT_SKIN);

        fpsLabel = new Label("FPS",labelStyle);
        fpsLimiter = new SelectBox<Integer>(Utility.DEFAULT_SKIN);
        fpsLimiter.setItems(60,45,30);

        backButton = new TextButton("Volver",Utility.DEFAULT_SKIN);
        backButton.addListener(new ClickListener() {


            @Override

            public void clicked(InputEvent event, float x, float y) {

                game_ref.setScreen(DogsAndDungeons.mainScreen);

            }


        });

        applyButton = new TextButton("Aplicar cambios",Utility.DEFAULT_SKIN);


        // Background batch and texture
        batch = new SpriteBatch();
        backgroundTexture = new Texture(Gdx.files.internal("backgrounds/Background8.png"));

        // Adding components to table
        table.top();
        table.add(label).padTop(20).colspan(2);
        table.row().expandY();
        table.add(volumeLabel).expandX().right().padRight(20);
        table.add(slider).expandX().left().padLeft(20);
        table.row().expandY();
        table.add(musicLabel).right().padRight(20);
        table.add(musicBox).left().padLeft(20);
        table.row().expandY();
        table.add(fpsLabel).right().padRight(20);
        table.add(fpsLimiter).left().padLeft(20);
        table.row().expandY();
        table.add(backButton);
        table.add(applyButton);
        stage.addActor(table);





    }

    @Override
    public void show() {

        // La clase stage se encarga de gestionar los input que tinen lugar sobre todos sus hijos

        Gdx.input.setInputProcessor(stage);

    }

    @Override
    public void render(float delta) {

        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // Background
        batch.begin();
        batch.draw(backgroundTexture, 0, 0, 800, 500);
        batch.end();

        stage.act(delta); // Llama al método act() de cada actor

        stage.draw(); // Dibujado de la escena

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
