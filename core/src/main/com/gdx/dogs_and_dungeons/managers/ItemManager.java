package com.gdx.dogs_and_dungeons.managers;

// Clase para registrar nuevos items a partir de la capa de objetos del mapa

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.utils.Array;
import com.gdx.dogs_and_dungeons.objects.Item;
import com.gdx.dogs_and_dungeons.entities.player.Player;
import com.gdx.dogs_and_dungeons.profiles.ProfileManager;
import com.gdx.dogs_and_dungeons.profiles.ProfileObserver;

import java.util.Iterator;

public class ItemManager implements ProfileObserver {

    private static final String TAG = ItemManager.class.getSimpleName();

    // Lista de objetos del mapa completa

    private Array<Item> mapItems;

    // Lista de objetos actualizada

    Array<Item> items;

    private MapManager mapManager;

    public ItemManager(MapManager mapManager) {

        ProfileManager.getInstance().addObserver(this);

        this.mapManager = mapManager;

        items = new Array<>();

        mapItems = new Array<>();

        loadObjectsFromMap();

    }

    public void render(OrthogonalTiledMapRenderer renderer) {

        for (Item item: items) {

           renderer.getBatch().draw(item.getTexture(),item.getX(), item.getY(),1,1);
        }
    }

    // Carga los objetos directamente del mapa

    private void loadObjectsFromMap() {

        MapLayer objectsLayer = mapManager.getObjectsLayer();

        for(MapObject object: objectsLayer.getObjects()) {

            Item item = Item.loadItemFromMap(object);

            // Si el objeto es de un tipo definido, se añade a la lista

            if (item.getType() != Item.Type.UNDEFINED) {

                mapItems.add(item);
            }
        }
    }


    public void itemsTriggered(Player player) {

        for(Iterator<Item> it = items.iterator();it.hasNext();) {

            Item item = it.next();

            if (item.isTriggered(player.getCollisionBox())) {

                Gdx.app.debug(TAG,"El jugador ha cogido el objeto: " + item.getType());

                // Se coge el objeto

                item.collect();

                // Eliminamos el objeto

                it.remove();

                // Añadimos el objeto al inventario del jugador
            }
        }
    }

    // Guardado y lectura de objetos en Json

    @Override
    public void onNotify(ProfileManager subject, ProfileEvent event) {

        if (event == ProfileEvent.SAVING_PROFILE) {

            subject.setProperty("Items", items);
        }

        else if (event == ProfileEvent.LOADING_PROFILE) {

            items = subject.getProperty("Items",Array.class, new Array());

            // Si la lista sigue vacía, se añaden todos los objtos del mapa por ser un perfil nuevo

            if (items.isEmpty()) {

                items.addAll(mapItems);
            }
        }
    }
}
