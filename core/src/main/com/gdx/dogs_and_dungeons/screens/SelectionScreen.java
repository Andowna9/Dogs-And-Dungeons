package com.gdx.dogs_and_dungeons.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.HorizontalGroup;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.VerticalGroup;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.gdx.dogs_and_dungeons.DogsAndDungeons;
import com.gdx.dogs_and_dungeons.Utility;
import com.gdx.dogs_and_dungeons.profiles.ProfileManager;
import com.gdx.dogs_and_dungeons.users.User;

public class SelectionScreen implements Screen {

    // Código de pantalla de Asier

	private Stage stage;
	
	private HorizontalGroup horizontalGroup;
	
	private Table tableImage;
	
	private VerticalGroup verticalGroup;
	
	private TextButton newGameButton;
	
	private TextButton continueButton;
	
	private TextButton backButton;
	
	private TextButton chooseButton;
	
	private DogsAndDungeons game_ref;
	
	private ShapeRenderer gradient;
	
	private Image image;
	
	private Label titleLabel;
	
	private SpriteDrawable boy;
	
	private SpriteDrawable girl;

	private static String gender = "boy";

	// Variables relativas al usuario

	private Label userLabel;

	private User user;
	
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
		
		backButton = new TextButton("Atras", Utility.DEFAULT_SKIN);
		
		chooseButton = new TextButton("Elegir", Utility.DEFAULT_SKIN);
		
		gradient = new ShapeRenderer();
		
		boy = new SpriteDrawable((new Sprite(new Texture(Gdx.files.internal("player/preview/boy.png")))));
		
		girl =  new SpriteDrawable((new Sprite(new Texture(Gdx.files.internal("player/preview/girl.png")))));
		
		image = new Image();
		image.setDrawable(boy);
		
		LabelStyle style = new LabelStyle();
		style.font = Utility.mainFont;
		titleLabel = new Label("Selecciona el género:", style);
		
		
		newGameButton.addListener(new ClickListener() {
			public void clicked(InputEvent event, float x, float y) {

				// Se borra el perfil y se carga uno nuevo

				ProfileManager.getInstance().deleteCurrentProfile();

				game_ref.setScreen(DogsAndDungeons.mainGameScreen);
			}
		});

		continueButton.addListener(new ClickListener() {

			@Override
			public void clicked(InputEvent event, float x, float y) {

				game_ref.setScreen(DogsAndDungeons.mainGameScreen);
			}

		});

		
		chooseButton.addListener(new ClickListener() {

			@Override

			public void clicked(InputEvent event, float x, float y) {

				if(image.getDrawable() == boy) {
					image.setDrawable(girl);
					gender = "girl";
				
				}
				else	{
					image.setDrawable(boy);
					gender = "boy";
				}
			}

		});
		
		backButton.addListener(new ClickListener() {

			@Override

			public void clicked(InputEvent event, float x, float y) {

				game_ref.setScreen(DogsAndDungeons.usersScreen);

			}

		});
		
		verticalGroup.addActor(newGameButton);
		
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

		userLabel = new Label("" , style);

		userLabel.setBounds(20, 20, 5, 5);

		stage.addActor(userLabel);

	}

	private void init() {

		user = UsersScreen.getSelectedUser();

		// Usuario cargado

		if (user != null) {

			userLabel.setText("Usuario: " + user.getNickname());

			ProfileManager.getInstance().setCurrentProfile(user.getNickname());

			// Solo se permite continuar si existe un perfil guardado

			if (ProfileManager.getInstance().profileExists(user.getNickname())) {

				verticalGroup.addActorAt(1,continueButton);
			}

			else {

				continueButton.remove();
			}

		}

		else {

			userLabel.setText("Sin Usuario");
		}


	}

	public static String getGender() {

		return gender;
	}
	
    @Override
    public void show() {
		init();
		Gdx.input.setInputProcessor(stage);
	}

    @Override
    public void render(float delta) {
    	Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
        
        gradient.begin(ShapeRenderer.ShapeType.Filled);
        gradient.rect(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), Color.DARK_GRAY, Color.DARK_GRAY, Color.GOLD, Color.GOLD);
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
