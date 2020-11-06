package com.gdx.dogs_and_dungeons;
import com.badlogic.gdx.Game;
import com.gdx.dogs_and_dungeons.screens.*;

// Clase para gestionar las distintas ventanas del juego gracias a la herencia de Game

public class DogsAndDungeons extends Game {

	// Pantalla como atributos estáticos

	public static MainGameScreen mainGameScreen;
	public static OptionsScreen optionsScreen;
	public static MainScreen mainScreen;
	public static SelectionScreen selectionScreen;
	public static GameOverScreen gameOverScreen;

	@Override
	public void create() {

		optionsScreen = new OptionsScreen(this);
		mainGameScreen = new MainGameScreen(this);
		mainScreen = new MainScreen(this);
		selectionScreen = new SelectionScreen(this);
		gameOverScreen = new GameOverScreen(this);

		// Por defecto se inicia la pantalla del juego como primera

		setScreen(mainGameScreen);
	}

	@Override
	public void dispose() {

		getScreen().dispose();

	}
}

