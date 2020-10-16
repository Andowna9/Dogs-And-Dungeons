package com.gdx.dogs_and_dungeons;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.gdx.dogs_and_dungeons.screens.MainGameScreen;

// Clase para gestionar las distintas ventanas del juego gracias a la herencia de Game

public class DogsAndDungeons extends Game {


	public static final MainGameScreen mainGameScreen = new MainGameScreen();

	public OrthographicCamera camera;   // Ortographic 2D camera. lookAt(float x, float y, float z) for scroll on player position
	public SpriteBatch spriteBatch;    // We're gonna use this to draw sprites http://rbwhitaker.wikidot.com/spritebatch-basics
	public BitmapFont bitmapFont;   //Bitmap font with sprites https://stackoverflow.com/questions/12895822/how-to-draw-a-bitmapfont-in-libgdx

	@Override
	public void create() {
		setScreen(mainGameScreen); // We set the main screen

		// Creation of declared variables
		spriteBatch = new SpriteBatch();
		bitmapFont = new BitmapFont();

	}

	@Override
	public void dispose() {
		// All the variables go here when disposed
		getScreen().dispose();
		spriteBatch.dispose();
		bitmapFont.dispose();
	}
}
