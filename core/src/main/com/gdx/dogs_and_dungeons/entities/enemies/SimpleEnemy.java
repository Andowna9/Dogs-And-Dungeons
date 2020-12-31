package com.gdx.dogs_and_dungeons.entities.enemies;

import com.badlogic.gdx.Gdx;

public class SimpleEnemy extends Enemy {

    private static final String TAG = Enemy.class.getSimpleName();

    private float timer = 0;

    private static final String specificPath = "pumpkin.png";

    public SimpleEnemy(int width, int height,float drawWidth, float drawHeight) {

        super(width, height, drawWidth, drawHeight, specificPath);

    }

    public SimpleEnemy() {

        super(specificPath);
    }

    // Método que representa el comportamiento del enemigo

    @Override
    public void initEnemy() {

        // Vida

        setHealth(3);

        setState(State.WALKING);

        setDirection(Direction.RIGHT);

        setVelocity(2f,2f);
    }

    public void behave(float delta) {

        timer += delta;

        if (timer >= 4) {

            Gdx.app.debug(TAG, "Cambio de direccion despues de: " + String.valueOf(timer) + " s");

            timer = 0;

            currentDirection = getOppositeDirection();
        }

    }

}
