package com.gdx.dogs_and_dungeons.pathfinding;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ai.pfa.Connection;
import com.badlogic.gdx.ai.pfa.DefaultGraphPath;
import com.badlogic.gdx.ai.pfa.GraphPath;
import com.badlogic.gdx.ai.pfa.indexed.IndexedAStarPathFinder;
import com.badlogic.gdx.ai.pfa.indexed.IndexedGraph;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ObjectMap;
import com.gdx.dogs_and_dungeons.MapManager;
import com.gdx.dogs_and_dungeons.entities.Entity;
import com.gdx.dogs_and_dungeons.managers.SpriteManager;
import java.util.Arrays;

// Esta clase se comporta como un grafo indexado que es capaz de encontrar la ruta óptima entre 2 de sus nodos (inicio, fin)

public class TileGraph implements IndexedGraph<Tile> {

    private static final String TAG = TileGraph.class.getSimpleName();

    // Propiedades de la zona del mapa

    // Tiles (nodos) del grafo

    Tile [][] tiles;

    // Vértices del rectángulo que rodea la zona del mapa

    private int initX;

    private int endX;

    private int initY;

    private int endY;

    // Conexiones entre tiles (aristas) del grafo

    Array<TileConnection> tileConnections = new Array<>();

    // Nodos en una lista

    Array<Tile> tileArray = new Array<>();

    // Función para que A* realice estimaciones

    TileHeuristic heuristic = new TileHeuristic();

    // Mapa que contiene todas las conexiones para un tile dado

    ObjectMap<Tile,Array<Connection<Tile>>> connectionsMap = new ObjectMap<>();

    // Índice del último nodo no nulo

    private int lastTileIndex = 0;

    // Zona  a partir de la cual se crea el grafo

    private String zone;

    // Rectángulo que representa los límites de la zona en el mapa

    private Rectangle zoneRect;

    // Constructor del grafo
    // Recibe una capa del mapa y la convierte en un grafo

    public TileGraph(TiledMapTileLayer layer, String location) {

        zone = location;

        RectangleMapObject obj = (RectangleMapObject) SpriteManager.mapManager.getLocationsLayer().getObjects().get(location);

        Rectangle rect = obj.getRectangle();

        zoneRect = rect;

        // Fila y columna iniciales

        initX = MathUtils.floorPositive(rect.x * MapManager.UNIT_SCALE);

        initY = MathUtils.floorPositive(rect.y * MapManager.UNIT_SCALE);

        Gdx.app.debug(TAG, String.format("Pos inicial: (%d, %d)", initX, initY));

        // Fila y columna finales

        endX = MathUtils.ceilPositive((rect.x + rect.width)  * MapManager.UNIT_SCALE);

        endY = MathUtils.ceilPositive((rect.y + rect.height) * MapManager.UNIT_SCALE);

        Gdx.app.debug(TAG, String.format("Pos final: (%d, %d)", endX, endY));

        // Filas y columnas

        int cols = endX - initX; // Eje horizontal(X) -> Columnas
                                                                    // Aunque pueda resultar confuso
        int rows = endY - initY; // Eje vertical(Y) -> Filas

        tiles = new Tile[rows][cols];

        fillGraph(layer);

        createConnections();

        Gdx.app.debug(TAG, "Número de nodos: " + getNodeCount());

        showGraph();

    }

    // Método para añadir y guardar los nodos correspondientes

    private void fillGraph(TiledMapTileLayer layer) {

        // Añadimos los tiles al grafo: Estos representan los bloques por los que es posible moverse

        for (int y = 0; y < tiles.length; y++) {

            for (int x = 0; x < tiles[y].length; x++) {

                // Aplicamos el offset correspondiente para obtener la celda desde el vértoce inferior izquierdo del rectángulo que limita la zona

                TiledMapTileLayer.Cell cell = layer.getCell(initX + x, initY + y);

                // Si la celda actual es nula, continuamos (no nos interesa porque no forma parte del camino)

                if (cell == null) continue;

                // Creamos un nodo con las coordenadas x e y relativas a la zona

                Tile currentTile = new Tile(x, y, initX + x, initY + y);

                // Se añade el nodo actual al grafo
                // Para mantener el sistema de coordenadas del mapa(derecha-arriba) empezamos añadiendo desde la última fila

                addTile(currentTile,tiles.length - 1 - y,x);

                // También se añade a una lista, en la que no habrá tiles nulos

                tileArray.add(currentTile);

            }
        }
    }

    // Método para conectar los nodos del grafo
    // No crea conexiones entre tiles que están al lado de forma diagonal, ya que al juego no comtempla movimientos diagonales

