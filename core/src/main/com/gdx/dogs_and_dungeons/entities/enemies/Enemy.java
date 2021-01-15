package com.gdx.dogs_and_dungeons.entities.enemies;

import com.gdx.dogs_and_dungeons.entities.Entity;

// Clase abstracta, no queremos que sea instanciada

public abstract class Enemy extends Entity {

    private static final String generalPath = "enemy/";

    // Enumeración para instanciar un tipo de enemigo

    public enum Type {

        SIMPLE, BALANCED, HARD, BOSS
    }


    // Método que determinará el comportamiento o lógica del enemigo

    public abstract void behave(float delta);

    // Constructor de enemigos con tamaños de sprites/dibujado por defecto

    public Enemy(String specificPath) {

        super(32,32,1f,1f);

        animManager.loadDirectionalAnimations(generalPath + specificPath + ".png",State.WALKING);

    }
}
