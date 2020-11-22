package com.gdx.dogs_and_dungeons;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Vector2;
import java.util.Hashtable;

public class MapManager {

    private static final String TAG = MapManager.class.getSimpleName();

    private Vector2 playerStartPosition;

    // Almacenamos las rutas utilizando como clave el nombre de cada mapa

    private Hashtable<String,String> maps;

    // Mapa actual

    private TiledMap currentMap;

    // Capas del mapa

    private MapLayer collisionLayer;

    private MapLayer spawnLayer;

    private MapLayer portalLayer;

    private MapLayer objectsLayer;

    // Nombre de cada capa

    private static final String COLLISION_LAYER = "COLLISION_LAYER";

    private static final String SPAWN_LAYER = "SPAWN_LAYER";

    private static final String PORTAL_LAYER = "PORTAL_LAYER";

    private static final String OBJECTS_LAYER = "OBJECTS_LAYER";

    // Factor de conversión para gestionar tiles sin depender de su tamaño en píxeles (32 px X 32 px)

    private static final String VILLAGE_MAP = "DEFAULT_MAP";

    // Dimensiones del mapa

    private int currentMapWidth;

    private int currentMapHeight;

    public static final float UNIT_SCALE = 1/32f;

    public MapManager() {

        playerStartPosition = new Vector2();

        maps = new Hashtable<>();

        maps.put(VILLAGE_MAP,"tiledmaps/village/village_map.tmx");

        // Por defcto carga el mapa del pueblo

        loadMap(VILLAGE_MAP);

    }

    public void loadMap(String mapName) {

        String mapPath = maps.get(mapName);

        Utility.loadMapAsset(mapPath);

        // Si ya había un mapa antes, elinamos el recurso para disponer de más memoria

        if (currentMap != null) {

            currentMap.dispose();
        }

        currentMap = Utility.getMapAsset(mapPath);

        currentMapWidth = currentMap.getProperties().get("width",Integer.class);

        currentMapHeight = currentMap.getProperties().get("height",Integer.class);

        Gdx.app.debug(TAG,"Número De Capas: " + currentMap.getLayers().size());

        collisionLayer = currentMap.getLayers().get(COLLISION_LAYER);

        if (collisionLayer == null) {

            Gdx.app.log(TAG,"No se ha encontrado la capa: " + COLLISION_LAYER);

        }

        else {

            Gdx.app.debug(TAG,String.valueOf(collisionLayer.getObjects().getByType(RectangleMapObject.class).size));
        }

        spawnLayer = currentMap.getLayers().get(SPAWN_LAYER);

        if (spawnLayer == null) {

            Gdx.app.log(TAG,"No se ha encontrado la capa: " + SPAWN_LAYER);

        }

        portalLayer = currentMap.getLayers().get(PORTAL_LAYER);

        if (portalLayer == null) {

            Gdx.app.log(TAG,"No se ha encontrado la capa: " + PORTAL_LAYER);

        }

        objectsLayer = currentMap.getLayers().get(OBJECTS_LAYER);

        if (objectsLayer == null) {

            Gdx.app.log(TAG,"No se ha encontrado la capa: " + OBJECTS_LAYER);

        }


    }

    public TiledMap getMap() {

        return currentMap;
    }

    public MapLayer getCollisionLayer() {

        return  collisionLayer;
    }

    public MapLayer getObjectsLayer() {

        return objectsLayer;
    }

    public int getCurrentMapWidth() {

        return currentMapWidth;
    }

    public int getCurrentMapHeight() {

        return currentMapHeight;

    }
}
