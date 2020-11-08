package com.gdx.dogs_and_dungeons.entities;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapRenderer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.gdx.dogs_and_dungeons.MapManager;
import com.gdx.dogs_and_dungeons.Utility;

public class Entity {

    private static final String TAG = Entity.class.getSimpleName();

    public enum State {

        IDLE, WALKING
    }

    public enum Direction {

        UP,DOWN,LEFT,RIGHT
    }

    // Propiedades relacionadas con movimiento

    protected Vector2 velocity;

    protected Vector2 nextPosition;

    protected Vector2 currentPosition;

    // Contenedor de textura en cada momento

    private TextureRegion currentTexture;

    // Dirección y estado por defecto

    protected Direction currentDirection = Direction.RIGHT;

    protected State currentState = State.IDLE;


    // Animaciones

    protected float animationTime = 0f;

    protected Animation<TextureRegion> walkUpAnimation;

    protected Animation<TextureRegion> walkDownAnimation;

    protected Animation<TextureRegion> walkLeftAnimation;

    protected Animation<TextureRegion> walkRightAnimation;

    // Tamaño de cada región de la textura (en píxeles)

    protected int tileWidth;

    protected int tileHeight;

    // Tamaño con el que se dibuja la textura en unidades de mapa (1 unidad = 32 píxeles)

    private float drawWidth;

    private float drawHeight;

    // Caja de colisión

    protected Rectangle collisionBox;

    // Vida

    protected int health = 1;

    // Factor de escalado, que se utiliza si un sprite es reescalado al ser dibujado

    private float scaleFactor;

    // Ruta donde se encuentra la hoja de sprites

    private String spritesPath;


    public Entity(int width, int height, float drawWidth, float drawHeight,String spritesPath) {

        tileWidth = width;

        tileHeight = height;

        this.drawWidth = drawWidth;

        this.drawHeight = drawHeight;

        // De momento, el factor de escala para la caja de colisiones solo tiene en cuenta la anchura de dibujado

        scaleFactor = drawWidth / (tileWidth * MapManager.UNIT_SCALE);

        this.spritesPath = spritesPath;

        init();
    }

    protected void init() {

        currentPosition = new Vector2();

        nextPosition = new Vector2();

        velocity = new Vector2(0f,0f);

        // El tamaño de la caja de colisiones es la mitad de la anchura X la mitad de la altura para que sea más difícil recibir daño

        collisionBox = new Rectangle(0,0,(tileWidth * scaleFactor)/2,(tileHeight * scaleFactor)/2);

        // Cargamos la textura (imagen que contiene las animacions por filas)

        Utility.loadTextureAsset(spritesPath);

        loadAnimations(spritesPath);

    }

    // Actualiza el tiempo de animación, entre otros atributos

    public void update (float deltaTime) {

        // Se resetea el valor cada 5s para no sumar indefinidamente

        animationTime = (animationTime + deltaTime) % 5;

        if (currentState != State.IDLE) {

            updateAnimations();
        }

        // Sumamos un cuarto del sprite a la caja de colisiones para compensar espacio vacío que pueda haber desde la izquierda

        collisionBox.x = nextPosition.x / MapManager.UNIT_SCALE + (tileWidth * scaleFactor)/4;

        collisionBox.y = nextPosition.y / MapManager.UNIT_SCALE;

    }

    // Sitúa a la entidad en una posición dada

    public void setPosition(float x, float y) {

        currentPosition.x = x;

        currentPosition.y = y;

        nextPosition.x = x;

        nextPosition.y = y;

    }

    // Carga las animaciones de la entidad

