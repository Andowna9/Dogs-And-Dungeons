package com.gdx.dogs_and_dungeons.managers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.MathUtils;

public class CameraManager {

    private RenderManager renderManager;

    private SpriteManager spriteManager;

    private OrthographicCamera camera;

    private static class VIEWPORT {

        static float viewportWidth;
        static float viewportHeight;
        static float virtualWidth;
        static float virtualHeight;
        static float physicalWidth;
        static float physicalHeight;
        static float aspectRatio;
    }


    public CameraManager(RenderManager renderManager, SpriteManager spriteManager) {

        this.renderManager = renderManager;

        this.spriteManager = spriteManager;

        camera = new OrthographicCamera();

        init();

    }

    private void init() {

        setViewport(15,12);

        camera.setToOrtho(false,VIEWPORT.viewportWidth,VIEWPORT.viewportHeight);

    }

    public void updateCamera() {

        // Actualizamos la posición de la cámara, evitando que se salga de los límites establecidos

        camera.position.x = MathUtils.clamp(spriteManager.getPlayer().getCurrentPosition().x,
                camera.viewportWidth/2,
                spriteManager.mapManager.getCurrentMapWidth() - camera.viewportWidth/2);

        camera.position.y = MathUtils.clamp(spriteManager.getPlayer().getCurrentPosition().y,
                camera.viewportHeight/2,
                spriteManager.mapManager.getCurrentMapHeight() - camera.viewportHeight/2);

        camera.update();

        renderManager.mapRenderer.setView(camera);


    }

    private void setViewport(int width, int height) {

        // La anchura y altura representan el número de tiles que se dibujan

        VIEWPORT.virtualWidth = width;

        VIEWPORT.virtualHeight = height;

        VIEWPORT.viewportWidth = VIEWPORT.viewportWidth;

        VIEWPORT.viewportHeight = VIEWPORT.virtualHeight;

        VIEWPORT.physicalWidth = Gdx.graphics.getWidth();

        VIEWPORT.physicalHeight = Gdx.graphics.getHeight();

        VIEWPORT.aspectRatio = (VIEWPORT.virtualWidth / VIEWPORT.virtualHeight);

        if (VIEWPORT.physicalWidth / VIEWPORT.physicalHeight >= VIEWPORT.aspectRatio) {

            VIEWPORT.viewportWidth = VIEWPORT.viewportHeight * (VIEWPORT.physicalWidth/ VIEWPORT.physicalHeight);
            VIEWPORT.viewportHeight = VIEWPORT.virtualHeight;
        }
        else {

            VIEWPORT.viewportWidth = VIEWPORT.virtualWidth;
            VIEWPORT.viewportHeight = VIEWPORT.viewportWidth * (VIEWPORT.physicalHeight/ VIEWPORT.physicalWidth);
        }

    }


}
