package com.gdx.dogs_and_dungeons.tests;

import com.gdx.dogs_and_dungeons.Entity;
import org.junit.Before;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EntityTest {

    private Entity e;

    @Before
    public void setUp(){
        // Se crea un objeto global para cada test
        //e = new Entity(200,200);
    }

    @Test
    void update() {
        e.update(0.0E00f);
    }

    @Test
    void setPosition() {
        e.setPosition(500, 600);
       // assertEquals((500,600), e.getCurrentPosition());
    }

    @Test
    void loadAnimations() {

    }

    @Test
    void setState() {
        e.setState(Entity.State.IDLE);
    }

    @Test
    void setDirection() {
        e.setDirection(Entity.Direction.DOWN);
    }

    @Test
    void calculateNextPosition() {
        e.calculateNextPosition(0.0E00f);
    }

    @Test
    void getCurrentTexture() {
    }

    @Test
    void getCurrentPosition() {
    }

    @Test
    void getCollisionBox() {
    }

    @Test
    void updatePosition() {
    }
}