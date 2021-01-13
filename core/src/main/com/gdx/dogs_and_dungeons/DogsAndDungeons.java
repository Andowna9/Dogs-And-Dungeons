package com.gdx.dogs_and_dungeons;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.gdx.dogs_and_dungeons.screens.*;

// Clase para gestionar las distintas ventanas del juego gracias a la herencia de Game

public class DogsAndDungeons extends Game {

	private static final String TAG = DogsAndDungeons.class.getSimpleName();

	// Pantallas como atributos estáticos

	public static MainGameScreen mainGameScreen;
	public static OptionsScreen optionsScreen;
	public static UsersScreen usersScreen;
	public static MainScreen mainScreen;
	public static SelectionScreen selectionScreen;
	public static GameOverScreen gameOverScreen;
	public static VictoryScreen victoryScreen;

	// Preferencias estáticas del juego para un acceso simple desde cualquier otra clase

	// Nombre del fichero de preferencias

	public static final String PREFERENCES_FILE_NAME = "Preferences";

	public static class GamePreferences {

		public static boolean fullscreen;

		public static boolean musicOn;

		public static float volume;
	}

	// Carga de preferencias del usuario, las cuales se definen y modifican en el menú de opciones

	private void loadPreferences() {

		Gdx.app.log(TAG, "Cargando Preferencias de usuario ...");

		Preferences prefs = Gdx.app.getPreferences(PREFERENCES_FILE_NAME);

		// Volumen general (por defecto volumen medio)

		GamePreferences.volume = prefs.getFloat("volume",0.5f);

		Gdx.app.log(TAG, "Nivel de volumen (de 0 a 10): " + (int) (GamePreferences.volume * 10));

		// Música activada/desactivada (por defecto activada)

		GamePreferences.musicOn = prefs.getBoolean("musicOn", true);

		Gdx.app.log(TAG, "Música activada: " + GamePreferences.musicOn);


		// Pantalla completa (por defecto no)

		GamePreferences.fullscreen = prefs.getBoolean("fullscreen", false);

		Gdx.app.log(TAG,"Pantalla completa: " + GamePreferences.fullscreen);
	}

	@Override
	public void create() {

		loadPreferences();

		optionsScreen = new OptionsScreen(this);
		mainGameScreen = new MainGameScreen(this);
		usersScreen = new UsersScreen(this);
		mainScreen = new MainScreen(this);
		selectionScreen = new SelectionScreen(this);
		gameOverScreen = new GameOverScreen(this);
		victoryScreen = new VictoryScreen(this);

		// Por defecto se inicia la pantalla del juego como primera

		setScreen(mainGameScreen);
	}

	@Override
	public void dispose() {

		getScreen().dispose();

	}

}

