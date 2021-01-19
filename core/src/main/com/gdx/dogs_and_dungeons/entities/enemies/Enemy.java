package com.gdx.dogs_and_dungeons.entities.enemies;

import com.gdx.dogs_and_dungeons.entities.Entity;

// Clase abstracta, no queremos que sea instanciada

public abstract class Enemy extends Entity {

    private static final String generalPath = "enemy/";

    // Enumeración para instanciar un tipo de enemigo

    public enum Type {

        SIMPLE, BALANCED, BOSS
    }

    // Número de items que va a soltar cuando muera

    protected int dropCount;

    // Tipo del enemigo

    protected Type type;

    // Subtipo del enemigo

    protected String subtype;

    // Método que determinará el comportamiento o lógica del enemigo

    public abstract void behave(float delta);

    // Constructor de enemigos con tamaños de sprites/dibujado por defecto

    public Enemy(String specificPath) {

        super(32,32,1f,1f);

        subtype = specificPath;

        animManager.loadDirectionalAnimations(generalPath + specificPath + ".png",State.WALKING);

    }

    protected void setDropCount(int count) {

        dropCount = count;
    }

    public int getDropCount() {

        return dropCount;
    }

    // Devuelve un objeto simple y fácil de serializar con atributos básicos

    public EnemyProperties getProperties() {

        return new EnemyProperties(this);
    }


}
