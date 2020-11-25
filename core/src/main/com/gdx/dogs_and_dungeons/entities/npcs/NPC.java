package com.gdx.dogs_and_dungeons.entities.npcs;

import com.badlogic.gdx.Gdx;
import com.gdx.dogs_and_dungeons.entities.Entity;
import com.gdx.dogs_and_dungeons.entities.enemies.BalancedEnemy;

import java.util.ArrayList;

public class NPC extends Entity {

    public enum State {
        WALKING, ATTACKING, IDLE
    }

    public enum Direction {
        UP, LEFT, DOWN, RIGHT
    }

    // Default states and properties
    protected Direction currentDirection = Direction.RIGHT;
    protected State currentState = State.IDLE;
    private static final String TAG = BalancedEnemy.class.getSimpleName();
    private static final String specificPath = "skeleton.png";
    private float timer = 0;
    private int ranNum;
    private ArrayList<Entity.Direction> movementArrayList = new ArrayList<>();


    // All methods inherited from class Entity

    // Constructor
    public NPC(int width, int height, float drawWidth, float drawHeight, Direction currentDirection) {
        super(width, height, drawWidth, drawHeight);
        this.currentDirection = currentDirection;
    }

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
        movementArrayList.add(Entity.Direction.LEFT);
        movementArrayList.add(Entity.Direction.UP);
        movementArrayList.add(Entity.Direction.DOWN);
        movementArrayList.add(Entity.Direction.RIGHT);

        ranNum = (int) (Math.random()*4);

        this.setDirection(movementArrayList.get(ranNum));
    }

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
