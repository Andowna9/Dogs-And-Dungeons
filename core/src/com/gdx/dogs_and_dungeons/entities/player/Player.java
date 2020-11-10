package com.gdx.dogs_and_dungeons.entities.player;
import com.gdx.dogs_and_dungeons.entities.Entity;

public class Player extends Entity {

    // Ahora las clases hijas como Player, Enemy son las que se encargan de pasar la ruta a las animaciones

    private static final String spritesPath = "player/boy/walking.png";

    public Player(int width, int height,float drawWidth, float drawHeight) {

        super(width, height,drawWidth,drawHeight);

        setVelocity(2.5f,2.5f);

        loadAnimations(spritesPath, State.WALKING);

        setDefaultTexture(State.WALKING, Direction.UP);

    }


}
