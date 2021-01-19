package com.gdx.dogs_and_dungeons.entities.player;

import com.badlogic.gdx.Gdx;
import com.gdx.dogs_and_dungeons.entities.Entity;
import com.gdx.dogs_and_dungeons.entities.enemies.Enemy;
import com.gdx.dogs_and_dungeons.entities.player.hud.ui.StatusUI;
import com.gdx.dogs_and_dungeons.managers.SpriteManager;
import com.gdx.dogs_and_dungeons.screens.SelectionScreen;

public class Player extends Entity {

    private static  final String TAG = Player.class.getSimpleName();

    // Ahora las clases hijas como Player, Enemy son las que se encargan de pasar la ruta a las animaciones

    private static final String walkingPath = "player/x/walking.png";

    private static final String attackingPath = "player/x/attack.png";

    private static final String dyingPath = "player/x/dying.png";

    // Booleano que indica si es posible atacar en un momento dado

    private boolean isCoolingDown = false;

    // Booleano para saber si el jugador está interactuando

    private boolean isInteracting = false;

    // Máxima salud del jugador

    private static final int MAX_HEALTH = 7;

    // Velocidad de movimiento

    private float normalSpeed = 2.5f;

    // Velocidad de animación de movimiento

    private float walkingFrameTime;

    // Daño

    private int normalDamage = 1;


    public Player(int width, int height,float drawWidth, float drawHeight) {

        super(width, height,drawWidth,drawHeight);

        setVelocity(normalSpeed, normalSpeed);

    }

    private void loadPlayerSprites() {

        clearAnimations();

        String gender = SelectionScreen.getGender();

        Gdx.app.debug(TAG, "Cargando sprites del género: " + gender);

        animManager.loadDirectionalAnimations(walkingPath.replace("x", gender), State.WALKING);

        animManager.loadDirectionalAnimations(attackingPath.replace("x", gender), State.ATTACKING);

        animManager.loadSingleAnimation(dyingPath.replace("x", gender),State.DYING);

        setFrameTime(State.ATTACKING,0.1f);

        walkingFrameTime = animManager.getDefaultFrameTime(State.WALKING);

    }

    @Override
    public void initEntity() {

        loadPlayerSprites();

        setHealth(7);

        setInitialPosition(SpriteManager.mapManager.getPlayerSpawnPosition(currentPosition));

        setState(Entity.State.IDLE);

        setDirection(Entity.Direction.UP);
    }

    public void checkAttack(Enemy e) {

        // Cuando el jugador es atacado y se vuelve inmune, no hace daño al enemigo

        if (isBlinking) {

            isCoolingDown = true;
        }

        // Si no se cumple lo anterior y está en reposo, puede atacar

        else if (currentState == State.IDLE) {

            isCoolingDown = false;
        }

        if (!isCoolingDown && !e.isBlinking() && currentState == State.ATTACKING && currentPosition.dst(e.getCurrentPosition()) <= 1.5f) {

            e.receiveDamageFrom(this);

            SpriteManager.audioManager.playSound("daggerSlice");

            e.setBlinking(0.5f);

            Gdx.app.debug(TAG,"Un enemigo " + e.getClass().getSimpleName() + " ha recibido un ataque! Vida restante: " + e.getHealth());

            isCoolingDown = true;
        }

    }

    public boolean isInteracting() {

        return isInteracting;
    }

    public void  setInteracting(boolean b) {

        isInteracting = b;
    }

    public void addHealth(int value) {

        health += value;

        if (health > MAX_HEALTH) {

            health = MAX_HEALTH;
        }
    }

    public void increaseSpeed() {

        if (StatusUI.startCountdown(StatusUI.speedCountdown)) {

            setVelocity(normalSpeed * 1.5f, normalSpeed * 1.5f);

            setFrameTime(State.WALKING, walkingFrameTime / 1.5f);
        }

    }

    public void increaseDamage() {

        if (StatusUI.startCountdown(StatusUI.damageCountdown)) {

            damage += 1;
        }

    }

    public void restoreSpeed() {

        setVelocity(normalSpeed,normalSpeed);

        setFrameTime(State.WALKING, walkingFrameTime);
    }

    public void restoreDamage() {

        damage = normalDamage;
    }

}
