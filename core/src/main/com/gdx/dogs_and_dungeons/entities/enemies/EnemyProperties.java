package com.gdx.dogs_and_dungeons.entities.enemies;

import com.badlogic.gdx.math.Vector2;
import com.gdx.dogs_and_dungeons.entities.Entity;

// Propiedades del enemigo para una serialización sencilla en Json

public class EnemyProperties {

        public Enemy.Type type;

        public String subtype;

        public Vector2 initialPosition;

        public Entity.Direction direction;

        // Para serializar es imprescindible el constructor vacío

        public EnemyProperties() {}

        // Constructor: Recibe un enemigo y obtiene las propiedades principales sobre su estado

        public EnemyProperties(Enemy e) {

            type = e.type;

            subtype = e.subtype;

            initialPosition = e.getInitialPosition();

            direction = e.getCurrentDirection();
        }
    }

