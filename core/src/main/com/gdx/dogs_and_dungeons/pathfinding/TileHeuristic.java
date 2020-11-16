package com.gdx.dogs_and_dungeons.pathfinding;

import com.badlogic.gdx.ai.pfa.Heuristic;


public class TileHeuristic implements Heuristic<Tile> {

    // Función de estimación para aproximar la distancia de un nodo dado, al nodo objetivo

    // Una de las más utilizadas es la distancia de Manhattan (suma del número de tiles en cada eje para
    // llegar al nodo final)
    @Override
    public float estimate(Tile node, Tile endNode) {

        return Math.abs(endNode.x - node.x) + Math.abs(endNode.y - node.y);
    }
}
