package com.gdx.dogs_and_dungeons;

import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;

import java.util.ArrayList;

// Mapa ortogonal personalizado para determinar el order de dibujado

// Extendemos de la clase base para añadirle esta funcionalidad

public class CustomOrthogonalTiledMapRenderer extends OrthogonalTiledMapRenderer {

    private ArrayList<Entity> entities;

    public CustomOrthogonalTiledMapRenderer(TiledMap map, float unitScale) {

        super(map, unitScale);

        entities = new ArrayList<>();
    }

    public void addEntity(Entity e) {

        entities.add(e);
    }

    @Override
    public void render() {

        beginRender();

        for (MapLayer layer : map.getLayers()) {

            renderMapLayer(layer);
        }

        endRender();
    }
}
