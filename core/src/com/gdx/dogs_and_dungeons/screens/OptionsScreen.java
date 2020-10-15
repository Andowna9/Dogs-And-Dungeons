package com.gdx.dogs_and_dungeons.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class OptionsScreen implements Screen {

    Stage stage;

    Label label;

    Table table;

    @Override
    public void show() {

        stage = new Stage();

        table = new Table();

        table.setDebug(true); // Mostramos cómo se posicionan los elemnetos en la tabla

        table.setFillParent(true);

        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/Pixeled.ttf"));

        FreeTypeFontGenerator.FreeTypeFontParameter param = new FreeTypeFontGenerator.FreeTypeFontParameter();

        param.size = 14;

        BitmapFont font = generator.generateFont(param);

        generator.dispose();

        Label.LabelStyle labelStyle = new Label.LabelStyle();

        labelStyle.font = font;

        label = new Label("Opciones",labelStyle);

        table.top();

        table.add(label).padTop(20);

        stage.addActor(table);

        Gdx.input.setInputProcessor(stage);

    }

    @Override
    public void render(float delta) {

        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

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
