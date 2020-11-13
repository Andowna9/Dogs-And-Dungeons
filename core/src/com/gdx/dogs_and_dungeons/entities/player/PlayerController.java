package com.gdx.dogs_and_dungeons.entities.player;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;


public class PlayerController extends InputAdapter {

    private static final String TAG = PlayerController.class.getSimpleName();

    private Player player;

    private boolean movingUp = false;

    private boolean movingDown = false;

    private boolean movingLeft = false;

    private boolean movingRight = false;


    public PlayerController(Player player) {

        this.player = player;

    }


    @Override
    public boolean keyDown(int keycode) {

        if (keycode == Input.Keys.W ) {

            movingUp = true;
        }



        if (keycode == Input.Keys.A) {

            movingLeft = true;

        }

        if (keycode == Input.Keys.S) {

            movingDown = true;
        }

        if (keycode == Input.Keys.D) {

            movingRight = true;
        }

        return true;
    }

    @Override
    public boolean keyUp(int keycode) {

        if (keycode == Input.Keys.W ) {

            movingUp = false;
        }

        if (keycode == Input.Keys.A) {

            movingLeft = false;

        }

        if (keycode == Input.Keys.S) {

            movingDown = false;
        }

        if (keycode == Input.Keys.D) {

            movingRight = false;
        }

        return true;
    }

    public void processInput(float deltaTime) {

        if (movingUp) {

            player.calculateNextPosition(deltaTime);

            player.setState(Player.State.WALKING);

            player.setDirection(Player.Direction.UP);
        }

        else if (movingDown) {

            player.calculateNextPosition(deltaTime);

            player.setState(Player.State.WALKING);

            player.setDirection(Player.Direction.DOWN);

        }

        else if (movingLeft) {

            player.calculateNextPosition(deltaTime);

            player.setState(Player.State.WALKING);

            player.setDirection(Player.Direction.LEFT);

        }

        else if (movingRight) {

            player.calculateNextPosition(deltaTime);

            player.setState(Player.State.WALKING);

            player.setDirection(Player.Direction.RIGHT);

        }

        else {

            player.setState(Player.State.IDLE);

        }


    }



}