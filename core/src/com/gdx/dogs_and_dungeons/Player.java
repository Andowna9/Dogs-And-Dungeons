package com.gdx.dogs_and_dungeons;

public class Player extends Entity {

    // Ahora las clases hijas como Player, Enemy son las que se encargan de pasar la ruta a las animaciones

    private static final String spritesPath = "player/boy_spritesheet.png";

    public Player(int width, int height) {

        super(width, height,spritesPath);

    }

    public Player(int width, int height, float scaleFactor) {

        super(width, height,scaleFactor,spritesPath);
    }


}
