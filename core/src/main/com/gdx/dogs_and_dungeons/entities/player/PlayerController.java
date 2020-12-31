package com.gdx.dogs_and_dungeons.entities.player;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.gdx.dogs_and_dungeons.entities.Entity;
import com.gdx.dogs_and_dungeons.managers.SpriteManager;


public class PlayerController extends InputAdapter {

    private static final String TAG = PlayerController.class.getSimpleName();

    private Player player;

    private boolean movingUp ;

    private boolean movingDown;

    private boolean movingLeft;

    private boolean movingRight;

    private boolean attacking;


    public PlayerController(Player player) {

        this.player = player;

    }

    public void init() {

        movingUp = false;

        movingDown = false;

        movingLeft = false;

        movingRight = false;
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

    public void processInput() {

        if (attacking) {

            if (player.getCurrentState() != Entity.State.ATTACKING) {

                player.setState(Entity.State.ATTACKING);

                player.resetAnimationTime();

                SpriteManager.audioManager.playSound("drawDagger");
            }

            if (player.animationIsFinished(Entity.State.ATTACKING)) {

                attacking = false;

                player.resetAnimationTime();

                player.setState(Entity.State.IDLE);
            }


        }

        else if (movingUp) {

            player.setState(Player.State.WALKING);

            player.setDirection(Player.Direction.UP);
        }

        else if (movingDown) {

            player.setState(Player.State.WALKING);

            player.setDirection(Player.Direction.DOWN);

        }

        else if (movingLeft) {

            player.setState(Player.State.WALKING);

            player.setDirection(Player.Direction.LEFT);

        }

        else if (movingRight) {

            player.setState(Player.State.WALKING);

            player.setDirection(Player.Direction.RIGHT);

        }

        else {

            player.setState(Player.State.IDLE);

        }


    }



}
