package com.gdx.dogs_and_dungeons;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.gdx.dogs_and_dungeons.screens.MainGameScreen;
import com.gdx.dogs_and_dungeons.screens.OptionsScreen;


// Clase para gestionar las distintas ventanas del juego gracias a la herencia de Game

public class DogsAndDungeons extends Game {

	// Pantalla como atributos estáticos

	public static MainGameScreen mainGameScreen;

	public static OptionsScreen optionsScreen;


	// Pantalla Alex

	// Pantalla Asier

	@Override
	public void create() {


		optionsScreen = new OptionsScreen();

		mainGameScreen = new MainGameScreen();

		// Por defecto se inicia la pantalla del juego como primera

		setScreen(mainGameScreen);

	}

	@Override
	public void dispose() {

		getScreen().dispose();

	}
}
