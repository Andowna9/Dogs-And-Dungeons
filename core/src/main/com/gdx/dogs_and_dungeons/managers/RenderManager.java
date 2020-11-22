package com.gdx.dogs_and_dungeons.managers;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.TextureMapObject;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.gdx.dogs_and_dungeons.MapManager;
import com.gdx.dogs_and_dungeons.entities.enemies.Enemy;

public class RenderManager {

    OrthogonalTiledMapRenderer mapRenderer;

    private SpriteManager spriteManager;

    private ItemManager itemManager;

    // Posiciones de las capas del mapa

    private final int [] backgroundLayers = {0,1,2};

    private final int [] foregroundLayers = {3};


    public RenderManager(SpriteManager spriteManager) {

        this.spriteManager = spriteManager;

        // Sobreescribimos el método renderOPbject() de la instancia para poder dibujar los objetos del mapa

        mapRenderer = new OrthogonalTiledMapRenderer(spriteManager.mapManager.getMap(), MapManager.UNIT_SCALE) {
            @Override
            public void renderObject(MapObject object) {

                if (object instanceof TextureMapObject) {

                    TextureMapObject textureObj = (TextureMapObject) object;

                    batch.draw(textureObj.getTextureRegion(),
                            textureObj.getX() * MapManager.UNIT_SCALE,
                            textureObj.getY()*MapManager.UNIT_SCALE,1,1);
                }

            }
        };

    }

    private void renderObjects() {

        spriteManager.itemManager.render(mapRenderer);
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

        renderObjects();

        // Renderizado de entidades

        renderPlayer();

        renderEnemies();


        mapRenderer.getBatch().end();

        // Renderizado de capas superiores

        mapRenderer.render(foregroundLayers);

    }
}
