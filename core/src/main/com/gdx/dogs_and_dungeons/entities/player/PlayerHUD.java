package com.gdx.dogs_and_dungeons.entities.player;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;


public class PlayerHUD extends ScreenAdapter {

    private static final String TAG = PlayerHUD.class.getSimpleName();

    private Stage stage;

    private StatusUI statusUI;

    public PlayerHUD(Camera camera, Player player) {

        Viewport viewport = new ScreenViewport(camera);

        stage = new Stage(viewport);

        statusUI = new StatusUI(player);

        stage.addActor(statusUI);

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
