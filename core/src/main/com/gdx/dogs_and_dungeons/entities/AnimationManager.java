package com.gdx.dogs_and_dungeons.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import com.gdx.dogs_and_dungeons.Utility;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class AnimationManager {

    private static final String TAG = AnimationManager.class.getSimpleName();

    private int tileWidth;

    private int tileHeight;

    private HashMap<Entity.State,HashMap<Entity.Direction,Animation<TextureRegion>>> dirAnimations;

    private HashMap<Entity.State,Animation<TextureRegion>> singleAnimations;

    public AnimationManager(int tileWidth, int tileHeight, HashMap<Entity.State,HashMap<Entity.Direction,Animation<TextureRegion>>> dirAnimations, HashMap<Entity.State,Animation<TextureRegion>> singleAnimations) {

        this.tileWidth = tileWidth;

        this.tileHeight = tileHeight;

        this.dirAnimations = dirAnimations;

        this.singleAnimations = singleAnimations;
    }

    private List<Animation<TextureRegion>> loadAnimations(String animationsPath,Animation.PlayMode playMode) {

        // Cargamos la textura (imagen que contiene las animacions por filas)

        Utility.loadTextureAsset(animationsPath);

        Texture tileSheet = Utility.getTextureAsset(animationsPath);

        // Obtenemos el número de filas columnas (el número de filas es igual al número de elementos
        // de la enumeración Diretion

        int num_rows = tileSheet.getHeight() / tileHeight;

        int num_cols = tileSheet.getWidth() / tileWidth;

        Gdx.app.debug(TAG, "Columnas de spritesheet: " + num_cols);

        TextureRegion[][] textures = TextureRegion.split(tileSheet, tileWidth, tileHeight);

        // El tiempo de cada textura cada segundo dependerá del número de columnas

        float frameTime = 1f / num_cols;

        // Guardamos los tiles en sus respectivos arrays

        List<Animation<TextureRegion>> animationSheets = new ArrayList<>();

        for (int i = 0 ; i < num_rows; i++ ) {

            Array<TextureRegion> sheet = new Array<>(false, num_cols);

            for (int j = 0; j < num_cols; j++) {

                TextureRegion texture = textures[i][j];

                sheet.add(texture);

            }

            Animation<TextureRegion> anim = new Animation<>(frameTime, sheet, playMode);

            animationSheets.add(anim);

        }

        return animationSheets;
    }

    // Se guardan en el HashMap correspondinte las animaciones asociadas a un estado (de acuerdo con la dirección)

    public void loadDirectionalAnimations(String animationsPath, Entity.State s) {

        List<Animation<TextureRegion>> animations = loadAnimations(animationsPath, Animation.PlayMode.LOOP);

        HashMap<Entity.Direction,Animation<TextureRegion>> dirMap = new HashMap<>();

        for (Entity.Direction direction: Entity.Direction.values()) {

            dirMap.put(direction, animations.get(direction.ordinal()));
        }

        dirAnimations.put(s, dirMap);

        Gdx.app.debug(TAG,"Éxito al cargar las animaciones direccionales para el estado " + s.name());
    }


    // Se guarda en el HashMap correspondiente la única animación cargada

    public void loadSingleAnimation(String animationPath, Entity.State s) {

        singleAnimations.put(s, loadAnimations(animationPath, Animation.PlayMode.NORMAL).get(0));

        Gdx.app.debug(TAG,"Éxito al cargar las animación única para el estado " + s.name());

    }
}
