package com.gdx.dogs_and_dungeons.entities.player;

import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;


public class PlayerHUD extends ScreenAdapter {

    private static final String TAG = PlayerHUD.class.getSimpleName();

    private Stage stage;

    private StatusUI statusUI;

    private DialogUI dialogUI;

    public PlayerHUD(Camera camera, Player player) {

        Viewport viewport = new ScreenViewport(camera);

        stage = new Stage(viewport);

        statusUI = new StatusUI(player);

        dialogUI = new DialogUI("Hello my name is Alex, what about you?");

        stage.addActor(statusUI);
      //  stage.addActor(dialogUI);    // this is just to show the dialog


    }

    @Override
    public void render(float delta) {

        stage.act(delta);

        statusUI.update();

        stage.draw();
    }

    @Override
    public void dispose() {

        stage.dispose();
    }
}
