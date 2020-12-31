package com.gdx.dogs_and_dungeons.entities.enemies;

import com.gdx.dogs_and_dungeons.entities.Entity;

// Clase abstracta, no queremos que sea instanciada

public abstract class Enemy extends Entity {

    public enum Type {

        SIMPLE, BALANCED, HARD
    }

    public enum Movement {

        MOVEMENT1, MOVEMENT2, MOVEMENT3
    }

    // Método que las clases hijas deben implementar para inicializar parámetros (cada enemigo los suyos)

    public abstract void initEnemy();

    // Método que determinará el comportamiento o lógica del enemigo

    public abstract void behave(float delta);


    private static final String generalPath = "enemy/";


    // mostly inherited Attributes

    // Positions and directions from inheritance
    // Texure region from inheritance
    // Default states from inheritance of class Entity
    // Animations from inheritance of class Entity
    // Collision box from inheritance

    // init() method inherited
    // update() inherited
    // setPosition() inherited
    // loadAnimations() inherited
    // setDirection() inherited
    // calculateNextPosition() inherited


    // Constructors

    // Cuando llamemos desde una clase hija de enemigo, sus rutas se concatenarán

    public Enemy(int width, int height, float drawWidth, float drawHeight, String specificPath) {

        super(width, height, drawWidth, drawHeight);

        animManager.loadDirectionalAnimations(generalPath + specificPath,State.WALKING);

        setDefaultTexture(State.WALKING, Direction.RIGHT);
    }

    // Constructor de enemigos con tamaños de sprites/dibujado por defecto

    public Enemy(String specificPath) {

        super(32,32,1f,1f);

        animManager.loadDirectionalAnimations(generalPath + specificPath,State.WALKING);

        setDefaultTexture(State.WALKING, Direction.RIGHT);

    }
}
