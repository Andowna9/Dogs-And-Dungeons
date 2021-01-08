package com.gdx.dogs_and_dungeons.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ActorGestureListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Timer;
import com.gdx.dogs_and_dungeons.DBManager;
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

    private TextButton bBack;

    private DogsAndDungeons game_ref;

    private ShapeRenderer gradient;

    private Array<User> userModel;

    private DBManager dbManager = DBManager.getInstance();

    // Usuario seleccionado

    private static User selectedUser;

    // Método para actualizar tamaño del diálogo

    private void updateSize(Dialog d, Label outputMessage) {

        d.getContentTable().getCell(outputMessage).height(outputMessage.getPrefHeight());


        // Actualizamos el tamaño del diálogo tras insertar el nuevo elemento (label)

        d.pack();

        // Se centra el diálogo

        d.setPosition(Math.round((stage.getWidth() - d.getWidth()) / 2), Math.round((stage.getHeight() - d.getHeight()) / 2));

    }


    // Diálogo de confirmación

    private  class ConfirmationDialog extends Dialog {

        public ConfirmationDialog(String title, String message, Skin skin) {

            super(title, skin);

            TextButton confButton = new TextButton("Confirmar", Utility.DEFAULT_SKIN);

            confButton.addListener(new ClickListener() {

                @Override
                public void clicked(InputEvent event, float x, float y) {

                    if (userList.getSelected() == null) return;

                    // Eliminamos al usuario de la base de datos

                    dbManager.deleteUser(userList.getSelected());

                    // Eliminamos al usuario de memoria

                    removeUser(userList.getSelected());

                    // Ocultamos el diálogo

                    hide();

                }
            });

            TextButton cancelButton = new TextButton("Cancelar", Utility.DEFAULT_SKIN);

            cancelButton.addListener(new ClickListener() {

                @Override
                public void clicked(InputEvent event, float x, float y) {

                    hide();

                }

            });

            // Añadimos el mensaje

            getContentTable().add(new Label(message, Utility.DEFAULT_SKIN)).pad(20,20,20,20);

            // Añadimos botones

            getButtonTable().add(cancelButton);

            getButtonTable().add(confButton);

        }
    }

    // Creation Dialog
    private class CreationDialog extends Dialog{

        private Label lNickname;
        private Label lPassword;
        private Label lName;
        private Label lSurname;
        private Label outputMessage;
        private TextField tNickname;
        private TextField tPassword;
        private TextField tName;
        private TextField tSurname;
        private Button bCreateUser;
        private Button bExit;


        public CreationDialog(String title, Skin skin) {
            super(title, skin);

            Label info = new Label("Completa los siguientes campos: ", Utility.DEFAULT_SKIN);

            lNickname = new Label("Usuario: ", Utility.DEFAULT_SKIN);
            lPassword = new Label("Clave: ", Utility.DEFAULT_SKIN);
            lName = new Label("Nombre: ", Utility.DEFAULT_SKIN);
            lSurname = new Label("Apellido: ", Utility.DEFAULT_SKIN);
            tNickname = new TextField("", Utility.DEFAULT_SKIN);
            tPassword = new TextField("", Utility.DEFAULT_SKIN);
            tName = new TextField("", Utility.DEFAULT_SKIN);
            tSurname = new TextField("", Utility.DEFAULT_SKIN);

            outputMessage = new Label("", Utility.DEFAULT_SKIN);


            tPassword.setPasswordMode(true);
            tPassword.setPasswordCharacter('*');

            // Botón crear

            bCreateUser = new TextButton("Crear", Utility.DEFAULT_SKIN);
            bCreateUser.addListener(new ClickListener() {

                @Override
                public void clicked(InputEvent e, float x, float y) {
                    String nickname = tNickname.getText();
                    String password = tPassword.getText();
                    String name = tName.getText();
                    String surname = tSurname.getText();

                    User createdUser = new User(name, surname, nickname);

                    // Contador del número de campos obligatorios restantes

                    int fieldsLeft = 0;

                    // Falta alguno de los campos obligatorios

                    // 1. Falta el nombre de usuario

                    if (nickname.isEmpty()) {

                        fieldsLeft++;

                        // Si no está marcado el label, lo marcamos

                        if (!lNickname.getText().contains("*")) {

                            lNickname.setText("* " + lNickname.getText());

                            lNickname.setColor(Color.RED);

                        }


                    }

                    else {

                        // Se desmarca el label porque el nombre de usuario introducido no es vacío

                        lNickname.setText(lNickname.getText().toString().replace("*", ""));

                        lNickname.setColor(Color.WHITE);

                    }

                    // 2. Falta la contraseña

                    if (password.isEmpty()) {

                        fieldsLeft++;

                        if (!lPassword.getText().contains("*")) {

                            lPassword.setText("* " + lPassword.getText());

                            lPassword.setColor(Color.RED);
                        }

                    }

                    else {

                        lPassword.setText(lPassword.getText().toString().replace("*", ""));

                        lPassword.setColor(Color.WHITE);
                    }

                    if (fieldsLeft >= 1) {

                        outputMessage.setText(String.format("%d campo/s obligatorio/s restante/s", fieldsLeft));

                        outputMessage.setColor(Color.RED);
                    }

                    // Si el usuario ya existe, lanzamos un aviso

                    else if (userExists(createdUser)) {

                        outputMessage.setText("El usuario ya existe!");

                        outputMessage.setColor(Color.GRAY);

                    }

                    else {

                        // Guardado en base de datos

                        dbManager.storeUser(createdUser, password);

                        // Añadimos el usuario a la lista en memoria

                        addUser(createdUser);

                        outputMessage.setText("Usuario creado correctamente!");

                        outputMessage.setColor(Color.GREEN);

                        hide(Actions.fadeOut(2f));

                    }

                    updateSize(CreationDialog.this, outputMessage);

                }});

            // Botón salir
            bExit = new TextButton("Cancelar", Utility.DEFAULT_SKIN);
            bExit.addListener(new ClickListener() {

                @Override
                public void clicked(InputEvent e, float x, float y) {

                    hide();
                }
            });

            // Button table

            getButtonTable().add(bCreateUser);
            getButtonTable().add(bExit);

            // Content Table
            getContentTable().padTop(20);
            getContentTable().padLeft(40);
            getContentTable().padRight(40);
            getContentTable().add(info).colspan(2).space(20);
            getContentTable().row();
            getContentTable().add(lNickname);
            getContentTable().add(tNickname);
            getContentTable().row().space(10);
            getContentTable().add(lPassword);
            getContentTable().add(tPassword);
            getContentTable().row().space(10);
            getContentTable().add(lName);
            getContentTable().add(tName);
            getContentTable().row().space(10);
            getContentTable().add(lSurname);
            getContentTable().add(tSurname);
            getContentTable().row();
            getContentTable().add(outputMessage).colspan(2).height(0);

        }
    }


    // Diálogo para introducir clave/contraseña

    private class PasswordDialog extends Dialog {

        private TextField tPass;

        private Label outputMessage;

        private User user_ref;



        public PasswordDialog(String title, Skin skin, User user) {

            super(title, skin);

            user_ref = user;

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

                        outputMessage.setText("Clave no proporcionada");

                        outputMessage.setColor(Color.GRAY);


                    }

                    else if (!dbManager.isPasswordValid(user_ref.getId(), password)) {

                        outputMessage.setText("La clave no es correcta!");

                        outputMessage.setColor(Color.RED);
                    }

                    else {

                        outputMessage.setText("Clave correcta!");

                        outputMessage.setColor(Color.GREEN);

                        // Guardamos en el atributo estático de clase el usuario escogido

                        selectedUser = user_ref;

                        Gdx.app.log(TAG, "Usuario elegido: " + selectedUser);

                        float fadeTime = 1.5f;

                        hide(Actions.fadeOut(fadeTime));

                        // Creamos un timer que cambie de pantalla una vez se desvanezca el diálogo

                        Timer timer = new Timer();

                        timer.scheduleTask(new Timer.Task() {
                            @Override
                            public void run() {

                                game_ref.setScreen(DogsAndDungeons.selectionScreen);
                            }
                        }, fadeTime);

                    }

                    updateSize(PasswordDialog.this, outputMessage);

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

        bCreate = new TextButton("Crear usuario", Utility.DEFAULT_SKIN);

        bCreate.addListener(new ClickListener() {

            @Override
            public void clicked(InputEvent e, float x, float y) {
                CreationDialog dCreate = new CreationDialog("Crear Usuario", Utility.DEFAULT_SKIN);
                dCreate.show(stage);
            }
        });

        bDelete = new TextButton("Borrar usuario", Utility.DEFAULT_SKIN);

        bDelete.addListener(new ClickListener() {

            @Override
            public void clicked(InputEvent event, float x, float y) {

                String message = "Se va a borrar el usuario: " + userList.getSelected();

                ConfirmationDialog confDialog = new ConfirmationDialog("Aviso!",message,Utility.DEFAULT_SKIN);

                confDialog.show(stage);

            }

        });

        TextButton bBack = new TextButton("Pantalla principal", Utility.DEFAULT_SKIN);

        bBack.addListener(new ClickListener() {

            @Override

            public void clicked(InputEvent event, float x, float y) {

                game_ref.setScreen(DogsAndDungeons.mainScreen);

            }
        });

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
        table.row();
        table.add(bBack).colspan(2).padBottom(20);

        stage.addActor(table);

    }

    // Añade un usuario al modelo de datos (si está en la lista)

    private void addUser(User user){

        // Nos aseguramos de que no se añaden usuarios duplicados al modelo

        if (!userExists(user)) {

            userModel.add(user);

            userList.setItems(userModel);

        }
    }



    // Elimina un usuario del modelo de datos (si está en la lista)

    private void removeUser(User user) {

        if (userExists(user)) {

            userModel.removeValue(user, false);

            userList.setItems(userModel);
        }

    }

    private boolean userExists(User user) {

        return userModel.contains(user, false);
    }

    public static User getSelectedUser() {

        return selectedUser;
    }

    // Al mostrar la pantalla, se inicia la conexión con la base de datos y se cargan

    @Override
    public void show() {

        Gdx.input.setInputProcessor(stage);

        // Iniciamos conexión con la base de datos

        dbManager.connect("core/database/user.db");

        // Carga de usuarios de la base de datos

        for (User user: dbManager.getAllUsers()) {

            // Este método no permite añadir usuarios repetidos a la lista, luego no hace falta comprobar si los usuariso ya están en memoria

            addUser(user);
        }

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);


        gradient.begin(ShapeRenderer.ShapeType.Filled);
        gradient.rect(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), Color.DARK_GRAY, Color.DARK_GRAY, Color.GRAY, Color.GRAY);
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

        dbManager.disconnect();
    }

    @Override
    public void dispose() {

        dbManager.disconnect();
    }
}
