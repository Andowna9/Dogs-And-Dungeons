package com.gdx.dogs_and_dungeons;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
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

    private Vector2 velocity;

    private Vector2 nextPosition;

    private Vector2 currentPosition;

    // Contenedor de la posición en cada momento, al igual que textura

    private Sprite sprite;

    private TextureRegion currentTexture;

    // Dirección y estado por defecto

    private Direction currentDirection = Direction.RIGHT;

    private State currentState = State.IDLE;


    // Animaciones

    private float animationTime = 0f;

    private Animation<TextureRegion> walkUpAnimation;

    private Animation<TextureRegion> walkDownAnimation;

    private Animation<TextureRegion> walkLeftAnimation;

    private Animation<TextureRegion> walkRightAnimation;

    // Tamaño de cada región de la textura (en píxeles)

    private int tileWidth;

    private int tileHeight;

    public Entity(int width,int height) { // Probamos con 48 px X 48 px

        tileWidth = width;

        tileHeight = height;

        init();

    }

    private void init() {

        sprite = new Sprite();

        currentPosition = new Vector2();

        nextPosition = new Vector2();

        velocity = new Vector2(50f,50f);

        // Cargamos la textura (imagen que contiene las animacions por filas)

        Utility.loadTextureAsset("player/george.png");

        loadAnimations();

    }

    // Actualiza el tiempo de animación, entre otros atributos

    public void update (float deltaTime) {

        // Se resetea el valor cada 5s para no sumar indefinidamente

        animationTime = (animationTime + deltaTime) % 5;

    }

    // Sitúa a la entidad en una posición dada

    public void setPosition(float x, float y) {

        currentPosition.x = x;

        currentPosition.y = y;

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

    public void calculateNextPosition (float deltaTime) {


        nextPosition = currentPosition;

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



        // Comprobar si hay colisón y en tal caso no actualizar la posición actual

        // En caso contrario

        currentPosition = nextPosition;

        // Volvemos a escalar la velocidad para que mantenga el valor que tenía

        velocity.scl(1 / deltaTime);
    }


    public TextureRegion getCurrentTexture() {

        return currentTexture;
    }

    public Vector2 getCurrentPosition() {

        return currentPosition;
    }








}
