package com.gdx.dogs_and_dungeons.entities.npcs;

import com.badlogic.gdx.Gdx;
import com.gdx.dogs_and_dungeons.entities.Entity;
import com.gdx.dogs_and_dungeons.entities.enemies.BalancedEnemy;

import java.util.ArrayList;

public abstract class NPC extends Entity {

    private static final String generalPath = "npc/";

    public abstract void behave(float delta);

    // All methods inherited from class Entity

    // Constructor
    public NPC(int width, int height, float drawWidth, float drawHeight, String specificPath) {

        super(width, height, drawWidth, drawHeight);

        animManager.loadDirectionalAnimations(generalPath + specificPath,State.WALKING);

        setDefaultTexture(State.WALKING, Direction.DOWN);
    }


}

