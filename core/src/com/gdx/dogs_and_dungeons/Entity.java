package com.gdx.dogs_and_dungeons;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

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

    protected TextureRegion currentTexture;

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

    // Caja de colisión

    protected Rectangle collisionBox;

    public Entity(int width,int height) { // Probamos con 48 px X 48 px

        tileWidth = width;

        tileHeight = height;

        init();

    }

    protected void init() {

        currentPosition = new Vector2();

        nextPosition = new Vector2();

        velocity = new Vector2(2.5f,2.5f);

        collisionBox = new Rectangle(0,0,tileWidth/2,tileHeight/4);

        // Cargamos la textura (imagen que contiene las animacions por filas)

        Utility.loadTextureAsset("player/george.png");

        loadAnimations();

    }

    // Actualiza el tiempo de animación, entre otros atributos

    public void update (float deltaTime) {

        // Se resetea el valor cada 5s para no sumar indefinidamente

        animationTime = (animationTime + deltaTime) % 5;

        collisionBox.x = nextPosition.x / MapManager.UNIT_SCALE + tileWidth/4;

        collisionBox.y = nextPosition.y / MapManager.UNIT_SCALE;

    }

    // Sitúa a la entidad en una posición dada

    public void setPosition(float x, float y) {

        currentPosition.x = x;

        currentPosition.y = y;

        nextPosition.x = x;

        nextPosition.y = y;

    }

    public void loadAnimations() {

        Texture tileSheet = Utility.getTextureAsset("player/george.png");

        TextureRegion[][] textures = TextureRegion.split(tileSheet,tileWidth,tileHeight);

        // Leemos por columnas, en otro caso podría ser por filas

        Array <TextureRegion> walkDownSheet = new Array<>(false,4);

        Array <TextureRegion> walkLeftSheet = new Array<>(false,4);

        Array <TextureRegion> walkUpSheet = new Array<>(false,4);

        Array <TextureRegion> walkRightSheet = new Array<>(false,4);

        // Guardamos los tiles en sus respectivos arrays

        for (int j = 0; j < 4; j++) {

            for (int i = 0; i < 4;i++) {

                TextureRegion texture = textures[i][j];


                switch (j) {

                    case 0:

                        walkDownSheet.add(texture);

                        break;

                    case 1:

                        walkLeftSheet.add(texture);

                        break;

                    case 2:

                        walkUpSheet.add(texture);

                        break;

                    case 3:

                        walkRightSheet.add(texture);

                        break;
                }

            }


        }

        currentTexture = walkDownSheet.get(0);

        // Creación de las animaciones correspondientes

        walkDownAnimation = new Animation<>(0.25f, walkDownSheet, Animation.PlayMode.LOOP);

        walkLeftAnimation = new Animation<>(0.25f, walkLeftSheet, Animation.PlayMode.LOOP);

        walkUpAnimation = new Animation<>(0.25f, walkUpSheet, Animation.PlayMode.LOOP);

        walkRightAnimation = new Animation<>(0.25f, walkRightSheet, Animation.PlayMode.LOOP);


    }


    public void setState(State s) {


        currentState = s;

    }

    public void setDirection(Direction d) {

        currentDirection = d;

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









}
