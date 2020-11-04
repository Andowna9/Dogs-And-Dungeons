package com.gdx.dogs_and_dungeons.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.gdx.dogs_and_dungeons.PlayerController;
import com.gdx.dogs_and_dungeons.Entity;
import com.gdx.dogs_and_dungeons.MapManager;
import com.gdx.dogs_and_dungeons.DogsAndDungeons;
import com.badlogic.gdx.math.Rectangle;

// Pantalla de juego

public class MainGameScreen implements Screen {

    private static final String TAG = MainGameScreen.class.getSimpleName();

    private SpriteBatch batch;

    // Fuente para mostrar fps

    private BitmapFont font;

    private OrthographicCamera camera;

    private MapManager mapManager;

    private TiledMap tiledMap;

    private OrthogonalTiledMapRenderer mapRenderer;

    private ShapeRenderer shapeRenderer;

    // Jugador

    private Entity player;

    private PlayerController playerController;

    private Rectangle playerCollisionBox;

    // Referencia a la clase que extiende de game

    private DogsAndDungeons game_ref;

    // Posiciones de las capas del mapa

    private final int [] backgroundLayers = {1,2};

    private final int [] foregroundLayers = {4};

    private static class VIEWPORT {

        static float viewportWidth;
        static float viewportHeight;
        static float virtualWidth;
        static float virtualHeight;
        static float physicalWidth;
        static float physicalHeight;
        static float aspectRatio;
    }

    private void setViewport(int width, int height) {

        // La anchura y altura representan el número de tiles que se dibujan

        VIEWPORT.virtualWidth = width;

        VIEWPORT.virtualHeight = height;

        VIEWPORT.viewportWidth = VIEWPORT.viewportWidth;

        VIEWPORT.viewportHeight = VIEWPORT.virtualHeight;

        VIEWPORT.physicalWidth = Gdx.graphics.getWidth();

        VIEWPORT.physicalHeight = Gdx.graphics.getHeight();

        VIEWPORT.aspectRatio = (VIEWPORT.virtualWidth /
                VIEWPORT.virtualHeight);

        if( VIEWPORT.physicalWidth / VIEWPORT.physicalHeight >=
                VIEWPORT.aspectRatio){

            VIEWPORT.viewportWidth = VIEWPORT.viewportHeight *
                    (VIEWPORT.physicalWidth/VIEWPORT.physicalHeight);
            VIEWPORT.viewportHeight = VIEWPORT.virtualHeight;
        }else{

            VIEWPORT.viewportWidth = VIEWPORT.virtualWidth;
            VIEWPORT.viewportHeight = VIEWPORT.viewportWidth *
                    (VIEWPORT.physicalHeight/VIEWPORT.physicalWidth);
        }

    }


    public MainGameScreen(DogsAndDungeons game) {


        game_ref = game;

        player = new Entity(64,64);

        player.setPosition(20,20);

        playerCollisionBox = player.getCollisionBox();

        playerController = new PlayerController(player);

        shapeRenderer = new ShapeRenderer();

        batch = new SpriteBatch();

        font = new BitmapFont();

        camera = new OrthographicCamera();

        setViewport(15,12);

        // camera.setToOrtho(false,VIEWPORT.viewportWidth,VIEWPORT.viewportHeight);

        camera.setToOrtho(false,VIEWPORT.physicalWidth/32,VIEWPORT.physicalHeight/32);

        camera.position.set(25,23,0); // Centramos la posición de la ventana de acuerdo con el tamaño del tilemap (1600 x 1600 px)

        camera.update(); // Cada vez que la cámara es manipulada, hay que actualizarla manualmente

        mapManager = new MapManager();

        tiledMap = mapManager.getMap();

        mapRenderer = new OrthogonalTiledMapRenderer(tiledMap,MapManager.UNIT_SCALE);

        mapRenderer.setView(camera);

    }

    @Override
    public void show() {

        Gdx.input.setInputProcessor(playerController);

    }

    @Override
    public void render(float delta) {

        // *LIMPIANDO PANTALLA*

        Gdx.gl.glClearColor(0, 0, 0, 1);

        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // *ACTUALIZACIONES*


        // Actualizamos la posición de la cámara, evitando que se salga de los límites establecidos

        camera.position.x = MathUtils.clamp(player.getCurrentPosition().x,
                camera.viewportWidth/2,
                mapManager.getCurrentMapWidth() - camera.viewportWidth/2);

        camera.position.y = MathUtils.clamp(player.getCurrentPosition().y,
                camera.viewportHeight/2,
                mapManager.getCurrentMapHeight() - camera.viewportHeight/2);

        camera.update();

        player.update(delta);

        // Actualizamos la posición del jugador solo si no detectamos colisión en su próxima posición

        if (!isCollidingWithMap(playerCollisionBox)) {

            player.updatePosition();
        }

        // *PROCESADO DE INPUT DEL JUGADOR*

        playerController.processInput(delta);


        // *RENDERIZADO DE TEXTURAS (GRÁFICOS)*

        // Antes de renderizar ajustamos la vista del mapa, ya que hemos actualizado la cámara

        mapRenderer.setView(camera);

        // Renderizado de capas inferiores

        mapRenderer.render(backgroundLayers);

        // Renderizado de jugador a a partir del batch utilizado para el mapa

        mapRenderer.getBatch().begin();

        mapRenderer.getBatch().draw(player.getCurrentTexture(),player.getCurrentPosition().x,player.getCurrentPosition().y,1.5f,1.5f);

        mapRenderer.getBatch().end();

        // Renderizado de capas superiores

        mapRenderer.render(foregroundLayers);

        // Renderizado de texto para mostrar FPS en cada instante (con otro batch)

        batch.begin();

        font.draw(batch,String.valueOf(Gdx.graphics.getFramesPerSecond()),20,20);

        batch.end();


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

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
