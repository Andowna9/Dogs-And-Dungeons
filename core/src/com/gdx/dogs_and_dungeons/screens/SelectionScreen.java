package com.gdx.dogs_and_dungeons.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.HorizontalGroup;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.VerticalGroup;
import com.badlogic.gdx.scenes.scene2d.ui.Widget;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.badlogic.gdx.utils.Align;
import com.gdx.dogs_and_dungeons.DogsAndDungeons;
import com.gdx.dogs_and_dungeons.Utility;

public class SelectionScreen implements Screen {

    // Código de pantalla de Asier
	private Stage stage;
	
	private HorizontalGroup horizontalGroup;
	
	private Table tableImage;
	
	private VerticalGroup verticalGroup;
	
	private TextButton newGameButton;
	
	private TextButton continueButton;
	
	private TextButton optionsButton;
	
	private TextButton backButton;
	
	private TextButton chooseButton;
	
	private DogsAndDungeons game_ref;
	
	private ShapeRenderer gradient;
	
	private Image image;
	
	private Label titleLabel;
	
	private SpriteDrawable boy;
	
	private SpriteDrawable girl;
	
	public SelectionScreen(DogsAndDungeons game) {

		this.game_ref = game;
		
		stage = new Stage();
		
		horizontalGroup = new HorizontalGroup();
		//horizontalGroup.setDebug(true);
		
		verticalGroup = new VerticalGroup();
		
		tableImage = new Table();
		//tableImage.setDebug(true);
		
		horizontalGroup.setFillParent(true);

		//verticalGroup.setDebug(true);
		
		newGameButton = new TextButton("Nueva Partida", Utility.DEFAULT_SKIN);
		
		continueButton = new TextButton("Continuar", Utility.DEFAULT_SKIN);
		
		optionsButton = new TextButton("Opciones", Utility.DEFAULT_SKIN);
		
		backButton = new TextButton("Atras", Utility.DEFAULT_SKIN);
		
		chooseButton = new TextButton("Choose", Utility.DEFAULT_SKIN);
		
		gradient = new ShapeRenderer();
		
		boy = new SpriteDrawable((new Sprite(new Texture(Gdx.files.internal("player/boy.png")))));
		
		girl =  new SpriteDrawable((new Sprite(new Texture(Gdx.files.internal("player/girl.png")))));
		
		image = new Image();
		image.setDrawable(boy);
		
		LabelStyle style = new LabelStyle();
		style.font = Utility.mainFont;
		titleLabel = new Label("Selecciona el genero", style);
		
		backButton.addListener(new ClickListener() {

			@Override

			public void clicked(InputEvent event, float x, float y) {

				game_ref.setScreen(DogsAndDungeons.mainScreen);

			}

		});
		
		chooseButton.addListener(new ClickListener() {

			@Override

			public void clicked(InputEvent event, float x, float y) {
				if(image.getDrawable() == boy) {
					image.setDrawable(girl);
				
				}
				else	{
					image.setDrawable(boy);
					
				}
				
			}

		});
		
		verticalGroup.addActor(newGameButton);
		
		verticalGroup.addActor(continueButton);
		
		verticalGroup.addActor(optionsButton);
		
		verticalGroup.addActor(backButton);
		verticalGroup.space(40);
				
		tableImage.add(titleLabel);
		tableImage.row();
		tableImage.add(image).expandY().padTop(20);
		tableImage.row();
		tableImage.add(chooseButton).expandY().padTop(20);
		
		
		horizontalGroup.addActor(verticalGroup);	
		horizontalGroup.addActor(tableImage);
		horizontalGroup.expand(true);
		horizontalGroup.space(100);
		horizontalGroup.center();
		stage.addActor(horizontalGroup);
	}
	
    @Override
    public void show() {
		Gdx.input.setInputProcessor(stage);
	}

    @Override
    public void render(float delta) {
    	Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
        
        gradient.begin(ShapeRenderer.ShapeType.Filled);
        gradient.rect(0, 0, 800, 600, Color.DARK_GRAY, Color.DARK_GRAY, Color.GOLD, Color.GOLD);
        gradient.end();
        
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
