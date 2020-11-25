package com.gdx.dogs_and_dungeons;

public class Attribute {
    public enum Type {
        HEALTH, DAMAGE, RESISTENCE, AGILITY
    }

    private Type type;
    private float value;
    private static final String TAG = Attribute.class.getSimpleName();

    public Attribute(Type type, float value) {
        this.type = type;
        this.value = value;
    }


    public float getValue() {
        return value;
    }

    public Type getType() {
        return type;
    }

    public void addToValue(float value) {

        this.value =+ value;

    }
}
