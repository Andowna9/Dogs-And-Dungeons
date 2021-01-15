package com.gdx.dogs_and_dungeons.entities.enemies;

import com.gdx.dogs_and_dungeons.managers.SpriteManager;
import com.gdx.dogs_and_dungeons.pathfinding.PfaAgent;
import com.gdx.dogs_and_dungeons.pathfinding.Tile;
import com.gdx.dogs_and_dungeons.pathfinding.TileGraph;

public class HardEnemy extends Enemy {

    private static final String specificPath = "ghost.png";
    private static final String TAG = HardEnemy.class.getSimpleName();
    private boolean isFollowingPlayer = false;
    private TileGraph tileGraph;
    private PfaAgent pfaAgent;

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

        tileGraph = new TileGraph(SpriteManager.mapManager.getAStarLayer(), "GHOSTZONE");

        pfaAgent = new PfaAgent(tileGraph, this);
    }

    public void setPathToPlayer() {

        // Cuidado porque el método getTile() del grafo puede devolver valor nulo

        Tile playerTile = tileGraph.getTileFrom(SpriteManager.player);

        Tile enemyTile = tileGraph.getTileFrom(this);

        // Establecemos un nuevo camino para el agente desde su posición hasta la del jugador

        pfaAgent.setPath(enemyTile, playerTile);

        isFollowingPlayer = true;

    }

    @Override
    public void behave(float delta) {

        if (!isFollowingPlayer) {

            if (tileGraph.isPlayerInsideZone()) {

                setPathToPlayer();
            }
        }

        pfaAgent.move();
    }





}
