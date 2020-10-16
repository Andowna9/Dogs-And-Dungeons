package com.gdx.dogs_and_dungeons.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;

public class OptionsScreen implements Screen {

    Stage stage;

    Table table;

    Label label;

    Label volumeLabel;

    Label musicLabel;

    CheckBox musicBox;

    Label fpsLabel;

    SelectBox<Integer> fpsLimiter;

    TextButton backButton;

    TextButton applyButton;

    @Override
    public void show() {

        stage = new Stage();

        // La tabla es un layout muy flexible que permite colocar actores

        table = new Table();

        // table.setDebug(true); // Mostramos cómo se posicionan los elemnetos en la tabla

        // Hacemos que la tabla ocupe toda la pantalla

        table.setFillParent(true);

        // Utilizamos un generador que toma una fuente TTF y la convierte en bitmap

        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/Pixelade.ttf"));

        FreeTypeFontGenerator.FreeTypeFontParameter param = new FreeTypeFontGenerator.FreeTypeFontParameter();

        param.size = 40;

        BitmapFont font = generator.generateFont(param);

        generator.dispose();

        Label.LabelStyle labelStyle = new Label.LabelStyle();

        labelStyle.font = font;

        label = new Label("Opciones",labelStyle);

        volumeLabel = new Label("Volumen", labelStyle);

        musicLabel = new Label("Música",labelStyle);

        // Cargamos la skin, que contine texturas para todos los widgets (elementos UI)

        Skin skin = new Skin(Gdx.files.internal("skins/default/uiskin.json"));

        Slider slider = new Slider(0,10,1,false,skin);

        musicBox = new CheckBox("",skin);

        fpsLabel = new Label("FPS",labelStyle);

        fpsLimiter = new SelectBox<Integer>(skin);

        fpsLimiter.setItems(60,45,30);

        backButton = new TextButton("Volver",skin);

        applyButton = new TextButton("Aplicar cambios",skin);

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

        // La clase stage se encarga de gestionar los input que tinen lugar sobre todos sus hijos

        Gdx.input.setInputProcessor(stage);

    }

    @Override
    public void render(float delta) {

        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

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
