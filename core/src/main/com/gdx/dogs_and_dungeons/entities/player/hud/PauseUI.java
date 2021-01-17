package com.gdx.dogs_and_dungeons.entities.player.hud;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.gdx.dogs_and_dungeons.DogsAndDungeons;
import com.gdx.dogs_and_dungeons.Utility;
import com.gdx.dogs_and_dungeons.managers.SpriteManager;
import com.gdx.dogs_and_dungeons.profiles.ProfileManager;

public class PauseUI extends Table {

    private static final Drawable pauseMode = new TextureRegionDrawable(new Texture("HUD/pause.png"));

    private float alpha = 0.5f;

    private ShapeRenderer shapeRenderer;

    private Image modeImage;

    private Label infoLabel;

    public PauseUI() {


        shapeRenderer = new ShapeRenderer();

        // Imagen de play o pausa

        modeImage = new Image(pauseMode);

        // Botón para reanudar la partida

        TextButton continueButton = new TextButton("Reanudar", Utility.DEFAULT_SKIN);

        continueButton.addListener(new ClickListener() {

            @Override
            public void clicked(InputEvent event, float x, float y) {


                setVisible(false);

            }
        });

        // Botón para guardar y salir de la partida

        TextButton leaveButton = new TextButton("Guardar y Salir", Utility.DEFAULT_SKIN);

        leaveButton.addListener(new ClickListener() {

            @Override
            public void clicked(InputEvent event, float x, float y) {

                // Se sale a la pantalla principal

                SpriteManager.game_ref.setScreen(DogsAndDungeons.mainScreen);

                setVisible(false);
            }

        });

        // Label que contiene texto para avisar de que se ha entrado en modo pausa

        Label.LabelStyle style = new Label.LabelStyle();

        style.font = Utility.mainFont;

        style.fontColor = Color.BLACK;

        infoLabel = new Label("Juego Pausado", style);

        setFillParent(true);

        add(infoLabel);

        add(modeImage).padLeft(10);

        row().space(50);

        add(continueButton).colspan(2);

        row().space(20);

        add(leaveButton).colspan(2);

    }


    public void renderFilter() {

        Gdx.gl.glEnable(GL20.GL_BLEND);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);

        shapeRenderer.setColor(Color.BLACK);
        shapeRenderer.setColor(0,0,0,alpha);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.rect(0,0,Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        shapeRenderer.end();

       Gdx.gl.glDisable(GL20.GL_BLEND);




    }



}
