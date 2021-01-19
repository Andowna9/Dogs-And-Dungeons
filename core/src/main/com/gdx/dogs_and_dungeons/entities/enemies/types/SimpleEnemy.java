package com.gdx.dogs_and_dungeons.entities.enemies.types;

import com.badlogic.gdx.Gdx;
import com.gdx.dogs_and_dungeons.entities.enemies.Enemy;

public class SimpleEnemy extends Enemy {

    private static final String TAG = Enemy.class.getSimpleName();

    private float timer = 0;

    public SimpleEnemy(String subtype) {

        super(subtype);

        type = Type.SIMPLE;
    }

    // Método que representa el comportamiento del enemigo

    @Override
    public void initEntity() {

        // Vida

        setHealth(3);

        setState(State.WALKING);

        setDirection(Direction.RIGHT);

        setVelocity(2f,2f);

        setDropCount(1);
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
