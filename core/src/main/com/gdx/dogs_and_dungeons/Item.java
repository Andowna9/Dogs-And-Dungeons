package com.gdx.dogs_and_dungeons;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.math.Rectangle;
import com.gdx.dogs_and_dungeons.entities.player.hud.StatusUI;
import com.gdx.dogs_and_dungeons.managers.ParticleEffectsManager;
import com.gdx.dogs_and_dungeons.managers.SpriteManager;

//Clase Item para gestionar los objetos que el jugador podra conseguir (trozos de madera, pociones de regeneración, etc.)
public class Item {

	private static final String TAG = Item.class.getSimpleName();

	public enum Type {

		WOOD, APPLE, ATTACK_BOOK, SPEED_BOOK, UNDEFINED
	}

	private Rectangle box;	//Hitbox del objeto que sera un rectangulo
	private MapObject mapObject; //Objeto del mapa asociado
	private Type itemType;

	public Item (MapObject object) {

	    mapObject = object;

		MapProperties properties = object.getProperties();

		//Cogemos los datos del rectangulo del Item con los metodos de MapProperties

		float width = properties.get("width", Float.class);
        float height = properties.get("height", Float.class);
        float x = properties.get("x", Float.class);
        float y = properties.get("y",Float.class);
        String type = properties.get("type","",String.class);

        box = new Rectangle(x, y, width, height);

        try {

			itemType = Type.valueOf(type.toUpperCase());

		}

        catch (IllegalArgumentException e) {

			Gdx.app.error(TAG, "El tipo de objeto " + type + " no existe!", e);

			itemType = Type.UNDEFINED;
		}
		
	}
	
	// Método que devuelve True si la hitbox del jugador y de un objeto Item se solapan

	public boolean isTriggered(Rectangle playerBox) {

		return box.overlaps(playerBox);
	}

	// Método al que se llama cada vez que un objeto es tocado

	public void collect() {

		switch (itemType) {

			// Se incrementa el contador de madera

			case WOOD:

				StatusUI.incrementLogs();

				break;

			// Las manzanas suman 1 de vida al jugador

			case APPLE:

				SpriteManager.player.addHealth(1);

				break;

			// Aumenta el ataque del jugador temporalmente

			case ATTACK_BOOK:

				SpriteManager.player.increaseDamage();

				SpriteManager.effectsManager.generateEffect(SpriteManager.player.getCurrentPosition().x + 0.5f,
						SpriteManager.player.getCurrentPosition().y + 0.5f , ParticleEffectsManager.EffectType.ATTACK_UPGRADE);

				break;

			// Aumenta la velocidad del jugador temporalmente

			case SPEED_BOOK:

				SpriteManager.player.increaseSpeed();

				SpriteManager.effectsManager.generateEffect(SpriteManager.player.getCurrentPosition().x + 0.5f,
						SpriteManager.player.getCurrentPosition().y + 0.5f , ParticleEffectsManager.EffectType.SPEED_UPGRADE);

				break;
		}

	}

	public Type getType() {

		return itemType;
	}


	public MapObject getMapObject() {

	    return mapObject;
    }
	
}
