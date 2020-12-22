package com.gdx.dogs_and_dungeons;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Vector2;
import com.gdx.dogs_and_dungeons.entities.EntityFactory;
import com.gdx.dogs_and_dungeons.entities.enemies.Enemy;

import java.util.Hashtable;
import java.util.List;

public class MapManager {

    private static final String TAG = MapManager.class.getSimpleName();

    // Almacenamos las rutas utilizando como clave el nombre de cada mapa

    private Hashtable<String,String> maps;

    // Mapa actual

    private TiledMap currentMap;

    // Capas del mapa

    private MapLayer collisionLayer;

    private MapLayer playerSpawnLayer;

    private MapLayer enemiesSpawnLayer;

    private MapLayer portalLayer;

    private MapLayer objectsLayer;

    // Nombre de cada capa

    private static final String COLLISION_LAYER = "COLLISION_LAYER";

    private static final String PLAYER_SPAWN_LAYER = "PLAYER_SPAWN_LAYER";

    private static final String ENEMIES_SPAWN_LAYER = "ENEMIES_SPAWN_LAYER";

    private static final String PORTAL_LAYER = "PORTAL_LAYER";

    private static final String OBJECTS_LAYER = "OBJECTS_LAYER";

    // Factor de conversión para gestionar tiles sin depender de su tamaño en píxeles (32 px X 32 px)

    private static final String VILLAGE_MAP = "DEFAULT_MAP";

    // Dimensiones del mapa

    private static int currentMapWidth;

    private static int currentMapHeight;

    public static final float UNIT_SCALE = 1/32f;

    public MapManager() {

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

        playerSpawnLayer = currentMap.getLayers().get(PLAYER_SPAWN_LAYER);

        if (playerSpawnLayer == null) {

            Gdx.app.log(TAG,"No se ha encontrado la capa: " + PLAYER_SPAWN_LAYER);

        }

        enemiesSpawnLayer = currentMap.getLayers().get(ENEMIES_SPAWN_LAYER);

        if(enemiesSpawnLayer == null){

            Gdx.app.log(TAG,"No se ha encontrado la capa: " + ENEMIES_SPAWN_LAYER);

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

    public Vector2 getPlayerSpawnPosition(Vector2 lastPosition) {

        float shortestDistance = Float.MAX_VALUE;

        // Es importante pasar la posición del jugador a píxeles para compararar distancias con instancias de RectangleMapObject

        lastPosition.scl(1/UNIT_SCALE);

        Vector2 playerPosition = new Vector2();

        for (MapObject mapObject: playerSpawnLayer.getObjects()) {
            boolean isActivated = mapObject.getProperties().get("activated",Boolean.class);
            // Si el punto de spawn está activado, lo tenemos en cuenta en el cálculo del más cercano

            if (isActivated) {

                RectangleMapObject spawn = (RectangleMapObject) mapObject;

                Vector2 spawnPosition = new Vector2();

                spawn.getRectangle().getPosition(spawnPosition);

                Gdx.app.debug(TAG, "Nombre de spawn: " + spawn.getName());

                Gdx.app.debug(TAG, "Posición: " + spawnPosition);

                Gdx.app.debug(TAG, "Posición jugador: " + lastPosition);

                float distance = lastPosition.dst(spawnPosition);

                Gdx.app.debug(TAG, "Distancia: " + distance);

                if (distance < shortestDistance) {

                    shortestDistance = distance;

                    playerPosition = spawnPosition;

                }


            }
        }

        lastPosition.set(playerPosition);

        // Compensamos la posición de acuerdo con el tamaño del jugador

        lastPosition.add(-16, -16);

        return lastPosition.scl(UNIT_SCALE);

    }
    public void spawnEnemies(List<Enemy> enemies){
        //Buscamos cada punto de spawn que hay en el mapa para los enemigos para los enemigos
        for (MapObject mapObject : enemiesSpawnLayer.getObjects()){

            //Cogemos el tipo de enemigos que creará cada spawn
            String type =  mapObject.getProperties().get("type",String.class);

            //Cogemos la posicion de los spawns
            RectangleMapObject spawn = (RectangleMapObject) mapObject;
            float x = spawn.getRectangle().getX() * UNIT_SCALE;
            float y = spawn.getRectangle().getY() * UNIT_SCALE;
            Gdx.app.debug(TAG,""+ type);

            //Creamos los enemigos con el tipo que hemos sacado previamente y los añadimos a la lista de enemigos
            Enemy enemy = EntityFactory.getEnemy(type);

            enemy.setInitialPosition(x,y);
            enemies.add(enemy);


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

    public static int getCurrentMapWidth() {

        return currentMapWidth;
    }

    public static int getCurrentMapHeight() {

        return currentMapHeight;
    }


}
