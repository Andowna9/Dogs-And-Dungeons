package com.gdx.dogs_and_dungeons.managers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.gdx.dogs_and_dungeons.MapManager;
import com.gdx.dogs_and_dungeons.entities.Entity;
import com.gdx.dogs_and_dungeons.entities.EntityFactory;
import com.gdx.dogs_and_dungeons.entities.enemies.Enemy;
import com.gdx.dogs_and_dungeons.entities.npcs.NPC;
import com.gdx.dogs_and_dungeons.entities.player.Player;
import com.gdx.dogs_and_dungeons.entities.player.PlayerController;
import com.gdx.dogs_and_dungeons.pathfinding.TileGraph;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

// Clase para gestionar las interacciones entre entidades

public class SpriteManager {

    private static final String TAG = SpriteManager.class.getSimpleName();

    Player player;

    private PlayerController playerController;

    // Lista de enemigos

    List<Enemy> enemies;

    // Lista de NPCs

    List<NPC> npcs;

    // Gestor de mapa

    public static MapManager mapManager;

    // Gestor de objetos

    ItemManager itemManager;

    // Gestor de efectos de partículas

    ParticleEffectsManager effectsManager;

    // Gestor de audio

    public static AudioManager audioManager;

    public SpriteManager() {

        mapManager = new MapManager();

        // Guardamos la referencia estática a la instancia del map manager para que todas las entidades tengam acceso

        audioManager = new AudioManager();


        itemManager = new ItemManager(mapManager);

        effectsManager = new ParticleEffectsManager();

        // Inicialización de lista de enemigos

        enemies = new ArrayList<>();

        // Inicialización de lista de npcs

        npcs = new ArrayList<>();

        // Creación del jugador

        player = EntityFactory.getPlayer();

        player.setPosition(22.5f,0);

        playerController = new PlayerController(player);

        // Prueba de TileGraph

        new TileGraph((TiledMapTileLayer) mapManager.getMap().getLayers().get("AStar"),"GRAVEYARD");
    }

    // Inicialización en caso de reanudar la partida (más adelante con puntos de spawn)

    public void init() {

        player.loadPlayerSprites();

        player.setHealth(7);

        player.setInitialPosition(mapManager.getPlayerSpawnPosition(player.getCurrentPosition()));

        player.setDefaultTexture(Entity.State.WALKING, Entity.Direction.UP);

        player.setState(Entity.State.IDLE);

        player.setDirection(Entity.Direction.UP);

        enemies.clear();

        mapManager.spawnEnemies(enemies);

        mapManager.spawnNPCs(npcs);

    }

    // Actulización de enemigos

    void updateEnemies(float delta) {

        for (Iterator<Enemy> it = enemies.iterator();it.hasNext();) {

            Enemy e = it.next();

            // Actualización de enemigos

            e.update(delta);

            e.behave(delta);

            // Si el enemigo ha perdido toda su salud, se elimina de la lista

            if (e.getHealth() <= 0) {

                effectsManager.generateEffect(e.getCurrentPosition().x + 0.5f, e.getCurrentPosition().y + 0.5f, ParticleEffectsManager.EffectType.ENEMY_DEATH);

                Gdx.app.debug(TAG, e.getClass().getSimpleName() + " eliminado por jugador!");

                it.remove();
            }
        }
    }

    // Actualización jugador

    private void updatePlayer(float delta) {

        // Procesado de input (teclas)

        playerController.processInput(delta);

        // Actulización de animaciones y cajas de colisión

        player.update(delta);

        if (!mapManager.isCollidingWithMap(player)) {

            player.updatePosition();
        }
    }

    private void updateNPCs(float delta) {

        for (NPC npc: npcs) {

            npc.update(delta);

            npc.behave(delta);
        }
    }

    // Actualización general

    public void update(float delta) {

       updateEnemies(delta);

        updateNPCs(delta);

       updatePlayer(delta);

       // Interacción entre jugador y objetos

        itemManager.itemsTriggered(player);

        // Interacción entre enemigos y jugador

        for (Enemy enemy: enemies) {

            player.checkAttack(enemy);

            if (player.isCollidingWithEntity(enemy) && !player.isBlinking()) {

                player.receiveDamage();

                Gdx.app.debug(TAG,"Vida restante de jugador: " + player.getHealth());

                if (!player.isDead()) {

                    player.setBlinking(2f);

                }
            }
        }
    }

    public PlayerController getPlayerController() {

        return playerController ;
    }


    public Player getPlayer() {

        return player;
    }


}
