package com.gdx.dogs_and_dungeons.entities.npcs;

public class Villager extends NPC{


    public Villager(String subtype) {

        super("humans/" + subtype);
    }

    @Override
    public void initEntity() {

        setState(State.IDLE);
        setVelocity(1f,1f);
    }

    @Override
    public void behave(float delta) {

    }

}
