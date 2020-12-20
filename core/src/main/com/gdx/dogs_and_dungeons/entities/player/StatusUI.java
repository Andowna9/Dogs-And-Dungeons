package com.gdx.dogs_and_dungeons.entities.player;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.gdx.dogs_and_dungeons.Utility;

// Actor (tabla) que contiene los gráficos para mostrar las características principales del jugador

public class StatusUI extends Table {

    private Image hpBar;

    private Label.LabelStyle style;

    private Player player;

    public StatusUI(Player player) {

        this.player = player;

        hpBar = new Image(Utility.STATUSUI_TEXTUREATLAS.findRegion("healthbar", 7));

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


    }
}
