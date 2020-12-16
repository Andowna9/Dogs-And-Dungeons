package com.gdx.dogs_and_dungeons;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

// Clase para gestionar recursos

// No queremos que la clase pueda ser heredada (final class)

public final class Utility {

    private static final String TAG = Utility.class.getSimpleName();

    // Referencia stática a skin por defecto para acceder convenientemente

    public static final Skin DEFAULT_SKIN = new Skin(Gdx.files.internal("skins/default/uiskin.json"));

    public static final AssetManager assetManager = new AssetManager();

    public static final BitmapFont mainFont = new BitmapFont(Gdx.files.internal("fonts/pixelade.fnt"));

    public static final BitmapFont titleFont = new BitmapFont(Gdx.files.internal("fonts/pixelade_title.fnt"));

    private final static InternalFileHandleResolver pathResolver = new InternalFileHandleResolver();

    // Constructor privado para evitar instanciación

    private Utility() {}

    // Eliminar recurso

    public static void unloadAsset(String assetPath) {

        if (assetManager.isLoaded(assetPath)) {

            // Una vez las referencias al recurso sean nulas, el gestor se encargará de eliminarlo

            assetManager.unload(assetPath);

        }

        else {

            Gdx.app.debug(TAG, "No es posible eliminar un recurso que no ha sido cargado!");
        }
    }

    // Cargar recurso mapa

    public static void loadMapAsset(String mapPath) {

        if (mapPath == null || mapPath.isEmpty()) return;

        if (pathResolver.resolve(mapPath).exists()) {

            assetManager.setLoader(TiledMap.class, new TmxMapLoader(pathResolver)); // Añadimos un nuevi loader para cargar mapas en formato .tmx

            assetManager.load(mapPath, TiledMap.class);

            // Bloqueamos el hilo de renderizado hasta que se haya terminado de cargar (de momento)
            //También se puede gestionar asíncronamente con una pantalla de carga

            assetManager.finishLoading();

            Gdx.app.debug(TAG,"Mapa cargado con éxito!");
        }

        else {

            Gdx.app.error(TAG,"No se ha podido encontrar la ruta donde se encuentra el mapa!");
        }


    }

    // Obtener recurso mapa

    public static TiledMap getMapAsset(String mapPath) {

        TiledMap map = null;

        if (assetManager.isLoaded(mapPath)) {

            map = assetManager.get(mapPath,TiledMap.class);

        }

        else {

            Gdx.app.debug(TAG,"El mapa no se ha cargado todavía!");
        }

        return map;

    }

    // Cargar recurso textura

    public static void loadTextureAsset(String texturePath) {

        if (texturePath == null || texturePath.isEmpty()) return;

        if (pathResolver.resolve(texturePath).exists()) {

            assetManager.load(texturePath, Texture.class);

            assetManager.finishLoading();

            Gdx.app.debug(TAG,"Textura cargada correctamente");

        }

        else {

            Gdx.app.error(TAG,"La textura no existe!");
        }
    }

    public static Texture getTextureAsset(String texturePath) {

        Texture texture = null;

        if (assetManager.isLoaded(texturePath)) {

            texture = assetManager.get(texturePath,Texture.class);

        }

        else {

            Gdx.app.debug(TAG,"La textura no se ha cargado todavía!");
        }

        return texture;
    }




}
