package com.gdx.dogs_and_dungeons.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.BitmapFontCache;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ActorGestureListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
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


    // Diálogo para introducir clave/contraseña

    private class PasswordDialog extends Dialog {

        private TextField tPass;

        private Label outputMessage;

        private User user;

        private void updateSize() {

            getContentTable().getCell(outputMessage).height(outputMessage.getPrefHeight());


            // Actualizamos el tamaño del diálogo tras insertar el nuevo elemento (label)

            pack();

            // Se centra el diálogo

            setPosition(Math.round((stage.getWidth() - getWidth()) / 2), Math.round((stage.getHeight() - getHeight()) / 2));

        }


        public PasswordDialog(String title, Skin skin, User user) {

            super(title, skin);

            this.user = user;

            // Label para indicar que se espera la contraseña

            Label lPass = new Label(String.format("Introduce la clave para %s: ",user.getNickname()), Utility.DEFAULT_SKIN);

            // Mensaje de error o éxito después de comprobar contraseña

            outputMessage = new Label("",Utility.DEFAULT_SKIN);

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

                    if (password.isEmpty()) {

                        // Se borra el anterior mensaje y si no existía se añade una fila también

                        // Se añade de nuevo

                        outputMessage.setText("Clave incorrecta!");

                        outputMessage.setColor(Color.RED);


                    }

                    else {

                        outputMessage.setText("Clave correcta!");

                        outputMessage.setColor(Color.GREEN);

                        updateSize();

                        hide(Actions.fadeOut(1.5f));

                    }

                    updateSize();

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

            getContentTable().row();

            getContentTable().add(outputMessage).height(0);

        }

    }


    public UsersScreen(DogsAndDungeons game){

        this.game_ref = game;

        userModel = new Array<>();

        gradient = new ShapeRenderer();

        //Componentes gráficos

        stage = new Stage();

        userList = new List<>(Utility.DEFAULT_SKIN);

        userList.addListener(new ActorGestureListener() {

            @Override
            public void tap (InputEvent event, float x, float y, int count, int button) {

                if (count == 2) {

                    User selectedUser = userList.getSelected();

                    Gdx.app.log(TAG, "Usuario seleccionado: " + selectedUser);

                    // Mostramos un nuevo diálogo para introducir contraseña

                    PasswordDialog dPass = new PasswordDialog("Advertencia!", Utility.DEFAULT_SKIN,selectedUser);

                    dPass.show(stage);

                }

            }
        });

        addUser(new User("Asier", "añlsasal"));
        addUser(new User("Jon Andoni", "sañlkñsa"));
        addUser(new User("Alex", "alklas"));
        addUser(new User("Alexby11","LKAJ"));
        addUser(new User("Willyrex","alñjsksa"));
        addUser(new User("Vegeta777","lakKJASK"));
        addUser(new User("ElRubius","añksldsadñ"));

        bCreate = new TextButton("Crear usuario", Utility.DEFAULT_SKIN);

        bDelete = new TextButton("Borrar usuario", Utility.DEFAULT_SKIN);

        Label.LabelStyle style = new Label.LabelStyle();

        style.font = Utility.mainFont;

        lSelectUser = new Label("Selecciona un usuario:", style);

        ScrollPane scroller = new ScrollPane(userList, Utility.DEFAULT_SKIN);

        Table table = new Table();

        //table.setDebug(true); //Quitar comentario para ver la alineación de la tabla


        table.setFillParent(true);

        table.center();

        table.top();
        table.add(lSelectUser).padTop(25).colspan(2); //padTop(50).left().padLeft(50);
        table.row().expandY().space(20);
        table.add(scroller).colspan(2).width(500); //padLeft(100).padBottom(50).padTop(50);
        table.row().expandY().space(20);
        table.add(bCreate).right().padBottom(25); //padRight(50).padBottom(100);
        table.add(bDelete).left().padBottom(25);  //padRight(150).padBottom(100);


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
