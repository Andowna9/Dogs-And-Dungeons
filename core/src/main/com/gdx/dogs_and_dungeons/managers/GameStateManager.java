package com.gdx.dogs_and_dungeons.managers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.gdx.dogs_and_dungeons.DogsAndDungeons;
import com.gdx.dogs_and_dungeons.entities.Entity;
import com.gdx.dogs_and_dungeons.screens.MainGameScreen;

// Clase para gestionar los distintos estados del juego

public class GameStateManager {

    private static final String TAG = GameStateManager.class.getSimpleName();


    public enum GameState {
        PLAYING,
        INTERACTING,
        PAUSED,
        GAME_OVER
    }

    private GameState currentGameState;

    private SpriteManager spriteManager;

    private MainGameScreen gameScreen;


    public GameStateManager(MainGameScreen gameScreen, SpriteManager spriteManager) {

        this.spriteManager = spriteManager;

        this.gameScreen = gameScreen;

        // El estado por defecto es jugando

        currentGameState = GameState.PLAYING;

    }

    public void setCurrentGameState(GameState g) {

        currentGameState = g;
    }

    private void changeState() {

        // Estado actual: JUGANDO

        if ( currentGameState == GameState.PLAYING ) {

            // FIN DEL JUEGO

            if (SpriteManager.player.isDead()) {

                Gdx.input.setInputProcessor(null);

                setCurrentGameState(GameState.GAME_OVER);

            }

            // MODO PAUSA

            else if(Gdx.input.isKeyPressed(Input.Keys.ESCAPE)) {

                Gdx.input.setInputProcessor(spriteManager.playerHUD.getStage());

                spriteManager.playerHUD.showPauseMenu();

                setCurrentGameState(GameState.PAUSED);
            }

            // MODO DIÁLOGOS (INTERACCIÓN CON NPCS)

            else if (Gdx.input.isKeyPressed(Input.Keys.E) && SpriteManager.player.isInteracting()) {

                System.out.println("Mostrando diálogo...");

                spriteManager.interactingNPC.setDirection(SpriteManager.player.getOppositeDirection());

                Gdx.input.setInputProcessor(null);

                setCurrentGameState(GameState.INTERACTING);
            }
        }

        // Estado Actual: PAUSADO

        else if (currentGameState == GameState.PAUSED) {

            // Si el HUD ya no está en modo pausa, se puede volver a jugar

            if (!spriteManager.playerHUD.isPaused()) {

                Gdx.input.setInputProcessor(spriteManager.getPlayerController());

                setCurrentGameState(GameState.PLAYING);
            }

        }
    }

    // De momento solo tenemos un estado, pero en caso de tener más, podríamos crear clases estado
    // y guardar instancias en una lista para que el estadp se actualice de forma más dinámica

    public void updateState(float delta) {

        changeState();

        switch (currentGameState) {

            case INTERACTING:

            case PLAYING:

                spriteManager.update(delta);

                break;

            case GAME_OVER:

                spriteManager.updateEnemies(delta);

                SpriteManager.player.update(delta);

                if (SpriteManager.player.getCurrentState() != Entity.State.DYING) {

                    SpriteManager.player.setState(Entity.State.DYING);

                    SpriteManager.player.resetAnimationTime();
                }

                if (SpriteManager.player.animationIsFinished(Entity.State.DYING)) {

                    Gdx.app.log(TAG, "La animación de muerte ha terminado!");

                    gameScreen.switchScreen(DogsAndDungeons.gameOverScreen, delta);
                }

                break;

             // Por defecto, nos se realiza ninguna actualización

            default:

                break;
        }


    }
}
