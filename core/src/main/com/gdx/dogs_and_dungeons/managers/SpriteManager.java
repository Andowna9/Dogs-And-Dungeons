package com.gdx.dogs_and_dungeons.managers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.math.Rectangle;
import com.gdx.dogs_and_dungeons.MapManager;
import com.gdx.dogs_and_dungeons.entities.Entity;
import com.gdx.dogs_and_dungeons.entities.EntityFactory;
import com.gdx.dogs_and_dungeons.entities.enemies.Enemy;
import com.gdx.dogs_and_dungeons.entities.player.Player;
import com.gdx.dogs_and_dungeons.entities.player.PlayerController;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

// Clase para gestionar las interacciones entre entidades

public class SpriteManager {

    private static final String TAG = SpriteManager.class.getSimpleName();

    Player player;

    private PlayerController playerController;

    List<Enemy> enemies;

    MapManager mapManager;

    ItemManager itemManager;

    ParticleEffectsManager effectsManager;

    public SpriteManager() {

        mapManager = new MapManager();

        itemManager = new ItemManager(mapManager);

        effectsManager = new ParticleEffectsManager();

        // Inicialización de lista de enemigos

        enemies = new ArrayList<>();

        // Creación del jugador

        player = EntityFactory.getPlayer();

        player.setPosition(22.5f,0);

        playerController = new PlayerController(player);
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

        if (!isCollidingWithMap(player.getCollisionBox())) {

            player.updatePosition();
        }
    }

    // Actualización general

    public void update(float delta) {

       updateEnemies(delta);

       updatePlayer(delta);

       // Interacción entre jugador y objetos

        itemManager.itemsTriggered(player);

        // Interacción entre enemigos y jugador

        for (Enemy enemy: enemies) {

            player.attack(enemy);

            if (player.isCollidingWithEntity(enemy) && !player.isBlinking()) {

                player.receiveDamage();

                Gdx.app.debug(TAG,"Vida restante: " + player.getHealth());

                if (!player.isDead()) {

                    player.setBlinking();

                }
            }
        }
    }

    public PlayerController getPlayerController() {

        return playerController ;
    }

    private boolean isCollidingWithMap(Rectangle collisionBox) {

        MapLayer collisionLayer = mapManager.getCollisionLayer();

        Rectangle rectangle;

        for (RectangleMapObject object: collisionLayer.getObjects().getByType(RectangleMapObject.class)) {

            rectangle = object.getRectangle();

            if (collisionBox.overlaps(rectangle)) {

                return true;
            }
        }

        return false;
    }

    public Player getPlayer() {

        return player;
    }


}
