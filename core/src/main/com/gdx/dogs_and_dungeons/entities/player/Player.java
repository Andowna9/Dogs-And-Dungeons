package com.gdx.dogs_and_dungeons.entities.player;
import com.badlogic.gdx.Gdx;
import com.gdx.dogs_and_dungeons.entities.Entity;
import com.gdx.dogs_and_dungeons.entities.enemies.Enemy;

public class Player extends Entity {

    private static  final String TAG = Player.class.getSimpleName();

    // Ahora las clases hijas como Player, Enemy son las que se encargan de pasar la ruta a las animaciones

    private static final String walkingPath = "player/boy/walking.png";

    private static final String attackingPath = "player/boy/dagger_attack.png";

    private static final String dyingPath = "player/boy/dying.png";

    public Player(int width, int height,float drawWidth, float drawHeight) {

        super(width, height,drawWidth,drawHeight);

        setVelocity(2.5f,2.5f);

        animManager.loadDirectionalAnimations(walkingPath, State.WALKING);

        animManager.loadDirectionalAnimations(attackingPath, State.ATTACKING);

        animManager.loadSingleAnimation(dyingPath,State.DYING);

        setFrameTime(State.ATTACKING,0.1f);

    }

    public void attack(Enemy e) {

        if ( currentState == State.ATTACKING && currentPosition.dst(e.getCurrentPosition()) <= 1.5f) {

            e.receiveDamage();

            Gdx.app.debug(TAG,"Un enemigo ha recibido un ataque!");

        }

    }


}
