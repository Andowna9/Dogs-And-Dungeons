package com.gdx.dogs_and_dungeons.entities.enemies;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Timer;
import com.gdx.dogs_and_dungeons.managers.SpriteManager;
import com.gdx.dogs_and_dungeons.pathfinding.PfaAgent;
import com.gdx.dogs_and_dungeons.pathfinding.Tile;
import com.gdx.dogs_and_dungeons.pathfinding.TileGraph;

// Enemigo que es capaz de evitar obstaculos en una zona restringida (zona de jefe) por medio de A* (algoritmo de pathfinding)

public class BossEnemy extends Enemy {

    private static final String TAG = BossEnemy.class.getSimpleName();

    private static final String specificPath = "dark_skeleton.png";

    // Variable booleana para controlar si el enemigo ha entrado en modo persecución

    private boolean isFollowingPlayer = false;

    // Representación en forma de grafo de las zonas de movimiento

    private TileGraph tileGraph;

    // Agente de pathfinding con que permiten mover la entidad por el grafo

    private PfaAgent pfaAgent;

    // Timer para realizar acciones de forma periódica o pasado un tiempo

    private Timer timer;

    // Constructor completo

    public BossEnemy(int width, int height,float drawWidth, float drawHeight) {

        super(width, height, drawWidth, drawHeight, specificPath);
    }

    // Constructor por defecto

    public BossEnemy() {

        super(specificPath);
    }


    // Comportamiento/lógica del enemigo

    @Override
    public void initEnemy() {

        setHealth(5);

        setVelocity(2f, 2f);

        setState(State.IDLE);

        setDirection(Direction.LEFT);

        tileGraph = new TileGraph(SpriteManager.mapManager.getAStarLayer(),"GRAVEYARD");

        timer = new Timer();

        pfaAgent = new PfaAgent(tileGraph,this);

    }


    public void setPathToPlayer() {

        // Cuidado porque el método getTile() del grafo puede devolver valor nulo

        Tile playerTile = tileGraph.getTileFrom(SpriteManager.player);

        Tile enemyTile = tileGraph.getTileFrom(this);

        // Establecemos un nuevo camino para el agente desde su posición hasta la del jugador

        pfaAgent.setPath(enemyTile, playerTile);

        isFollowingPlayer = true;

    }

    @Override
    public void behave(float delta) {

        // Cuando el jugador no es perseguido

       if (!isFollowingPlayer) {

            // Entra en la zona el jugador

            if (tileGraph.isPlayerInsideZone()) {

                Gdx.app.debug(TAG, "El jugador ha entrado en la zona: " + tileGraph.getZoneName());

                setPathToPlayer();
            }

        }

       // Cuando el jugador está siendo perseguido

        else {

            if (!tileGraph.isPlayerInsideZone()) {

                // El enemigo vuelve a la posición inicial  2 s después que el jugador salga de la zona

                pfaAgent.returnToInitialTile();

                // Se deja de seguir al jugador

                isFollowingPlayer = false;

                Gdx.app.debug(TAG, "El jugador ha salido de la zona: " + tileGraph.getZoneName());
            }

           // Si se ha alcanzado el destino, se vuelve a buscar un destino hacia donde se encuentra el jugador

            if (pfaAgent.isDestinationReached()) {

                setPathToPlayer();
            }

        }

        // Gestión del movimiento del

        pfaAgent.move();

    }
}
