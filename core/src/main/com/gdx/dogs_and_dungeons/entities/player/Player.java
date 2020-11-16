package com.gdx.dogs_and_dungeons.entities.player;
import com.badlogic.gdx.Gdx;
import com.gdx.dogs_and_dungeons.entities.Entity;
import com.gdx.dogs_and_dungeons.entities.enemies.Enemy;

public class Player extends Entity {

    private static  final String TAG = Player.class.getSimpleName();

    // Ahora las clases hijas como Player, Enemy son las que se encargan de pasar la ruta a las animaciones

    private static final String walkingPath = "player/boy/walking.png";

    private static final String attackingPath = "player/boy/dagger_attack.png";

    public Player(int width, int height,float drawWidth, float drawHeight) {

        super(width, height,drawWidth,drawHeight);

        // Vida del jugador

        health = 5;

        setVelocity(2.5f,2.5f);

        loadAnimations(walkingPath, State.WALKING);

        loadAnimations(attackingPath, State.ATTACKING);

        setFrameTime(State.ATTACKING,0.1f);

        setDefaultTexture(State.WALKING, Direction.UP);

        setDirection(Direction.UP);

    }

    public void attack(Enemy e) {

        if ( currentState == State.ATTACKING && currentPosition.dst(e.getCurrentPosition()) <= 1.5f) {

            e.receiveDamage();

            Gdx.app.debug(TAG,"Un enemigo ha recibido un ataque!");

        }

    }


}
