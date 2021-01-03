package com.gdx.dogs_and_dungeons.entities.npcs;

public class Villager extends NPC{

    private static final String specificPath = "male/old_man.png";

    public Villager(int width, int height, float drawWidth, float drawHeight, String specificPath) {

        super(width, height, drawWidth, drawHeight, specificPath);
    }

    public Villager() {

        super(specificPath);
    }

    @Override
    public void initNPC() {

        setState(State.IDLE);
        setDirection(Direction.DOWN);
        setVelocity(1f,1f);
    }

    @Override
    public void behave(float delta) {

    }

}
