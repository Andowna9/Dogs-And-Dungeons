package com.gdx.dogs_and_dungeons.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.utils.Array;
import com.gdx.dogs_and_dungeons.DogsAndDungeons;
import com.gdx.dogs_and_dungeons.Utility;
import com.badlogic.gdx.scenes.scene2d.ui.List;
import com.gdx.dogs_and_dungeons.users.User;

public class UsersScreen implements Screen {

    private Stage stage;

    private List userList;

    private Label lSelectUser;

    private TextButton bCreate;

    private TextButton bDelete;

    private DogsAndDungeons game_ref;

    private ShapeRenderer gradient;

    private Array<User> userModel;

    public UsersScreen(DogsAndDungeons game){

        this.game_ref = game;

        userModel = new Array<>();

        gradient = new ShapeRenderer();

        //Componentes gráficos

        stage = new Stage();

        userList = new List(Utility.DEFAULT_SKIN);

        addUser(new User("Asier", "Jauregui"));
        addUser(new User("Jon Andoni", "Castillo"));
        addUser(new User("Alex", "Nitu"));

        bCreate = new TextButton("Crear usuario", Utility.DEFAULT_SKIN);

        bDelete = new TextButton("Borrar usuario", Utility.DEFAULT_SKIN);

        Label.LabelStyle style = new Label.LabelStyle();
        style.font = Utility.mainFont;

        lSelectUser = new Label("Selecciona un usuario:", style);

        ScrollPane scroller = new ScrollPane(userList);

        Table table = new Table();
        table.setDebug(true);

        table.setFillParent(true);

        table.top();
        table.add(lSelectUser);
        table.row().expandY();
        table.add(scroller);
        table.row().expandY();
        table.add(bCreate);
        table.add(bDelete);


        stage.addActor(table);







    }

    private void addUser(User user){
        userModel.add(user);
        userList.setItems(userModel);
    }
    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);


        gradient.begin(ShapeRenderer.ShapeType.Filled);
        gradient.rect(0, 0, 800, 600, Color.DARK_GRAY, Color.DARK_GRAY, Color.GRAY, Color.GRAY);
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
