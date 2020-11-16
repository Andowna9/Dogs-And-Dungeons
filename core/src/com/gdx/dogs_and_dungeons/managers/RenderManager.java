package com.gdx.dogs_and_dungeons.managers;

import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.gdx.dogs_and_dungeons.MapManager;
import com.gdx.dogs_and_dungeons.entities.enemies.Enemy;

public class RenderManager {

    OrthogonalTiledMapRenderer mapRenderer;

    private SpriteManager spriteManager;

    // Posiciones de las capas del mapa

    private final int [] backgroundLayers = {0,1,2};

    private final int [] foregroundLayers = {3};


    public RenderManager(SpriteManager spriteManager) {

        this.spriteManager = spriteManager;

        mapRenderer = new OrthogonalTiledMapRenderer(spriteManager.mapManager.getMap(), MapManager.UNIT_SCALE);

    }

    private void renderPlayer() {

        spriteManager.player.render(mapRenderer);

    }

    private void renderEnemies() {

        for(Enemy e: spriteManager.enemies) {

            e.render(mapRenderer);
        }
    }

    public void render() {

        // Renderizado de capas inferiores

        mapRenderer.render(backgroundLayers);

        mapRenderer.getBatch().begin();

        // Renderizado de entidades

        renderPlayer();

        renderEnemies();

        mapRenderer.getBatch().end();

        // Renderizado de capas superiores

        mapRenderer.render(foregroundLayers);

    }
}
