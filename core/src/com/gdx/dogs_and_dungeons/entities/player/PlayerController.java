package com.gdx.dogs_and_dungeons.entities.player;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.gdx.dogs_and_dungeons.entities.Entity;


public class PlayerController extends InputAdapter {

    private static final String TAG = PlayerController.class.getSimpleName();

    private Player player;

    private boolean movingUp = false;

    private boolean movingDown = false;

    private boolean movingLeft = false;

    private boolean movingRight = false;

    private boolean attacking = false;


    public PlayerController(Player player) {

        this.player = player;

    }


    @Override
    public boolean keyDown(int keycode) {

        if (keycode == Input.Keys.W ) {

            movingUp = true;
            attacking = false;
        }


        if (keycode == Input.Keys.A) {

            movingLeft = true;
            attacking = false;

        }

        if (keycode == Input.Keys.S) {

            movingDown = true;
            attacking = false;
        }

        if (keycode == Input.Keys.D) {

            movingRight = true;
            attacking = false;
        }

        if (keycode == Input.Keys.F) {

            attacking = true;
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



        if (attacking) {

            if (player.getCurrentState() != Entity.State.ATTACKING) {

                player.setState(Entity.State.ATTACKING);

                player.resetAnimationTime();
            }

            if (player.animationIsFinished(Entity.State.ATTACKING)) {

                attacking = false;

                player.resetAnimationTime();

                player.setState(Entity.State.WALKING);
            }


        }

        else if (movingUp) {

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
