package com.gdx.dogs_and_dungeons.entities.npcs;

import com.gdx.dogs_and_dungeons.managers.SpriteManager;
import com.gdx.dogs_and_dungeons.pathfinding.PfaAgent;
import com.gdx.dogs_and_dungeons.pathfinding.TileGraph;

public class Cat extends NPC {

    private TileGraph tileGraph;

    private PfaAgent pfaAgent;

    public Cat(String subtype) {

        super("animals/" + subtype);
    }

    @Override
    public void initEntity() {

        setVelocity(1,1);
        setState(State.IDLE);

        String location = SpriteManager.mapManager.getLocationFor(this);

        tileGraph = new TileGraph(SpriteManager.mapManager.getAStarLayer(),location);

        pfaAgent = new PfaAgent(tileGraph, this);

    }

    @Override
    public void behave(float delta) {

        if (pfaAgent.isDestinationReached()) {

            pfaAgent.setPath(tileGraph.getTileFrom(this), tileGraph.getRandomTile());


        }

        pfaAgent.move();
    }
}
