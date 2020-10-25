package com.gdx.dogs_and_dungeons;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.gdx.dogs_and_dungeons.screens.MainGameScreen;
import com.gdx.dogs_and_dungeons.screens.MainScreen;
import com.gdx.dogs_and_dungeons.screens.OptionsScreen;
import com.gdx.dogs_and_dungeons.screens.SelectionScreen;


// Clase para gestionar las distintas ventanas del juego gracias a la herencia de Game

public class DogsAndDungeons extends Game {

	// Pantalla como atributos estáticos

	public static MainGameScreen mainGameScreen;
	public static OptionsScreen optionsScreen;
	public static MainScreen mainScreen;
	
	// Pantalla Alex

	// Pantalla Asier
	public static SelectionScreen selectionScreen;

	@Override
	public void create() {

		optionsScreen = new OptionsScreen(this);
		mainGameScreen = new MainGameScreen(this);
		mainScreen = new MainScreen(this);
		selectionScreen = new SelectionScreen();

		// Por defecto se inicia la pantalla del juego como primera

		setScreen(mainScreen);
	}

	@Override
	public void dispose() {

		getScreen().dispose();

	}
}

