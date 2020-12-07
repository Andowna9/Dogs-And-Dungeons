package com.gdx.dogs_and_dungeons.entities;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.gdx.dogs_and_dungeons.MapManager;
import com.gdx.dogs_and_dungeons.Utility;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

public abstract class Entity {

    private static final String TAG = Entity.class.getSimpleName();

    public enum State {

        WALKING, ATTACKING, IDLE, DYING
    }

    public enum Direction {

        UP, LEFT, DOWN, RIGHT
    }

    // Propiedades relacionadas con movimiento

    protected Vector2 velocity;

    protected Vector2 initialPosition;

    protected Vector2 nextPosition;

    protected Vector2 currentPosition;

    // Contenedor de textura en cada momento

    private TextureRegion currentTexture;

    // Dirección y estado por defecto

    protected Direction currentDirection = Direction.RIGHT;

    protected State currentState = State.IDLE;


    // Animaciones

    protected float animationTime = 0f;

    // Animaciones con varias direcciones

    protected HashMap<State,HashMap<Direction,Animation<TextureRegion>>> dirAnimations;

    // Animaciones en una única dirección

    protected  HashMap<State,Animation<TextureRegion>> singleAnimations;

    // Gestor de animaciones (principalmente carga)

    protected AnimationManager animManager;

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

    // Parpadeo al recibir daño

    private float blinkingTime = 0f;

    private  float maxBlinkingTime = 2f;

    private Timer blinkingTimer;

    private boolean isRendered = true;

    protected boolean isBlinking = false;


    // Factor de escalado, que se utiliza si un sprite es reescalado al ser dibujado

    private float scaleFactor;


    public Entity(int width, int height, float drawWidth, float drawHeight) {

        tileWidth = width;

        tileHeight = height;

        this.drawWidth = drawWidth;

        this.drawHeight = drawHeight;

        // De momento, el factor de escala para la caja de colisiones solo tiene en cuenta la anchura de dibujado

        scaleFactor = drawWidth / (tileWidth * MapManager.UNIT_SCALE);

        dirAnimations = new HashMap<>();

        singleAnimations = new HashMap<>();

        animManager = new AnimationManager(tileWidth,tileHeight,dirAnimations,singleAnimations);

        init();
    }

    protected void init() {

        currentPosition = new Vector2();

        nextPosition = new Vector2();

        initialPosition = new Vector2();

        velocity = new Vector2(0f,0f);

        // El tamaño de la caja de colisiones es la mitad de la anchura X la mitad de la altura para que sea más difícil recibir daño

        collisionBox = new Rectangle(0,0,(tileWidth * scaleFactor)/2,(tileHeight * scaleFactor)/2);

        // El timer es daemon, por lo que no se espera al hilo asociado para finalizar la ejecución del programa

        blinkingTimer = new Timer(true);

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

        // Actualización del parpadeo

        if (isBlinking) {

            blinkingTime += deltaTime;
        }


    }

    // Sitúa a la entidad en una posición dada

    protected void setDefaultTexture(State state, Direction direction) {

        currentTexture = dirAnimations.get(state).get(direction).getKeyFrame(0);
    }


    public void setPosition(float x, float y) {

        currentPosition.x = x;

        currentPosition.y = y;

        nextPosition.x = x;

        nextPosition.y = y;

    }


    protected void setFrameTime(State s, float frameTime) {

        for (Animation<TextureRegion> a: dirAnimations.get(s).values()) {

            a.setFrameDuration(frameTime);
        }

    }


    public void setState(State s) {


        currentState = s;

    }

    private void updateAnimations() {

        if (singleAnimations.containsKey(currentState)) currentTexture = singleAnimations.get(currentState).getKeyFrame(animationTime);

        else currentTexture = dirAnimations.get(currentState).get(currentDirection).getKeyFrame(animationTime);

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

    // Método para restar vida al enemigo (de momento siempre resta 1)

    public void receiveDamage() {

        health--;

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

    public boolean isCollidingWithEntity(Entity other) {

        return this.collisionBox.overlaps(other.collisionBox);

    }

    public void resetAnimationTime() {

        animationTime = 0;
    }

    public  State getCurrentState() {

        return currentState;
    }

    public boolean animationIsFinished(State s) {

        if (singleAnimations.containsKey(s)) return singleAnimations.get(s).isAnimationFinished(animationTime);

        return dirAnimations.get(s).get(currentDirection).isAnimationFinished(animationTime);

    }


    public void updatePosition() {

        currentPosition.x = nextPosition.x;

        currentPosition.y = nextPosition.y;

    }

    public void setBlinking() {

        isBlinking = true;

        Gdx.app.log(TAG, "Entrando en modo parpadeo...");

        // El timer ejecuta la tarea en un hilo distinto, luego es muy importante cancelarlo una vez terminado un tiempo

        blinkingTimer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {

                if (blinkingTime >= maxBlinkingTime) {

                    blinkingTime = 0;

                    isRendered = true;

                    isBlinking = false;

                    Gdx.app.log(TAG,"Timer finalizado");

                    cancel();

                }

                else {

                    isRendered = !isRendered;

                }
            }
        }, 0, 100);


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

    public void render(OrthogonalTiledMapRenderer renderer) {

        // Si la entidad no está en modo parpadeo, se renderiza la textura

        if(isRendered) {

            renderer.getBatch().draw(currentTexture, currentPosition.x, currentPosition.y, drawWidth, drawHeight);

        }
    }

    public void setInitialPosition(float x, float y) {

        initialPosition.x = x;

        initialPosition.y = y;

        // Actualizamos la posición actual y la siguiente para que coincidan con la inicial

       setPosition(initialPosition.x,initialPosition.y);

    }

    public boolean isBlinking() {

        return isBlinking;
    }

    public int getHealth() {

        return health;
    }

    public boolean isDead() {

        return health <= 0;
    }


}
