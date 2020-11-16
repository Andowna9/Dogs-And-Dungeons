package com.gdx.dogs_and_dungeons.entities.enemies;

import com.badlogic.gdx.Gdx;

import java.util.ArrayList;

public class BalancedEnemy extends Enemy {

    private static final String specificPath = "skeleton.png";
    private static final String TAG = BalancedEnemy.class.getSimpleName();

    private float timer = 0;
    private int ranNum;
    private ArrayList<Direction> movementArrayList = new ArrayList<>();



    public BalancedEnemy(int width, int height,float drawWidth, float drawHeight) {

        super(width, height, drawWidth, drawHeight, specificPath);

        setState(State.WALKING);

        setVelocity(2f, 2f);
    }

    @Override
    public void behave(float delta) {

        calculateNextPosition(delta);

        updatePosition();

        restringeField();

        timer += delta;

        if (timer >= 3) {

            Gdx.app.debug(TAG, "Cambio de direccion despues de: " + String.valueOf(timer) + " s          " + ranNum +  currentPosition.y + currentPosition.x);

            timer = 0;

            this.changeRandomDirection();
        }

    }

    public void changeRandomDirection(){
        movementArrayList.add(Direction.LEFT);
        movementArrayList.add(Direction.UP);
        movementArrayList.add(Direction.DOWN);
        movementArrayList.add(Direction.RIGHT);

        ranNum = (int) (Math.random()*4);

        this.setDirection(movementArrayList.get(ranNum));
    }

      /*  public void restringeField(float xHor1, float xHor2, float yVert1, float yVert2){ //Línea x de izquierda, línea x de derecha, línea y de arriba, línea y de abajo
        float currentPositionX = currentPosition.x;
        float currentPositionY = currentPosition.y;

        if (currentPosition.x == xHor1 || currentPosition.x == xHor2){
            this.setDirection(this.getOppositeDirection());
        }
        if (currentPosition.y == yVert1 || currentPosition.y == yVert2){
            this.setDirection(this.getOppositeDirection());
        }
    } */  // Not gonna use yet

    public void restringeField(){
        float verticalLine1 = initialPosition.x - 3;
        float verticalLine2 = initialPosition.x + 3;
        float horizontalLine1 = initialPosition.y - 3;
        float horizontalLine2 = initialPosition.y + 3;

        if (currentPosition.x <= verticalLine1 || currentPosition.x >= verticalLine2){
            this.setDirection(this.getOppositeDirection());
        }
        if (currentPosition.y <= horizontalLine1 || currentPosition.y >= horizontalLine2){
            this.setDirection(this.getOppositeDirection());
        }
    }

}
