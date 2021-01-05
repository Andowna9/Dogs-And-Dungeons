package com.gdx.dogs_and_dungeons.managers;

// Clase para registrar nuevos items a partir de la capa de objetos del mapa

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.gdx.dogs_and_dungeons.Item;
import com.gdx.dogs_and_dungeons.MapManager;
import com.gdx.dogs_and_dungeons.entities.player.Player;
import com.gdx.dogs_and_dungeons.entities.player.hud.StatusUI;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ItemManager {

    private static final String TAG = ItemManager.class.getSimpleName();

    List<Item> items;

    private MapManager mapManager;

    public ItemManager(MapManager mapManager) {

        this.mapManager = mapManager;

        items = new ArrayList<>();

        loadObjects();

    }

    public void render(OrthogonalTiledMapRenderer renderer) {


        for (Item item: items) {

           renderer.renderObject(item.getMapObject());
        }
    }

    private void loadObjects() {

        MapLayer objectsLayer = mapManager.getObjectsLayer();

        for(MapObject object: objectsLayer.getObjects()) {

            Item item = new Item(object);

            items.add(item);
        }
    }

    public void itemsTriggered(Player player) {

        for(Iterator<Item> it = items.iterator();it.hasNext();) {

            Item item = it.next();

            if (item.isTriggered(player.getCollisionBox())) {

                Gdx.app.debug(TAG,"El jugador ha cogido el objeto: " + item.getName());

                StatusUI.incrementLogs();
                // Eliminamos el objeto

                it.remove();

                // Añadimos el objeto al inventario del jugador
            }
        }
    }
}
