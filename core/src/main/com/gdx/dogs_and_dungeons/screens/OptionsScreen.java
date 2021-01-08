package com.gdx.dogs_and_dungeons.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.gdx.dogs_and_dungeons.DogsAndDungeons;
import com.gdx.dogs_and_dungeons.Utility;

public class OptionsScreen implements Screen {

    private Stage stage;

    private Table table;

    private Label label;

    private Slider volumeSlider;

    private Label volumeInfo;

    private Label volumeLabel;

    private Label musicLabel;

    private CheckBox musicBox;

    private Label screenLabel;

    private SelectBox<String> screenModes;

    private TextButton backButton;

    private TextButton applyButton;

    private DogsAndDungeons game_ref;

    private Texture backgroundTexture;

    private SpriteBatch batch;


    public OptionsScreen(DogsAndDungeons game) {

        game_ref = game;
        stage = new Stage();


        // La tabla es un layout muy flexible que permite colocar actores
        table = new Table();
        // table.setDebug(true); // Mostramos cómo se posicionan los elemnetos en la tabla
        // Hacemos que la tabla ocupe toda la pantalla
        table.setFillParent(true);

        Label.LabelStyle labelStyle = new Label.LabelStyle();
        labelStyle.font = Utility.mainFont;
        label = new Label("Opciones",labelStyle);
        volumeLabel = new Label("Volumen", labelStyle);
        musicLabel = new Label("Música",labelStyle);

        // Cargamos la skin, que contine texturas para todos los widgets (elementos UI)

        volumeInfo = new Label("", labelStyle);
        // El texto se reduce de acuerdo con el tamaño del label, no el del texto
        volumeInfo.setWrap(true);
        volumeSlider = new Slider(0,10,1,false, Utility.DEFAULT_SKIN);

        volumeSlider.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {

                float value = volumeSlider.getValue();

                volumeInfo.setText((int)(value * 10) + " %");
            }
        });

        musicBox = new CheckBox("",Utility.DEFAULT_SKIN);

        screenLabel = new Label("Modo Pantalla",labelStyle);
        screenModes = new SelectBox<String>(Utility.DEFAULT_SKIN);
        screenModes.setItems("Ventana", "Completa");

        backButton = new TextButton("Volver",Utility.DEFAULT_SKIN);
        backButton.addListener(new ClickListener() {


            @Override

            public void clicked(InputEvent event, float x, float y) {

                game_ref.setScreen(DogsAndDungeons.mainScreen);

            }


        });

        applyButton = new TextButton("Aplicar cambios",Utility.DEFAULT_SKIN);

        applyButton.addListener(new ClickListener() {

            @Override
            public void clicked(InputEvent event, float x, float y) {

                applyChanges();
            }
        });


        // Background batch and texture
        batch = new SpriteBatch();
        backgroundTexture = new Texture(Gdx.files.internal("backgrounds/Background8.png"));

        // Grupo horizontal que contiene el slider y el texto que muestra (porcentaje)

        HorizontalGroup hg = new HorizontalGroup();

        hg.addActor(volumeSlider);
        hg.space(20);
        hg.addActor(volumeInfo);
        // Adding components to table
        table.top();
        table.add(label).padTop(20).colspan(3);
        table.row().expandY();
        table.add(volumeLabel).expandX().right();
        table.add(hg).expandX().left().padLeft(40);
        table.row().expandY();
        table.add(musicLabel).right();
        table.add(musicBox).left().padLeft(40);
        table.row().expandY();
        table.add(screenLabel).right();
        table.add(screenModes).left().padLeft(40);
        table.row().expandY().expandX();
        table.add(backButton);
        table.add(applyButton).padLeft(40);
        stage.addActor(table);


        initUI();

    }

    // Inicializa la interfaz con las preferencias cargadas en memoria

    private void initUI() {

        // VOLUMEN

        volumeSlider.setValue(DogsAndDungeons.GamePreferences.volume * 10);

        // MÚSICA

        musicBox.setChecked(DogsAndDungeons.GamePreferences.musicOn);

        // MODO DE PANTALLA

        // Hacemos que la lista refleje los cambios guardados en las preferencias

        if (DogsAndDungeons.GamePreferences.fullscreen) screenModes.setSelected("Completa");

        else screenModes.setSelected("Ventana");

    }

    // Se aplican los cambios y se guardan en las preferencias para leerlas la próxima vez
    // que se inicie la aplicación

    private void applyChanges() {

        Preferences prefs = Gdx.app.getPreferences(DogsAndDungeons.PREFERENCES_FILE_NAME);

        // Volumen

        DogsAndDungeons.GamePreferences.volume = volumeSlider.getValue() / 10;

        prefs.putFloat("volume", DogsAndDungeons.GamePreferences.volume);

        // Música

        DogsAndDungeons.GamePreferences.musicOn = musicBox.isChecked();

        prefs.putBoolean("musicOn", DogsAndDungeons.GamePreferences.musicOn);

        // Modo de pantalla

        String mode = screenModes.getSelected();

        DogsAndDungeons.GamePreferences.fullscreen = mode.equalsIgnoreCase("completa");;

        prefs.putBoolean("fullscreen", DogsAndDungeons.GamePreferences.fullscreen);

        // Finalmente se escriben los cambios en el fichero correspondiente, es decir, se hacen persistentes
        // El formato utilizado es XML

        prefs.flush();

    }

    @Override
    public void show() {

        // La clase stage se encarga de gestionar los input que tinen lugar sobre todos sus hijos

        Gdx.input.setInputProcessor(stage);

    }

    @Override
    public void render(float delta) {

        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // Background
        batch.begin();
        batch.draw(backgroundTexture, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        batch.end();

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
