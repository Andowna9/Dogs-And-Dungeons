package com.gdx.dogs_and_dungeons.pathfinding;

import com.badlogic.gdx.ai.pfa.Connection;
import com.badlogic.gdx.ai.pfa.DefaultGraphPath;
import com.badlogic.gdx.ai.pfa.GraphPath;
import com.badlogic.gdx.ai.pfa.indexed.IndexedAStarPathFinder;
import com.badlogic.gdx.ai.pfa.indexed.IndexedGraph;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ObjectMap;

// Esta clase se comporta como un grafo indexado que es capaz de encontrar la ruta óptima entre 2 de sus nodos (inicio, fin)

public class TileGraph implements IndexedGraph<Tile> {

    // Tiles (nodos) del grafo

    Array<Tile> tiles = new Array<>();

    // Conexiones entre tiles (aristas) del grafo

    Array<TileConnection> tileConnections = new Array<>();

    // Función para que A* realice estimaciones

    TileHeuristic heuristic = new TileHeuristic();

    // Mapa que contiene todas las conexiones para un tile dado

    ObjectMap<Tile,Array<Connection<Tile>>> connectionsMap = new ObjectMap<>();


    private int lastTileIndex = 0;

    // Método para añadir nuevos tiles al grafo y asignarles un índice que va aumentando para cada caso

    public void addTile(Tile tile) {

        tile.index = lastTileIndex;

        lastTileIndex++;

        tiles.add(tile);
    }

    // Método para conectar un tile con otro y añadir

    public void connectTiles(Tile fromTile, Tile toTile) {

        TileConnection tileConnection = new TileConnection(fromTile,toTile);

        if (!connectionsMap.containsKey(fromTile)) {

            connectionsMap.put(fromTile,new Array<Connection<Tile>>());
        }

        connectionsMap.get(fromTile).add(tileConnection);

        tileConnections.add(tileConnection);

    }

    // Método que devuelve el camino a través del grafo desde el tile de inicio hasta el tile objetivo

    public GraphPath<Connection<Tile>> findPath(Tile startTile, Tile goalTile) {

        GraphPath<Connection<Tile>> tilePath = new DefaultGraphPath<>();

        new IndexedAStarPathFinder<>(this).searchConnectionPath(startTile,goalTile,heuristic,tilePath);

        return tilePath;
    }

    // Método que devuelve el índice de un tile

    @Override
    public int getIndex(Tile node) {
        return node.index;
    }

    // Método que devuelve el número de nodos

    @Override
    public int getNodeCount() {
        return lastTileIndex;
    }

    // Método que devuelve una lista con las conexiones de un nodo dado

    @Override
    public Array<Connection<Tile>> getConnections(Tile fromNode) {

        if (connectionsMap.containsKey(fromNode)) {

            return connectionsMap.get(fromNode);
        }

        return new Array<>(0);

    }
}
