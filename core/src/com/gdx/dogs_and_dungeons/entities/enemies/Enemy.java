package com.gdx.dogs_and_dungeons.entities.enemies;

import com.gdx.dogs_and_dungeons.entities.Entity;

// Clase abstracta, no queremos que sea instanciada

public abstract class Enemy extends Entity {

    public enum Type {

        ENEMY1, ENEMY2, ENEMY3, ENEMY4
    }

    public enum Movement {

        MOVEMENT1, MOVEMENT2, MOVEMENT3
    }


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

        super(width, height, drawWidth, drawHeight,generalPath + specificPath);

    }
}
