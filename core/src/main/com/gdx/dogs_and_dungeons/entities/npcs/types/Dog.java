package com.gdx.dogs_and_dungeons.entities.npcs.types;

import com.gdx.dogs_and_dungeons.entities.npcs.NPC;
import com.gdx.dogs_and_dungeons.managers.SpriteManager;

public class Dog extends NPC {

    private boolean leaving = false;

    public Dog() {

        super("animals/dog");
    }

    @Override
    public void initEntity() {

        setState(State.WALKING);
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