    private void createConnections() {

        for (int i = 0; i < tiles.length; i++) {

            for (int j = 0; j < tiles[i].length; j++) {

                if (tiles[i][j] == null) continue;

                // Si no estamos en la primera columna: Vecino a la izquirda

                if (j != 0) {

                    if (tiles[i][j - 1] != null) {

                        connectTiles(tiles[i][j], tiles[i][j - 1]);
                    }
                }

                // Si no estamos en la última columna: Vecino a la derecha

                if (j != tiles[i].length - 1) {

                    // Comprobamos si hay un nodo a la derecha del nodo actual y en tal caso se crea la conexión

                    if (tiles[i][j + 1] != null) {

                        connectTiles(tiles[i][j], tiles[i][j + 1]);

                    }
                }

                // Si no estamos en la primera fila: Vecino encima

                if (i != 0) {

                    if (tiles[i - 1][j] != null) {

                        connectTiles(tiles[i][j], tiles[i - 1][j]);
                    }
                }

                // Si no estamos en la última fila: Vecino debajo

                if (i != tiles.length - 1) {

                    // Comprobamos si hay un nodo debajo del nodo actual y en tal caso se crea la conexión

                    if (tiles[i + 1][j] != null) {

                        connectTiles(tiles[i][j], tiles[i + 1][j]);
                    }
                }

            }

        }


    }

    // Método privado usado por fillGraph() para indexar nodos y posteriormenete añadirlos a la matriz tiles

    private void addTile(Tile tile, int row, int col) {

        tile.index = lastTileIndex;

        lastTileIndex++;

        tiles[row][col] = tile;

    }

    // Método para obtener el nodo en las coordenada (x, y) relativas proporcionadas
    // Devuelve null si no hay nodo en dicha posición o está fuera de los límites

    private Tile getTile(int x, int y) {

        if (y >= tiles.length || y < 0 || x >= tiles[y].length || x < 0) {

            return null;
        }

        return tiles[tiles.length - 1 - y][x];
    }

    // Método para obtener el tile en coordenadas del mapa

    public Tile getTileAt(Vector2 pos) {

        // Obtenemos la posición en el mapa redondeando al entero más cercano

        int x = MathUtils.roundPositive(pos.x);

        int y = MathUtils.roundPositive(pos.y);

        // Se obtinen las posiciones relativas, de acuerdo a la posición en el mapa

        x -= initX;

        y -= initY;

        return getTile(x, y);
    }

    // Método para obtener un tile (nodo) de forma aleatoria

    public Tile getRandomTile() {

        int index = MathUtils.random(tileArray.size - 1);

        return tileArray.get(index);
    }


    // Método para para obtener la posición de una entidad en la zona
    // Devuelve null si la entidad no está dentro de la zona

    public Tile getTileFrom(Entity e) {


        return getTileAt(e.getCurrentPosition());
    }


    // Método para imprimir el grafo en pantalla como un mapa bidimensional en el que cada posición es un nodo

    public void showGraph() {

        String message = ".... Zona: " + zone + " ....";

       for (Tile[] tile: tiles) {

           message += "\n" + Arrays.toString(tile);
       }

        message += "\n" + "...........";

       Gdx.app.debug(TAG, message);
    }

    // Método para comprobar si el jugador está dentro de la zona en cuestión

    public boolean isPlayerInsideZone() {

        return zoneRect.contains(SpriteManager.player.getCollisionBox());

    }

    // Método para obtener el nombre de la zona en cuestión

    public String getZoneName() {

        return zone;
    }


    // Método para conectar un tile con otro y añadir

    public void connectTiles(Tile fromTile, Tile toTile) {

        TileConnection tileConnection = new TileConnection(fromTile,toTile);

        if (!connectionsMap.containsKey(fromTile)) {

            connectionsMap.put(fromTile,new Array<Connection<Tile>>());
        }

        connectionsMap.get(fromTile).add(tileConnection);

        tileConnections.add(tileConnection);

    }

    // Método que devuelve el camino a través del grafo desde el tile de inicio hasta el tile objetivo

    public GraphPath<Tile> findPath(Tile startTile, Tile goalTile) {

        GraphPath<Tile> tilePath = new DefaultGraphPath<>();

        boolean isPathFound = new IndexedAStarPathFinder<>(this).searchNodePath(startTile,goalTile,heuristic,tilePath);

        if (isPathFound) {

            String path = "";

            String arrow = " -> ";

            for (Tile tile: tilePath) {

                path = path + tile.toString() + arrow;
            }

            path = path.substring(0, path.length() - arrow.length());

            Gdx.app.debug(TAG, "Camino encontrado desde " + startTile + " hasta " + goalTile + ": " + path);
        }

        else Gdx.app.debug(TAG, "No se ha encontrado ninguna ruta desde " + startTile + " hasta " + goalTile);

        return tilePath;
    }

    // MÉTODOS PROPIOS DE LA IMPLEMENTACIÓN DEL GRAFO

    // Método que devuelve el índice de un tile

    @Override
    public int getIndex(Tile node) {
        return node.index;
    }

    // Método que devuelve el número de nodos

    @Override
    public int getNodeCount() {
        return lastTileIndex;
    }

    // Método que devuelve una lista con las conexiones de un nodo dado

    @Override
    public Array<Connection<Tile>> getConnections(Tile fromNode) {

        if (connectionsMap.containsKey(fromNode)) {

            return connectionsMap.get(fromNode);
        }

        return new Array<>(0);

    }
}
