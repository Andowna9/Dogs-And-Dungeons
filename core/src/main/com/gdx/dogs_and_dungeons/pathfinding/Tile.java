package com.gdx.dogs_and_dungeons.pathfinding;

// Clase que representa cada nodo del grafo (en nuestro caso los tiles del mapa o una zona del mapa)

import com.badlogic.gdx.math.Rectangle;
import com.gdx.dogs_and_dungeons.MapManager;

public class Tile {

    // Posición en la matriz que se utiliza para renderizar el mapa

    int x;

    int y;

    // Índice único asociado a cada nodo para poder utilizar A* indexado (versión del algoritmo con mayor rendimiento)

    int index;

    // Rectángulo con dimensiones del tile (bloque)

    Rectangle rect;

    public Tile(int x, int y, int realX, int realY) {

        this.x = x;

        this.y = y;

        rect = new Rectangle(realX/MapManager.UNIT_SCALE,realY /MapManager.UNIT_SCALE, 32, 32);
    }

    @Override
    public String toString() {

       return String.format("(%d, %d)", x, y);

    }

    // Dos tiles son iguales si lo son sus coordenadas x e y

    @Override
    public boolean equals(Object o) {

        if (!(o instanceof Tile)) {

            return false;
        }

        Tile t = (Tile) o;

        return  this.x == t.x && this.y == t.y;
    }

}
