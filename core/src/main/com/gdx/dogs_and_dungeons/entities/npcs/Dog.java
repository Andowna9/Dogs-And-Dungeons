package com.gdx.dogs_and_dungeons.entities.npcs;

import com.gdx.dogs_and_dungeons.managers.SpriteManager;

public class Dog extends NPC {

    private static final String specificPath = "animals/dog.png";

    private boolean leaving = false;

    public Dog(int width, int height, float drawWidth, float drawHeight) {
        super(width, height, drawWidth, drawHeight, specificPath);
        setPosition(25,10);
        setState(State.WALKING);
        setDirection(Direction.LEFT);
        setVelocity(1.0f,1.0f);
    }

    @Override
    public void behave(float delta) {

        calculateNextPosition(delta);

        if (!SpriteManager.mapManager.isCollidingWithMap(this)) {

            leaving = false;

        }

        else {

            if (!leaving) {

                leaving = true;

                currentDirection = getOppositeDirection();
            }
        }

    }
}
