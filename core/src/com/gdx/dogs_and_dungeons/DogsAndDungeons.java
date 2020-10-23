package com.gdx.dogs_and_dungeons;
import com.badlogic.gdx.Game;
import com.gdx.dogs_and_dungeons.screens.MainGameScreen;
import com.gdx.dogs_and_dungeons.screens.OptionsScreen;


// Clase para gestionar las distintas ventanas del juego gracias a la herencia de Game

public class DogsAndDungeons extends Game {

	// Pantalla como atributos estáticos

	public static final MainGameScreen mainGameScreen = new MainGameScreen();

	public static final OptionsScreen optionsScreen = new OptionsScreen();

	// Pantalla Alex

	// Pantalla Asier

	@Override
	public void create() {

		// Por defecto se inicia la pantalla del juego como primera

		setScreen(mainGameScreen);

	}

	@Override
	public void dispose() {

		getScreen().dispose();

	}
}
