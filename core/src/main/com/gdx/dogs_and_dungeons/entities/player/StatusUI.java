package com.gdx.dogs_and_dungeons.entities.player;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.gdx.dogs_and_dungeons.Utility;

import java.awt.font.ImageGraphicAttribute;

// Actor (tabla) que contiene los gráficos para mostrar las características principales del jugador

public class StatusUI extends Table {

    private static final String TAG = StatusUI.class.getSimpleName();

    private Image hpBar;

    private Image log;

    private Label.LabelStyle style;

    private Player player;

    private static int logCounter = 0;

    private Label.LabelStyle logStyle;

    private Label logs;

    public StatusUI(Player player) {

        this.player = player;

        hpBar = new Image(Utility.STATUSUI_TEXTUREATLAS.findRegion("healthbar", 7));

        log = new Image(Utility.STATUSUI_TEXTUREATLAS.findRegion("log"));

        float logScale = 1;

        log.scaleBy(logScale);

        float hpScale = 2;

        hpBar.scaleBy(hpScale);

        setFillParent(true);

        //setDebug(true);

        top();

        left();

        padLeft(20);

        padTop(20);

        style = new Label.LabelStyle();

        style.font = Utility.gameFont;

        style.fontColor = Color.GREEN;

        add(new Label("HP",style));

        add(hpBar).spaceLeft(10).padTop(hpBar.getPrefHeight() * hpScale);

        row().space(20);

        add(log).width(26).height(20);

        logStyle = new Label.LabelStyle();

        logStyle.font = Utility.gameFont;

        logStyle.fontColor = Color.BROWN;

        logs = new Label("x" + logCounter, logStyle);

        add(logs).padBottom(20).padLeft(10);
    }

    // 7, 6 y 5 -> Verde

    // 4 y 3 -> Amarillo

    // 2 y 1 -> Rojo

    public void update() {

        int health = player.getHealth();

        if (health <= 0) return;

       if (health <= 2) {

           style.fontColor = Color.RED;

       }

       else if (health <= 4) {

           style.fontColor = Color.YELLOW;
       }

       else {

           style.fontColor = Color.GREEN;
       }

       hpBar.setDrawable(new TextureRegionDrawable(Utility.STATUSUI_TEXTUREATLAS.findRegion("healthbar", health)));

       logs.setText("x"+ logCounter);
    }
    public static void incrementLogs(){
        logCounter++;
        Gdx.app.debug(TAG,""+logCounter);
    }
}
