package com.gdx.dogs_and_dungeons;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

public class Enemy extends Entity{

    public enum Movement {

        MOVEMENT1, MOVEMENT2, MOVEMENT3
    }

    public enum Direction {

        UP,DOWN,LEFT,RIGHT
    }

    public enum State {

        IDLE, WALKING
    }

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
    public Enemy(int width, int height) {
        super(width, height);
    }
}
