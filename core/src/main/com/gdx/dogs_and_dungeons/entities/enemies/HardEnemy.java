package com.gdx.dogs_and_dungeons.entities.enemies;

import com.badlogic.gdx.math.Rectangle;

public class HardEnemy extends Enemy {

    private static final String specificPath = "ghost.png";
    private static final String TAG = HardEnemy.class.getSimpleName();
    private Rectangle zonOfVision = new Rectangle(getCurrentPosition().x, getCurrentPosition().y, 30,30);
    private boolean isFollowing = false;

    public HardEnemy(int width, int height,float drawWidth, float drawHeight) {

        super(width, height, drawWidth, drawHeight, specificPath);
    }

    public HardEnemy() {

        super(specificPath);
    }

    @Override
    public void initEnemy() {

        setState(State.IDLE);

        setVelocity(2f, 2f);
    }

    @Override
    public void behave(float delta) {

    }
    public void followPlayer(float playerX, float playerY){
        if (zonOfVision.contains(playerX, playerY)){
            isFollowing = true;
        }
    }
    public void calculateDirection(float playerX, float playerY){
        /*if(isFollowing){
            if(getCurrentPosition().x > playerX){
                setDirection(Direction.LEFT);

          }
            */
    }



}
