package com.gdx.dogs_and_dungeons.entities;

import com.gdx.dogs_and_dungeons.entities.enemies.*;
import com.gdx.dogs_and_dungeons.entities.npcs.Cat;
import com.gdx.dogs_and_dungeons.entities.npcs.Dog;
import com.gdx.dogs_and_dungeons.entities.npcs.NPC;
import com.gdx.dogs_and_dungeons.entities.npcs.Villager;
import com.gdx.dogs_and_dungeons.entities.player.Player;

public class EntityFactory {

    // Devuelve una instancia del jugador

    public static Player getPlayer() {

        return new Player(64,64,1.3f,1.3f);
    }

    public static Entity getEntity(String entity, String type, String subtype) {

        if (entity.equalsIgnoreCase("Enemy")) {

            return getEnemy(Enemy.Type.valueOf(type.toUpperCase()),subtype);

        }

        else if (entity.equalsIgnoreCase("NPC")) {

            return getNPC(NPC.Type.valueOf(type.toUpperCase()),subtype);
        }

        return null;
    }

    // Devuelve un enemigo del tipo especificado

    public static Enemy getEnemy(Enemy.Type type, String subType) {

        Enemy e = null;

        switch (type) {

            case SIMPLE:

                e = new SimpleEnemy(subType);

                break;

            case BALANCED:

                 e = new BalancedEnemy(subType);

                 break;


            case BOSS:

                e = new BossEnemy(subType);

                break;
        }

        return e;
    }

    // Devuelve un NPC

    public static NPC getNPC(NPC.Type type, String subtype) {

        NPC npc = null;

        switch (type) {

            case DOG:

                npc = new Dog();

                break;

            case CAT:

                npc = new Cat(subtype);

                break;

            case VILLAGER:

                npc = new Villager(subtype);

                break;
        }

        return npc;
    }
}
