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
import com.badlogic.gdx.math.MathUtils;
import com.gdx.dogs_and_dungeons.PlayerController;
import com.gdx.dogs_and_dungeons.CustomOrthogonalTiledMapRenderer;
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

    private CustomOrthogonalTiledMapRenderer mapRenderer;

    private ShapeRenderer shapeRenderer;

    // Jugador

    private Entity player;

    private PlayerController playerController;

    private Rectangle playerCollisionBox;

    // Referencia a la clase que extiende de game

    private DogsAndDungeons game_ref;


    public MainGameScreen(DogsAndDungeons game) {


        game_ref = game;

        player = new Entity(48,48);

        player.setPosition(20,20);

        playerCollisionBox = player.getCollisionBox();

        playerController = new PlayerController(player);

        shapeRenderer = new ShapeRenderer();

        float h = Gdx.graphics.getHeight();

        float w = Gdx.graphics.getWidth();

        batch = new SpriteBatch();

        font = new BitmapFont();

        camera = new OrthographicCamera();

        camera.setToOrtho(false,w/32,h/32);

        camera.position.set(25,23,0); // Centramos la posición de la ventana de acuerdo con el tamaño del tilemap (1600 x 1600 px)

        camera.update(); // Cada vez que la cámara es manipulada, hay que actualizarla manualmente

        mapManager = new MapManager();

        tiledMap = mapManager.getMap();

        mapRenderer = new CustomOrthogonalTiledMapRenderer(tiledMap,MapManager.UNIT_SCALE);

        mapRenderer.addEntity(player);

        mapRenderer.setView(camera);

    }

    @Override
    public void show() {

        Gdx.input.setInputProcessor(playerController);

    }

    @Override
    public void render(float delta) {

        Gdx.gl.glClearColor(0, 0, 0, 1);

        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);


        // Actualizamos la posición de la cámara, evitando que se salga de los límites establecidos

        camera.position.x = MathUtils.clamp(player.getCurrentPosition().x,
                camera.viewportWidth/2,
                mapManager.getCurrentMapWidth() - camera.viewportWidth/2);

        camera.position.y = MathUtils.clamp(player.getCurrentPosition().y,
                camera.viewportHeight/2,
                mapManager.getCurrentMapHeight() - camera.viewportHeight/2);

        camera.update();

        player.update(delta);

        if (!isCollidingWithMap(playerCollisionBox)) {

            player.updatePosition();
        }

        playerController.processInput(delta);

        mapRenderer.setView(camera);

        mapRenderer.render();

        //mapRenderer.getBatch().begin();

        //mapRenderer.getBatch().draw(player.getCurrentTexture(),player.getCurrentPosition().x,player.getCurrentPosition().y,1.5f,1.5f);

        //mapRenderer.getBatch().end();

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
