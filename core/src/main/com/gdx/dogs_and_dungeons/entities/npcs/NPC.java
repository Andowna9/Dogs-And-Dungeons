package com.gdx.dogs_and_dungeons.entities.npcs;

import com.gdx.dogs_and_dungeons.entities.Entity;

public abstract class NPC extends Entity {

    private static final String generalPath = "npc/";

    private String name = "";

    public abstract void behave(float delta);

    public abstract void initNPC();

    // All methods inherited from class Entity

    // Constructor completo
    public NPC(int width, int height, float drawWidth, float drawHeight, String specificPath) {

        super(width, height, drawWidth, drawHeight);

        animManager.loadDirectionalAnimations(generalPath + specificPath,State.WALKING);

    }

    // Constructor con valores por defecto

    public NPC(String specificPath) {

        super(32, 32, 1f, 1f);

        animManager.loadDirectionalAnimations(generalPath + specificPath,State.WALKING);

    }

    public String getName() {

        return name;
    }

    public void setName(String n) {

        if (n != null) {

            name = n;

        }
    }


}

