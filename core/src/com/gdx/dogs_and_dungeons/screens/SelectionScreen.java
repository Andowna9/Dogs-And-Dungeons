package com.gdx.dogs_and_dungeons.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.gdx.dogs_and_dungeons.DogsAndDungeons;
import com.gdx.dogs_and_dungeons.Utility;

public class SelectionScreen implements Screen {

    // Código de pantalla de Asier
	Stage stage;
	
	Table table;
	
	TextButton newGameButton;
	
	TextButton continueButton;
	
	TextButton optionsButton;
	
	TextButton backButton;
	
	DogsAndDungeons game;
	
	public SelectionScreen(final DogsAndDungeons game) {

		this.game = game;
		
		stage = new Stage();
		
		table = new Table();
		
		table.setFillParent(true);

		//table.setDebug(true);
		
		newGameButton = new TextButton("Nueva Partida", Utility.DEFAULT_SKIN);
		
		continueButton = new TextButton("Continuar", Utility.DEFAULT_SKIN);
		
		optionsButton = new TextButton("Opciones", Utility.DEFAULT_SKIN);
		
		backButton = new TextButton("Atras", Utility.DEFAULT_SKIN);

		backButton.addListener(new ClickListener() {

			@Override

			public void clicked(InputEvent event, float x, float y) {

				game.setScreen(DogsAndDungeons.mainScreen);

			}

		});
		
		
		int espacio = 200;
		table.top();
		
		table.row().padTop(20);
		table.add(newGameButton).expandX().left().padLeft(espacio);
		table.add();
		
		table.row().padTop(20);
		
		table.add(continueButton).left().padLeft(espacio + 14);
		
		table.row().padTop(20);
		
		table.add(optionsButton).left().padLeft(espacio + 17);
		
		table.row().padTop(20);
		
		table.add(backButton).left().padLeft(espacio + 32);
		
		
		table.center();
		
		stage.addActor(table);
	}
	
    @Override
    public void show() {
		Gdx.input.setInputProcessor(stage);
	}

    @Override
    public void render(float delta) {
    	Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		stage.act(delta); 
        stage.draw(); 
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

    }
}
