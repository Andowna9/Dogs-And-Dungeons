package com.gdx.dogs_and_dungeons.entities.npcs;

import com.gdx.dogs_and_dungeons.entities.Entity;

public abstract class NPC extends Entity {

    private static final String generalPath = "npc/";

    // Tipos de de NPCs: Útil a la hora de instanciar

    public enum Type {

        VILLAGER, CAT, DOG
    }

    public abstract void behave(float delta);

    // All methods inherited from class Entity

    // Constructor con valores por defecto

    public NPC(String specificPath) {

        super(32, 32, 1f, 1f);

        animManager.loadDirectionalAnimations(generalPath + specificPath + ".png", State.WALKING);

    }


}

