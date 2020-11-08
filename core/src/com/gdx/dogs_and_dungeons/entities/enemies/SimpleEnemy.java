package com.gdx.dogs_and_dungeons.entities.enemies;

public class SimpleEnemy extends Enemy {

    private float timer = 0;

    private static final String specificPath = "pumpkin.png";

    public SimpleEnemy(int width, int height,float drawWidth, float drawHeight) {

        super(width, height, drawWidth, drawHeight, specificPath);

        setState(State.WALKING);

        setVelocity(2f,2f);

    }

    // Método que representa el comportamiento del enemigo

    public void behave(float delta) {

        calculateNextPosition(delta);

        updatePosition();

        timer += delta;

        if (timer >= 4) {

            timer = 0;

            currentDirection = getOppositeDirection();
        }

    }

}
