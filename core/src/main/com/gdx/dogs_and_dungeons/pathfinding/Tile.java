package com.gdx.dogs_and_dungeons.pathfinding;

// Clase que representa cada nodo del grafo (en nuestro caso los tiles del mapa o una zona del mapa)

public class Tile {

    // Posición en la matriz que se utiliza para renderizar el mapa

    int x;

    int y;

    // Índice único asociado a cada nodo para poder utilizar A* indexado (versión del algoritmo con mayor rendimiento)

    int index;

    public Tile(int x, int y) {

        this.x = x;

        this.y = y;
    }
}