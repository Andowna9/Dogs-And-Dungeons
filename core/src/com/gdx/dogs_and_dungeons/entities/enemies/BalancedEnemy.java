package com.gdx.dogs_and_dungeons.entities.enemies;

import com.badlogic.gdx.Gdx;

import java.util.ArrayList;

public class BalancedEnemy extends Enemy {

    private static final String specificPath = "pumpkin.png";
    private static final String TAG = BalancedEnemy.class.getSimpleName();

    private float timer = 0;
    private int ranNum;
    private ArrayList<Direction> movementArrayList = new ArrayList<Direction>();



    public BalancedEnemy(int width, int height,float drawWidth, float drawHeight) {

        super(width, height, drawWidth, drawHeight, specificPath);

        setState(State.WALKING);

        setVelocity(2f, 2f);
    }

    @Override
    public void behave(float delta) {

        calculateNextPosition(delta);

        updatePosition();

        timer += delta;

        if (timer >= 4) {

            Gdx.app.debug(TAG, "Cambio de direccion despues de: " + String.valueOf(timer) + " s" + ranNum);

            timer = 0;

            this.changeRandomDirection();
        }

    }

    public void changeRandomDirection(){
        movementArrayList.add(Direction.LEFT);
        movementArrayList.add(Direction.UP);
        movementArrayList.add(Direction.DOWN);
        movementArrayList.add(Direction.RIGHT);

        ranNum = (int) (Math.random()*4+1);

        this.setDirection(movementArrayList.get(ranNum));
    }


}
