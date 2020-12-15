package com.gdx.dogs_and_dungeons.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Array;
import com.gdx.dogs_and_dungeons.DogsAndDungeons;
import com.gdx.dogs_and_dungeons.Utility;
import com.badlogic.gdx.scenes.scene2d.ui.List;
import com.gdx.dogs_and_dungeons.users.User;

public class UsersScreen implements Screen {

    private static final String TAG = UsersScreen.class.getSimpleName();

    private Stage stage;

    private List<User> userList;

    private Label lSelectUser;

    private TextButton bCreate;

    private TextButton bDelete;

    private DogsAndDungeons game_ref;

    private ShapeRenderer gradient;

    private Array<User> userModel;

    private PasswordDialog dPass;

    // Diálogo para introducir clave/contraseña

    class PasswordDialog extends Dialog {

        private TextField tPass;


        public PasswordDialog(String title, Skin skin) {

            super(title, skin);

            // Label para indicar que se espera la contraseña

            Label lPass = new Label("Introduce la clave: ", Utility.DEFAULT_SKIN);

            // Campo de texto oculto para que no se visualice la contraseña

            tPass = new TextField("", Utility.DEFAULT_SKIN);

            tPass.setPasswordCharacter('*');

            tPass.setPasswordMode(true);

            // Botón de confirmación

            TextButton confirmButton = new TextButton("OK",Utility.DEFAULT_SKIN);

            confirmButton.addListener(new ClickListener() {

                @Override
                public void clicked(InputEvent e, float x, float y) {

                    String password = tPass.getText();

                    // Se consulta si la contraseña del usuario es correcta

                    Gdx.app.debug(TAG, "Contraseña introducida: " + password);

                    hide();


                }

            });

            // Botón para cancelar y cerrar el diálogo

            TextButton cancelButton = new TextButton("Cancelar", Utility.DEFAULT_SKIN);

            cancelButton.addListener(new ClickListener() {

                @Override
                public void clicked(InputEvent e, float x, float y) {

                    hide();
                }
            });

            getContentTable().add(lPass);

            getContentTable().row().width(200);

            getContentTable().add(tPass);

            getButtonTable().add(cancelButton,confirmButton);


        }
    }

    public UsersScreen(DogsAndDungeons game){

        this.game_ref = game;

        userModel = new Array<>();

        // Diálogo para contraseña

        dPass = new PasswordDialog("Advertencia!", Utility.DEFAULT_SKIN);

        gradient = new ShapeRenderer();

        //Componentes gráficos

        stage = new Stage();

        userList = new List<>(Utility.DEFAULT_SKIN);

        addUser(new User("Asier", "Jauregui"));
        addUser(new User("Jon Andoni", "Castillo"));
        addUser(new User("Alex", "Nitu"));
        addUser(new User("Asier", "Josejuan Alberto"));
        addUser(new User("Jon Andoni", "Castillo"));
        addUser(new User("Alex", "Nitu"));
        addUser(new User("Asier", "Jauregui"));
        addUser(new User("Jon Andoni", "Castillo"));
        addUser(new User("Alex", "Nitu"));
        addUser(new User("Asier", "Jauregui"));
        addUser(new User("Jon Andoni", "Castillo"));
        addUser(new User("Alex", "Nitu"));
        addUser(new User("MMMMMeEEEEGGGGGGAAAAAMMMMIIIINNNNDDDDd", "Jauregui"));
        addUser(new User("Jon Andoni", "Castillo"));
        addUser(new User("Alex", "Nitu"));
        addUser(new User("Asier", "Jauregui"));
        addUser(new User("Jon Andoni", "Castillo"));
        addUser(new User("Alex", "Nitu"));

        bCreate = new TextButton("Crear usuario", Utility.DEFAULT_SKIN);

        bCreate.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {

                dPass.show(stage);

            }
        });

        bDelete = new TextButton("Borrar usuario", Utility.DEFAULT_SKIN);

        Label.LabelStyle style = new Label.LabelStyle();
        style.font = Utility.mainFont;

        lSelectUser = new Label("Selecciona un usuario:", style);

        ScrollPane scroller = new ScrollPane(userList);

        Table table = new Table();

        //table.setDebug(true); Quitar comentario para ver la alineación de la tabla


        table.setFillParent(true);

        table.top();
        table.add(lSelectUser).padTop(50).left().padLeft(50);
        table.row().expandY();
        table.add(scroller).padLeft(100).padBottom(50).padTop(50);
        table.row();
        table.add(bCreate).padRight(50).padBottom(100);
        table.add(bDelete).padRight(150).padBottom(100);


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
