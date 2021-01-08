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

    private StatusUI statusUI;

    private DialogUI dialogUI;

    private PauseUI pauseUI;

    boolean isPaused;

    public PlayerHUD(Camera camera, Player player) {

        Viewport viewport = new ScreenViewport(camera);

        stage = new Stage(viewport);

        statusUI = new StatusUI(player);

        dialogUI = new DialogUI("Alex","Hello I'm Alex, what about you?");

        pauseUI = new PauseUI(this);

        pauseUI.setVisible(false);

        dialogUI.setVisible(false);

        // La tabla con el diálogo (Dialog UI) no se muestra por defecto

        stage.addActor(statusUI);
        stage.addActor(dialogUI);    // this is just to show the dialog
        stage.addActor(pauseUI);

    }

    @Override
    public void render(float delta) {

        stage.act(delta);

        statusUI.update();

        //Renderizado manual de UIs para tener un mayor control

        stage.getBatch().begin();

        statusUI.draw(stage.getBatch(), 1f);

        stage.getBatch().end();

        if (pauseUI.isVisible()) {

            pauseUI.renderFilter();

            stage.getBatch().begin();

            pauseUI.draw(stage.getBatch(), 1f);

            stage.getBatch().end();

        }

    }

    public void showPauseMenu() {

        pauseUI.setVisible(true);

        isPaused = true;
    }

    public void hidePauseMenu() {

        pauseUI.setVisible(false);

        isPaused = false;
    }


    public boolean isPaused() {

        return isPaused;
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
