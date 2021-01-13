package com.gdx.dogs_and_dungeons.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.gdx.dogs_and_dungeons.DogsAndDungeons;
import com.gdx.dogs_and_dungeons.Utility;

// Pantalla que se muestra al completar el juego

public class VictoryScreen implements Screen {

    private DogsAndDungeons game_ref;

    private Stage stage;

    public VictoryScreen(DogsAndDungeons game) {

        game_ref = game;

        stage = new Stage();

        Table table = new Table();

        table.setFillParent(true);

        Label.LabelStyle titleStyle = new Label.LabelStyle();
        titleStyle.font = Utility.titleFont;
        titleStyle.fontColor = Color.BLUE;

        // Label de victoria

        Label victoryLabel = new Label("Victoria !", titleStyle);

        Label.LabelStyle mainStyle = new Label.LabelStyle();
        mainStyle.font = Utility.mainFont;
        mainStyle.font.getData().setScale(0.8f);

        // Label de felicitación

        Label congratsLabel = new Label(String.format("Felicidades por completar el juego, %s !", UsersScreen.getSelectedUser()),mainStyle);

        // Botones

        TextButton backButton = new TextButton("Terminar", Utility.DEFAULT_SKIN);

        table.add(victoryLabel);

        table.row().space(100);

        table.add(congratsLabel);

        table.row().space(100);

        table.add(backButton);

        stage.addActor(table);
    }

    @Override
    public void show() {

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
