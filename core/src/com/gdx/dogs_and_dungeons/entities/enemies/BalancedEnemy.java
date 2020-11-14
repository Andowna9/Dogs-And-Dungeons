package com.gdx.dogs_and_dungeons.entities.enemies;

public class BalancedEnemy extends Enemy {

    private static final String specificPath = "";

    public BalancedEnemy(int width, int height,float drawWidth, float drawHeight) {

        super(width, height, drawWidth, drawHeight, specificPath);
    }

    @Override
    public void behave(float delta) {

    }
}
