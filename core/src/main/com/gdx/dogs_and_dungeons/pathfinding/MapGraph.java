package com.gdx.dogs_and_dungeons.pathfinding;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;

public class MapGraph {

    private TileGraph tileGraph;

    public MapGraph(TiledMap map) {

        convertToGraph(map);

    }

    private void convertToGraph(TiledMap map) {

        tileGraph = new TileGraph();

        // Obtenemos un tileset que indique aquellas zonas que pueden ser transitadas

        TiledMapTileLayer layer = (TiledMapTileLayer) map.getLayers().get("ground");

        System.out.println(layer.getTileWidth());


    }


}
