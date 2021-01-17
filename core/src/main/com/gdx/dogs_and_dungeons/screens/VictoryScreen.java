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
import com.gdx.dogs_and_dungeons.managers.SpriteManager;
import com.gdx.dogs_and_dungeons.profiles.ProfileManager;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.fadeIn;

// Pantalla que se muestra al completar el juego

public class VictoryScreen implements Screen {

    private DogsAndDungeons game_ref;

    private Stage stage;

    private Label timePlayedLabel;

    public VictoryScreen(DogsAndDungeons game) {

        game_ref = game;

        stage = new Stage();

        Table table = new Table();

        table.setFillParent(true);

        Label.LabelStyle titleStyle = new Label.LabelStyle();
        titleStyle.font = Utility.titleFont;
        titleStyle.fontColor = Color.GREEN;

        // Label de victoria

        Label victoryLabel = new Label("Victoria !", titleStyle);

        Label.LabelStyle mainStyle = new Label.LabelStyle();
        mainStyle.font = Utility.mainFont;
        mainStyle.font.getData().setScale(0.8f);

        // Label de felicitación

        Label congratsLabel = new Label(String.format("Felicidades por completar el juego, %s !", UsersScreen.getSelectedUser()),mainStyle);

        timePlayedLabel = new Label("", mainStyle);

        // Botones

        TextButton endButton = new TextButton("Terminar", Utility.DEFAULT_SKIN);

        endButton.addListener(new ClickListener() {

            @Override
            public void clicked(InputEvent event, float x, float y) {

                game_ref.setScreen(DogsAndDungeons.selectionScreen);
            }

        });

        table.add(victoryLabel).expandY();

        table.row();

        table.add(congratsLabel);

        table.row();

        table.add(timePlayedLabel);

        table.row();

        table.add(endButton).expandY();

        stage.addActor(table);
    }

    @Override
    public void show() {

        DateFormat dateFormat = new SimpleDateFormat("m:ss ");

        String playedTime = dateFormat.format(new Date(MainGameScreen.playedTime));

        timePlayedLabel.setText("Tiempo jugado: " + playedTime );

        stage.getRoot().getColor().a = 0;

        stage.addAction(fadeIn(1f));

        Gdx.input.setInputProcessor(stage);

        SpriteManager.audioManager.playMusic("victory");

        // Se reinicia el perfil asociado, ya que se ha pasado el juego

        ProfileManager.getInstance().resetProfile();
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

        SpriteManager.audioManager.stopMusic("victory");

    }

    @Override
    public void dispose() {

        stage.dispose();
    }
}
