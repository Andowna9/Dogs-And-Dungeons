package com.gdx.dogs_and_dungeons.pathfinding;

import com.badlogic.gdx.ai.pfa.DefaultConnection;

// En la conexión por defecto, el coste entre dos nodos cualesquiera simpre es uno

public class TileConnection extends DefaultConnection<Tile> {

    public TileConnection(Tile fromTile, Tile toTile) {

        super(fromTile, toTile);
    }
}
