package com.gdx.dogs_and_dungeons.entities.enemies;

import com.badlogic.gdx.Gdx;
import java.util.ArrayList;

public class BalancedEnemy extends Enemy {

    private static final String specificPath = "skeleton.png";
    private static final String TAG = BalancedEnemy.class.getSimpleName();

    private float timer = 0;

    public BalancedEnemy(int width, int height,float drawWidth, float drawHeight) {

        super(width, height, drawWidth, drawHeight, specificPath);

        setState(State.WALKING);

        setVelocity(2f, 2f);
    }

    @Override
    public void behave(float delta) {

        calculateNextPosition(delta);

        updatePosition();

        restringeField(3);

        timer += delta;

        if (timer >= 3) {

            Gdx.app.debug(TAG, "Cambio de direccion despues de: " + String.valueOf(timer) + " s "  +  currentPosition.y + currentPosition.x);

            timer = 0;

            setDirection(getRandomDirection());
        }

    }

    public void restringeField(int i){
        float verticalLine1 = initialPosition.x - i;
        float verticalLine2 = initialPosition.x + i;
        float horizontalLine1 = initialPosition.y - i;
        float horizontalLine2 = initialPosition.y + i;

        if (currentPosition.x <= verticalLine1 || currentPosition.x >= verticalLine2){
            this.setDirection(this.getOppositeDirection());
        }
        if (currentPosition.y <= horizontalLine1 || currentPosition.y >= horizontalLine2){
            this.setDirection(this.getOppositeDirection());
        }
    }


}
