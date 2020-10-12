package com.gdx.dogs_and_dungeons;
import com.badlogic.gdx.Game;
import com.gdx.dogs_and_dungeons.screens.MainGameScreen;

// Clase para gestionar las distintas ventanas del juego gracias a la herencia de Game

public class DogsAndDungeons extends Game {


	public static final MainGameScreen mainGameScreen = new MainGameScreen();

	@Override
	public void create() {

		setScreen(mainGameScreen);

	}

	@Override
	public void dispose() {

		getScreen().dispose();

	}
}