    private void loadAnimations(String animationsPath) {


        Texture tileSheet = Utility.getTextureAsset(animationsPath);

        // Obtenemos el número de filas y columnas

        int num_rows = tileSheet.getHeight() / tileHeight;

        int num_cols = tileSheet.getWidth() / tileWidth ;

        Gdx.app.debug(TAG,"Filas: " + num_rows);

        Gdx.app.debug(TAG,"Columnas: " + num_cols);

        TextureRegion[][] textures = TextureRegion.split(tileSheet,tileWidth,tileHeight);

        // Leemos por columnas, en otro caso podría ser por filas

        Array <TextureRegion> walkDownSheet = new Array<>(false,num_cols);

        Array <TextureRegion> walkLeftSheet = new Array<>(false,num_cols);

        Array <TextureRegion> walkUpSheet = new Array<>(false,num_cols);

        Array <TextureRegion> walkRightSheet = new Array<>(false,num_cols);

        // Guardamos los tiles en sus respectivos arrays

        for(int i = 0; i < num_rows;i++) {

            for(int j = 0; j < num_cols;j++) {

                TextureRegion texture = textures[i][j];

                switch (i) {

                    case 0:

                        walkUpSheet.add(texture);

                        break;

                    case 1:

                        walkLeftSheet.add(texture);

                        break;

                    case 2:

                        walkDownSheet.add(texture);

                        break;

                    case 3:

                        walkRightSheet.add(texture);

                        break;

                }


            }
        }

        currentTexture = walkDownSheet.get(0);

        // Creación de las animaciones correspondientes

        // El tiempo de cada textura cada segundo dependerá del número de columnas

        float frameTime = 1f / num_cols;

        walkDownAnimation = new Animation<>(frameTime, walkDownSheet, Animation.PlayMode.LOOP);

        walkLeftAnimation = new Animation<>(frameTime, walkLeftSheet, Animation.PlayMode.LOOP);

        walkUpAnimation = new Animation<>(frameTime, walkUpSheet, Animation.PlayMode.LOOP);

        walkRightAnimation = new Animation<>(frameTime, walkRightSheet, Animation.PlayMode.LOOP);


    }


    public void setState(State s) {


        currentState = s;

    }

    private void updateAnimations() {

        switch (currentDirection) {

            case DOWN:

                currentTexture = walkDownAnimation.getKeyFrame(animationTime);

                break;

            case LEFT:

                currentTexture = walkLeftAnimation.getKeyFrame(animationTime);

                break;

            case UP:

                currentTexture = walkUpAnimation.getKeyFrame(animationTime);

                break;

            case RIGHT:

                currentTexture = walkRightAnimation.getKeyFrame(animationTime);

                break;

            default:

                break;


        }

    }

    public void setDirection(Direction d) {

        currentDirection = d;


    }

    // Detect collisions for optimized
    public void calculateNextPosition (float deltaTime) {


        nextPosition = currentPosition.cpy();

        velocity.scl(deltaTime);

        switch (currentDirection) {

            case LEFT:

                nextPosition.x -= velocity.x;

                break;


            case RIGHT:

                nextPosition.x += velocity.x;

                break;

            case UP:

                nextPosition.y += velocity.y;

                break;

            case DOWN:

                nextPosition.y -= velocity.y;

                break;

            default:

                break;

        }

        // Volvemos a escalar la velocidad para que mantenga el valor que tenía

        velocity.scl(1 / deltaTime);
    }


    public TextureRegion getCurrentTexture() {

        return currentTexture;
    }

    public Vector2 getCurrentPosition() {

        return currentPosition;
    }

    public Rectangle getCollisionBox() {

        return collisionBox;
    }


    public void updatePosition() {

        currentPosition.x = nextPosition.x;

        currentPosition.y = nextPosition.y;

    }

    public Direction getOppositeDirection() {

        Direction opposite = null;

        switch (currentDirection) {

            case UP:
                opposite = Direction.DOWN;
                break;
            case DOWN:

                opposite = Direction.UP;
                break;

            case LEFT:

                opposite = Direction.RIGHT;
                break;

            case RIGHT:

                opposite = Direction.LEFT;
                break;
        }

        return opposite;

    }

    public void setVelocity(float vx,float vy) {

        velocity.x = vx;

        velocity.y =  vy;

    }

    // Método para dibujar las entidadades a partir del batch del mapa

    public void draw(OrthogonalTiledMapRenderer renderer) {

        renderer.getBatch().draw(currentTexture, currentPosition.x, currentPosition.y, drawWidth, drawHeight);

    }



}
