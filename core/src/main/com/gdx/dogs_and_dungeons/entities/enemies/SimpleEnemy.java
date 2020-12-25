package com.gdx.dogs_and_dungeons.entities.enemies;

import com.badlogic.gdx.Gdx;

public class SimpleEnemy extends Enemy {

    private static final String TAG = Enemy.class.getSimpleName();

    private float timer = 0;

    private static final String specificPath = "pumpkin.png";

    public SimpleEnemy(int width, int height,float drawWidth, float drawHeight, Direction direction) {

        super(width, height, drawWidth, drawHeight, specificPath);

        // Vida

        setHealth(3);

        setState(State.WALKING);

        setDirection(direction);

        setVelocity(2f,2f);

    }

    // Método que representa el comportamiento del enemigo

    public void behave(float delta) {

        calculateNextPosition(delta);

        updatePosition();

        timer += delta;

        if (timer >= 4) {

            Gdx.app.debug(TAG, "Cambio de direccion despues de: " + String.valueOf(timer) + " s");

            timer = 0;

            currentDirection = getOppositeDirection();
        }

    }

}
