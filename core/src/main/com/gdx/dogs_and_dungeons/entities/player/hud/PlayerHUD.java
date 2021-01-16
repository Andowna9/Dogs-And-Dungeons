package com.gdx.dogs_and_dungeons.entities.player.hud;

import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.gdx.dogs_and_dungeons.entities.player.Player;

public class PlayerHUD extends ScreenAdapter {

    private static final String TAG = PlayerHUD.class.getSimpleName();

    private Stage stage;

    // Interfaces gráficas que gestiona PlayerHUD

    private StatusUI statusUI;

    private DialogUI dialogUI;

    private PauseUI pauseUI;

    public PlayerHUD(Camera camera, Player player) {

        Viewport viewport = new ScreenViewport(camera);

        stage = new Stage(viewport);

        statusUI = new StatusUI();

        dialogUI = new DialogUI();

        pauseUI = new PauseUI();

        // El menú de pausa y la UI que muestra los diálogos son invisibles por defecto

        pauseUI.setVisible(false);

        dialogUI.setVisible(false);

        stage.addActor(statusUI);
        stage.addActor(dialogUI);
        stage.addActor(pauseUI);

    }

    @Override
    public void render(float delta) {

        stage.act(delta);

        statusUI.update();

        //Renderizado manual de UIs para tener un mayor control

        stage.getBatch().begin();

        statusUI.draw(stage.getBatch(), 1f);

        if (dialogUI.isVisible()) {

            dialogUI.update(delta);

            dialogUI.draw(stage.getBatch(), 1f);
        }

        stage.getBatch().end();

        if (pauseUI.isVisible()) {

            pauseUI.renderFilter();

            stage.getBatch().begin();

            pauseUI.draw(stage.getBatch(), 1f);

            stage.getBatch().end();
        }

    }

    // Muestra el menú de Pausa

    public void showPauseMenu() {

        pauseUI.setVisible(true);

    }


    // Método para comprobar si el HUD está en modo pausa

    public boolean isPaused() {

        return pauseUI.isVisible();
    }

    // Díalogo para interacciones con NPCs
    // Recibe el nombre del NPC, al igual que el texto que se quiere mostrar

    public void showDialog(String npcName, String text) {

        dialogUI.setTitle(npcName);

        dialogUI.setText(text);

        dialogUI.setVisible(true);
    }

    // Método para comprobar si el diálogo del HUD está activado

    public boolean isDialogActive() {

        return dialogUI.isVisible();
    }

    public Stage getStage() {

        return stage;
    }

    @Override
    public void resize(int width, int height) {

        stage.getViewport().setScreenSize(width, height);
    }

    @Override
    public void dispose() {

        stage.dispose();
    }
}
