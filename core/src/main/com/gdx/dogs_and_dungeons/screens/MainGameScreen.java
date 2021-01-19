package com.gdx.dogs_and_dungeons.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.TimeUtils;
import com.gdx.dogs_and_dungeons.*;
import com.gdx.dogs_and_dungeons.entities.player.hud.ui.StatusUI;
import com.gdx.dogs_and_dungeons.managers.CameraManager;
import com.gdx.dogs_and_dungeons.managers.GameStateManager;
import com.gdx.dogs_and_dungeons.managers.SpriteManager;
import com.gdx.dogs_and_dungeons.managers.RenderManager;
import com.gdx.dogs_and_dungeons.profiles.ProfileManager;
import com.gdx.dogs_and_dungeons.profiles.ProfileObserver;

// Pantalla de juego

public class MainGameScreen implements Screen, ProfileObserver {

    private static final String TAG = MainGameScreen.class.getSimpleName();

    private DogsAndDungeons game_ref;

    private SpriteBatch batch;

    // Fuente para mostrar fps

    private BitmapFont font;

    // Gestor de entidades

    private SpriteManager spriteManager;

    // Gestor de renderizado de entidades

    private RenderManager renderManager;

    // Gestor de cámara

    private CameraManager cameraManager;

    // Gestor de estados del juegi

    private GameStateManager gameStateManager;

    // Tiempos de pausa

    private long pauseTime;

    private long pauseStart;

    private ShapeRenderer shapeRenderer;

    private float alpha = 0f;

    //Tiempo de juego

    private long startTime = 0;

    public static long playedTime = 0;

    public MainGameScreen(DogsAndDungeons game) {

        ProfileManager.getInstance().addObserver(this);

        game_ref = game;

        shapeRenderer = new ShapeRenderer();

        batch = new SpriteBatch();

        font = new BitmapFont();

        spriteManager = new SpriteManager(game);

        renderManager = new RenderManager(spriteManager);

        gameStateManager = new GameStateManager(this,spriteManager);

        cameraManager = new CameraManager(renderManager, spriteManager);

    }


    @Override
    public void show() {

        // Carga de perfil guardado

        ProfileManager.getInstance().loadProfile();

        // Se establece pantalla completa dependiendo de las preferencias

        if (DogsAndDungeons.GamePreferences.fullscreen)
            Gdx.graphics.setFullscreenMode(Gdx.graphics.getDisplayMode());

        alpha = 0;

        cameraManager.init();

        gameStateManager.setCurrentGameState(GameStateManager.GameState.PLAYING);

        spriteManager.init();

        Gdx.input.setInputProcessor(spriteManager.getPlayerController());

        // Comenzamos a contar el tiempo de juego

        startTime = TimeUtils.millis();
    }


    @Override
    public void render(float delta) {

        delta = delta - pauseTime / 1000f;

        pauseTime = 0;

        // *LIMPIANDO PANTALLA*

        Gdx.gl.glClearColor(0, 0, 0, 1);

        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);


        // *ACTUALIZACIONES*

        cameraManager.updateCamera();

        // El GameStateManager actualiza el estado del juego si es necesario

        gameStateManager.updateState(delta);

        // Antes de renderizar ajustamos la vista del mapa, ya que hemos actualizado la cámara

        // *RENDERIZADO DE TEXTURAS (GRÁFICOS)*

        renderManager.render(delta);

        // Renderizado de texto para mostrar FPS en cada instante (con otro batch)

        batch.begin();

        font.draw(batch,String.valueOf(Gdx.graphics.getFramesPerSecond()),20,20);

        // Rectángulo negro que servirá para lograr fadeOut

        batch.end();

        Gdx.gl.glEnable(GL20.GL_BLEND);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);

        shapeRenderer.setColor(0,0,0,alpha);

        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);

        shapeRenderer.rect(0,0,Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        shapeRenderer.end();

    }

    public void switchScreen(Screen newScreen, float delta) {

        alpha += delta;

        if (alpha >= 1) {

            game_ref.setScreen(newScreen);

        }


    }

    // Actualiza el tiempo jugado en memoria

    private void updatePlayedTime(){

        playedTime +=  TimeUtils.timeSinceMillis(startTime);


    }


    @Override
    public void resize(int width, int height) {

        spriteManager.playerHUD.resize(width, height);
    }

    @Override
    public void pause() {

        pauseStart = System.currentTimeMillis();

        Gdx.app.debug(TAG, "Juego Pausado");

        updatePlayedTime();

    }

    @Override
    public void resume() {

        Gdx.app.debug(TAG, "Juego Reanudado");

        pauseTime = System.currentTimeMillis() - pauseStart;

        pauseStart = 0;

        // Reiniciamos el tiempo inicial para seguir contando el tiempo jugado

        startTime = TimeUtils.millis();

    }

    // Cuando el juego está en pantalla completa y se oculta (se pasa a otra pantalla), se vuelve
    // a establecer el modo ventana

    @Override
    public void hide() {

        if (Gdx.graphics.isFullscreen()) {

            Gdx.graphics.setWindowedMode(800, 500);
        }

        // Paramos los contadores si había

        StatusUI.stopCountdowns();

        // Por defecto, se guarda al ocultar la mainGameScreen

        ProfileManager.getInstance().saveProfile();
    }

    @Override
    public void dispose() {

        // También guardamos en caso de cierre

        ProfileManager.getInstance().saveProfile();
    }

    @Override
    public void onNotify(ProfileManager subject, ProfileEvent event) {

        if (event == ProfileEvent.SAVING_PROFILE) {

            updatePlayedTime();

            subject.setProperty("Played Time", playedTime);

        }

        else if (event == ProfileEvent.LOADING_PROFILE) {

            playedTime = subject.getProperty("Played Time", Long.class,0L);

        }
    }

}
