package com.gdx.dogs_and_dungeons.managers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.TextureMapObject;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.gdx.dogs_and_dungeons.MapManager;
import com.gdx.dogs_and_dungeons.entities.Entity;
import com.gdx.dogs_and_dungeons.entities.player.PlayerHUD;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class RenderManager {


    SpriteBatch particleBatch;

    OrthogonalTiledMapRenderer mapRenderer;

    private SpriteManager spriteManager;

    // Posiciones de las capas del mapa

    private final int [] backgroundLayers = {0,1,2,3,4};

    private final int [] foregroundLayers = {5,6};

    // HUD del jugador

    PlayerHUD playerHUD;

    // Orden de renderizado de entidades dependiendo del punto medio del sprite

    private List<Entity> entities;

    // Comparador de índices z (qué entidad se renderiza antes)

    private RenderComparator zOrdering;

    // Ordenación del índice z (índice en la lista) a partir de coordenada y

    private class RenderComparator implements Comparator<Entity> {

        @Override
        public int compare(Entity e1, Entity e2) {

            // Sumamos 16, para calcular la y desde la mitad de la entidad (sabemos que son de 32 x 32 píxeles)
            // Multiplicamos por 1000 para que la resta de ints posterior sea más precisa y tenga en cuenta los primeros decimales
            float y1 = (e1.getCurrentPosition().y + 16) * 1000;
            float y2 = (e2.getCurrentPosition().y + 16) * 1000;

            // Comparamos las coordenadas restándolas

            return (int) y2 - (int) y1;
        }
    }

    // Textura que se dibuja para representar las interacciones

    private Texture interaction = new Texture("HUD/interact.png");

    public RenderManager(SpriteManager spriteManager) {

        this.spriteManager = spriteManager;

        particleBatch = new SpriteBatch();

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

        OrthographicCamera camera = new OrthographicCamera();

        camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        playerHUD = new PlayerHUD(camera, spriteManager.player);

        entities = new ArrayList<>();

        zOrdering = new RenderComparator();

    }

    // Inicialización de lista de entidades, una vez se han inicializado en spritemanager

    public void init() {

        entities.clear();

        entities.add(SpriteManager.player);

        entities.addAll(spriteManager.npcs);

        entities.addAll(spriteManager.enemies);

    }

    // Renderizado de interacciones si hay un NPC

    private void renderInteractions() {

        if (spriteManager.interactingNPC != null)

        mapRenderer.getBatch().draw(interaction,spriteManager.interactingNPC.getCurrentPosition().x + 0.25f, spriteManager.interactingNPC.getCurrentPosition().y + 1 ,0.5f,0.5f);
    }

    private void renderObjects() {

        spriteManager.itemManager.render(mapRenderer);
    }

    public void render(float delta) {

        // Renderizado de capas inferiores

        mapRenderer.render(backgroundLayers);

        mapRenderer.getBatch().begin();

        renderObjects();

        // Renderizado de entidades

        // En cada vuelta del bucle reordenamos la lista de entidades para que se rendericen en el orden correcto

        entities.sort(zOrdering);

        // Renderizamos las entidades de la lista una vez ordenada

        for (Entity e: entities) {

            e.render(mapRenderer);
        }

        // Renderizado de efectos de partículas

        spriteManager.effectsManager.renderEffects(mapRenderer.getBatch(),delta);

        renderInteractions();

        mapRenderer.getBatch().end();

        // Renderizado de capas superiores

        mapRenderer.render(foregroundLayers);

        // Renderizado de HUD

        playerHUD.render(delta);


    }
}
