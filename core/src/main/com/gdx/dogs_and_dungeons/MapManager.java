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

    private MapLayer portlaLayer;

    // Nombre de cada capa

    private static final String COLLISION_LAYER = "COLLISION_LAYER";

    private static final String SPAWN_LAYER = "SPAWN_LAYER";

    private static final String PORTAL_LAYER = "PORTAL_LAYER";

    // Factor de conversión para gestionar tiles sin depender de su tamaño en píxeles (32 px X 32 px)

    private static final String TEST_MAP = "DEFAULT_MAP";

    // Dimensiones del mapa

    private int currentMapWidth;

    private int currentMapHeight;

    public static final float UNIT_SCALE = 1/32f;

    public MapManager() {

        playerStartPosition = new Vector2();

        maps = new Hashtable<>();

        maps.put(TEST_MAP,"tiledmaps/village/village_map.tmx");

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

        portlaLayer = currentMap.getLayers().get(PORTAL_LAYER);

        if (collisionLayer == null) {

            Gdx.app.log(TAG,"No se ha encontrado la capa: " + PORTAL_LAYER);

        }


    }

    public TiledMap getMap() {

        if (currentMap == null) {

            loadMap(TEST_MAP);

        }

        return currentMap;
    }

    public MapLayer getCollisionLayer() {

        return  collisionLayer;
    }

    public int getCurrentMapWidth() {

        return currentMapWidth;
    }

    public int getCurrentMapHeight() {

        return currentMapHeight;

    }
}
