package com.gdx.dogs_and_dungeons.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.gdx.dogs_and_dungeons.DogsAndDungeons;
import com.gdx.dogs_and_dungeons.Utility;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.fadeIn;

public class GameOverScreen implements Screen {

    private DogsAndDungeons game_ref;

    private Stage stage;

    public GameOverScreen(DogsAndDungeons game) {

        game_ref = game;

        stage = new Stage();

        Table table = new Table();

        table.setFillParent(true);

        //table.setDebug(true);

        // Texto fin de juego

        Label.LabelStyle endStyle = new Label.LabelStyle();

        // Botones para interacción

        endStyle.font = Utility.titleFont;

        endStyle.fontColor = Color.RED;

        Label endText = new Label("Fin del juego",endStyle);

        TextButton leaveButton = new TextButton("Abandonar",Utility.DEFAULT_SKIN);

        leaveButton.addListener(new ClickListener() {

            @Override
            public void clicked(InputEvent event, float x, float y) {

                game_ref.setScreen(DogsAndDungeons.mainScreen);

            }
        });

        TextButton resumeButton = new TextButton("Reintentar",Utility.DEFAULT_SKIN);

        resumeButton.addListener(new ClickListener() {

            @Override
            public void clicked(InputEvent event, float x, float y) {

                game_ref.setScreen(DogsAndDungeons.mainGameScreen);

            }

        });
        table.add(endText).colspan(2).expandY();

        table.row();

        table.add(resumeButton).expandX().expandY();

        table.add(leaveButton).expandX().expandY();

        stage.addActor(table);

    }


    @Override
    public void show() {

        // Ponemos el canal alpha de la raíz (la tabla) a 0 (invisible)

        stage.getRoot().getColor().a = 0;

        // A continuación, aplicamos el efecto de fade in, que irá aumentando este valor hasta 1

        stage.addAction(fadeIn(1f));

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
