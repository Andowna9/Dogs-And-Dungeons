package com.gdx.dogs_and_dungeons.entities;

import com.gdx.dogs_and_dungeons.entities.enemies.*;
import com.gdx.dogs_and_dungeons.entities.npcs.Dog;
import com.gdx.dogs_and_dungeons.entities.npcs.NPC;
import com.gdx.dogs_and_dungeons.entities.npcs.Villager;
import com.gdx.dogs_and_dungeons.entities.player.Player;

public class EntityFactory {

    // Devuelve una instancia del jugador

    public static Player getPlayer() {

        return new Player(64,64,1.3f,1.3f);
    }

    // Devuelve un enemigo del tipo especificado

    public static Enemy getEnemy(String type) {

        Enemy e = null;

        switch (type) {

            case "Simple":

                e = new SimpleEnemy();

                break;

            case "Balanced":

                 e = new BalancedEnemy();

                 break;


            case "Hard":

                e = new HardEnemy();

                break;

            case "Boss":

                e = new BossEnemy();

                break;
        }

        return e;
    }

    // Devuelve un NPC

    public static NPC getNPC(String type) {

        NPC npc = null;

        switch (type) {

            case "Dog":

                npc = new Dog();

                break;

            case "Villager":

                npc = new Villager();

                break;
        }

        return npc;
    }
}
