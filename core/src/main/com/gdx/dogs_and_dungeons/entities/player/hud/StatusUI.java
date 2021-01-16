package com.gdx.dogs_and_dungeons.entities.player.hud;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.HorizontalGroup;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.gdx.dogs_and_dungeons.Utility;
import com.badlogic.gdx.utils.TimeUtils;
import com.gdx.dogs_and_dungeons.entities.player.Countdown;
import com.gdx.dogs_and_dungeons.managers.SpriteManager;
import com.gdx.dogs_and_dungeons.profiles.ProfileManager;
import com.gdx.dogs_and_dungeons.profiles.ProfileObserver;

// Actor (tabla) que contiene los gráficos para mostrar las características principales del jugador

public class StatusUI extends Table implements ProfileObserver {

    private static final String TAG = StatusUI.class.getSimpleName();

    // Imágenes

    private Image hpBar;

    private Image log;

    private static Image sword;

    private static Image boots;

    // Estilo para hpLabel

    private Label.LabelStyle hpLabelstyle;

    // Número de troncos recogidos

    private static int logCounter = 0;

    // Labels que se actualizan

    private Label logLabel;

    private Label speedLabel;

    private Label damageLabel;

    // Cuentas atrás

    public static Countdown speedCountdown;

    public static Countdown damageCountdown;

    public long startTime;

    public StatusUI() {

        // Prueba de serialización

        ProfileManager.getInstance().addObserver(this);

        ProfileManager.getInstance().loadProfile();

        // Tiempo inicio

        startTime = TimeUtils.millis();

        // Carga de imágenes

        hpBar = new Image(Utility.STATUSUI_TEXTUREATLAS.findRegion("healthbar", 7));

        log = new Image(Utility.STATUSUI_TEXTUREATLAS.findRegion("log"));

        sword = new Image(new Texture(Gdx.files.internal("HUD/sword.png")));

        boots = new Image(new Texture(Gdx.files.internal("HUD/boots.png")));

        // Se escalan dichas imágenes

        float logScale = 1;

        log.scaleBy(logScale);

        float hpScale = 2;

        hpBar.scaleBy(hpScale);

        // Labels:

        // HP

        hpLabelstyle = new Label.LabelStyle();

        hpLabelstyle.font = Utility.gameFont;

        hpLabelstyle.fontColor = Color.GREEN;

        Label hpLabel = new Label("HP", hpLabelstyle);

        // Log - Tronco

        Label.LabelStyle logStyle = new Label.LabelStyle();

        logStyle.font = Utility.gameFont;

        logStyle.fontColor = Color.BROWN;

        logLabel = new Label("x" + logCounter, logStyle);

        // Label de velocidad extra

        Label.LabelStyle speedStyle = new Label.LabelStyle();

        speedStyle.font = Utility.gameFont;

        speedStyle.fontColor = Color.BLUE;

        speedLabel = new Label("", speedStyle);

        // Label de ataque(daño) adicional

        Label.LabelStyle damageStyle = new Label.LabelStyle();

        damageStyle.font = Utility.gameFont;

        damageStyle.fontColor = Color.RED;

        damageLabel = new Label("", damageStyle);

        // Se añaden los componentes a la tabla

        setFillParent(true);

        top();

        left();

        padLeft(20);

        padTop(20);

        add(hpLabel);

        add(hpBar).spaceLeft(10).padTop(hpBar.getPrefHeight() * hpScale);

        row().space(20);

        add(log).width(26).height(20).padTop(20);

        add(logLabel).padLeft(10);

        row().space(20);

        HorizontalGroup hg = new HorizontalGroup();

        add(hg).colspan(2).width(100);


        // Cuentas atrás

        speedCountdown = new Countdown(30, new Runnable() {
            @Override
            public void run() {

                Gdx.app.debug(TAG, "Cuenta atrás para velocidad terminada!");

                SpriteManager.player.restoreSpeed();
            }
        }, speedLabel, boots,hg);


        damageCountdown = new Countdown(60, new Runnable() {
            @Override
            public void run() {

                Gdx.app.debug(TAG, "Cuenta atrás para daño terminada!");

                SpriteManager.player.restoreDamage();

            }
        },damageLabel, sword, hg);
    }

    // 7, 6 y 5 -> Verde

    // 4 y 3 -> Amarillo

    // 2 y 1 -> Rojo

    public void update() {

        int health = SpriteManager.player.getHealth();

        if (health <= 0) return;

        if (health <= 2) {

           hpLabelstyle.fontColor = Color.RED;

        }

       else if (health <= 4) {

           hpLabelstyle.fontColor = Color.YELLOW;
       }

       else {

           hpLabelstyle.fontColor = Color.GREEN;
       }

       hpBar.setDrawable(new TextureRegionDrawable(Utility.STATUSUI_TEXTUREATLAS.findRegion("healthbar", health)));

       logLabel.setText("x"+ logCounter);
    }

    public static void incrementLogs() {

        logCounter++;

        Gdx.app.debug(TAG,""+logCounter);
    }
    public static int getLogs(){

        return logCounter;
    }

    public static boolean startCountdown(Countdown countdown) {

        if (!countdown.isFinished()) {

            countdown.increaseTime();

            return false;
        }


        countdown.start();

        return true;

    }


    @Override
    public void onNotify(ProfileManager subject, ProfileEvent event) {

        if (event == ProfileEvent.SAVING_PROFILE) {

            subject.setProperty("Log Count", logCounter);

        }

        else if (event == ProfileEvent.LOADING_PROFILE) {

            logCounter = subject.getProperty("Log Count", Integer.class);

        }
    }

}
