package com.gdx.dogs_and_dungeons.pathfinding;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ai.pfa.GraphPath;
import com.badlogic.gdx.utils.Queue;
import com.gdx.dogs_and_dungeons.entities.Entity;

// Clase que utilizarán todas aquellas entidades cuyo movimiento depende de un grafo

public class PfaAgent {

    private static final String TAG = PfaAgent.class.getSimpleName();

    // Grafo

    private TileGraph graph;

    // Último tile procesado

    private Tile lastTile;

    // Cola con los tiles que faltan por recorrer en el camino

    private Queue<Tile> pathQueue;

    // Referencia a entidad

    private Entity entity;

    // Tile inicial de la entidad (correspondiente a spawn)

    private Tile initTile;

    private Entity.Direction initDirection;

    // Destino alcanzado

    private boolean destReached = true;

    // Creamos un agente con acceso al grafo y las propiedades de la entidad

    public PfaAgent(TileGraph graph, Entity e) {

        this.graph = graph;

        entity = e;

        pathQueue = new Queue<>();

        initTile = graph.getTileAt(e.getInitialPosition());

        initDirection = entity.getCurrentDirection();

    }


    // Se encuentra la ruta y se añaden los nodos en orden a la cola (a espera de ser procesados)

    public void setPath(Tile startTile, Tile goalTile) {

        if (startTile == null || goalTile == null) return;

        // Si el inicio y el final es el mismo, no hay ningún camino que recorrer

        if (goalTile.equals(startTile)) return;

        GraphPath<Tile> path = graph.findPath(startTile, goalTile);

        // Vaciamos la cola por si se ha vuelto a llamar al método antes de completar el recorrido

        pathQueue.clear();

        // Añadimos todos los nodos a la cola, menos el primero (actual)

        for (int i = 1; i < path.getCount(); i++) {

            pathQueue.addLast(path.get(i));
        }

        destReached = false;

        // Nodo actual

        lastTile = path.get(0);

        // Cambio de estado a WALKING

        entity.setState(Entity.State.WALKING);

        // Calculo de la dirección para llegar al próximo nodo

        calculateDirection(pathQueue.first());

    }

    // Movimiento del agente

    public void move() {

        if (pathQueue.size > 0) {

            Tile targetTile = pathQueue.first();

            // Si el rectángulo del siguiente tile contine la caja de colisiones de la entidad, se ha alcanzado el siguiente tile

            if (targetTile.rect.contains(entity.getCollisionBox())) {

                reachNextTile();
            }
        }

    }

    // Método que calcula la dirección para llegar al próximo nodo, basándose en actual (ya procesado)

    private void calculateDirection(Tile nextTile) {

            float deltaX = nextTile.x - lastTile.x;

            float deltaY = nextTile.y - lastTile.y;

            // Dependiendo de la diferencia de coordenadas de los tiles, se asigna una dirección

            if (deltaX > 0) entity.setDirection(Entity.Direction.RIGHT);

            if (deltaX < 0) entity.setDirection(Entity.Direction.LEFT);

            if (deltaY > 0) entity.setDirection(Entity.Direction.UP);

            if (deltaY < 0) entity.setDirection(Entity.Direction.DOWN);

    }

    // Método al que se llama cada vez que se llega al siguiente nodo en la cola

    private void reachNextTile() {

        lastTile = pathQueue.removeFirst();

        // Cola vacía -> Destino alcanzado

        if (pathQueue.size == 0) {

            destinationReached();
        }

        // En caso contrario, se calcula una nueva dirección para moverse a partir del siguiente nodo en la cola

        else {

            calculateDirection(pathQueue.first());
        }

    }

    public void returnToInitialTile() {

        setPath(lastTile, initTile);
    }

    public boolean isDestinationReached() {

        return destReached;
    }

    // Método al que se llama cuando el camino más reciente se ha completado

    private void destinationReached() {

        Gdx.app.debug(TAG, "Destino alcanzado!");

        destReached = true;

        entity.setState(Entity.State.IDLE);

        // Si el destino es el punto de inicio, se pone en la dirección inicial

        if (lastTile.equals(initTile)) entity.setDirection(initDirection);

    }

}
