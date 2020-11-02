package com.gdx.dogs_and_dungeons;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;

import java.util.ArrayList;
import java.util.Arrays;

// Mapa ortogonal personalizado para determinar el order de dibujado

// Extendemos de la clase base para añadirle esta funcionalidad

public class CustomOrthogonalTiledMapRenderer extends OrthogonalTiledMapRenderer {

    private static final String TAG = CustomOrthogonalTiledMapRenderer.class.getSimpleName();

    private ArrayList<Entity> entities;

    private SpriteBatch spriteBatch;

    private int numLayers;

    public CustomOrthogonalTiledMapRenderer(TiledMap map, float unitScale) {

        super(map, unitScale);

        entities = new ArrayList<>();

        numLayers = map.getLayers().getCount();
    }

    public void addEntity(Entity e) {

        entities.add(e);
    }

    @Override
    public void render() {

        beginRender();

        int layerCount = 1;

        for (MapLayer layer : map.getLayers()) {

            // La capa en la que se renderizan los las entidades es la penúltima (entre midground y foreground)

            if (layerCount == numLayers - 1) {

                for (Entity e: entities) {

                    this.getBatch().draw(e.getCurrentTexture(),e.getCurrentPosition().x,e.getCurrentPosition().y,1.5f,1.5f);
                }

            }

            renderMapLayer(layer);

            layerCount++;

            }

        endRender();

        }


}
