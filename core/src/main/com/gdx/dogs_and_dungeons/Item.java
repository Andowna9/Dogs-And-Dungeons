package com.gdx.dogs_and_dungeons;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.objects.TextureMapObject;
import com.badlogic.gdx.math.Rectangle;
import com.gdx.dogs_and_dungeons.entities.player.hud.StatusUI;
import com.gdx.dogs_and_dungeons.managers.ParticleEffectsManager;
import com.gdx.dogs_and_dungeons.managers.SpriteManager;

import java.util.HashMap;

//Clase Item para gestionar los objetos que el jugador podra conseguir (trozos de madera, pociones de regeneración, etc.)
public class Item {

	private static final String TAG = Item.class.getSimpleName();

	public enum Type {

		WOOD, APPLE, ATTACK_BOOK, SPEED_BOOK, UNDEFINED
	}

	// Referencia a mapObject, tipo de objeto y caja de colisiones

	private Rectangle box;	//Hitbox del objeto que sera un rectangulo
	private MapObject mapObject; //Objeto del mapa asociado
	private Type itemType;

	// HashMap que contiene las texturas correspondientes a objetos cargados directamente del mapa

	private static HashMap<Type, TextureRegion> textures = new HashMap<>();

	private Item() {}

	// Constructor de objeto en memoria
	public Item (float x, float y, Type type) {

		itemType = type;

		// Creación de TextureMapObject asociado

		TextureMapObject textureMapObject = new TextureMapObject();

		textureMapObject.setTextureRegion(textures.get(type));

		// Posicionamiento en mapa

		x /= MapManager.UNIT_SCALE;

		y /= MapManager.UNIT_SCALE;

		textureMapObject.setX(x);

		textureMapObject.setY(y);

		// Asignación de MapObject

		mapObject = textureMapObject;

		// Creación de caja de colisiones

		box = new Rectangle(x, y, 32, 32);
	}

	// Método para obtener un objeto a partir del mapa

	public static Item loadItemFromMap(MapObject object) {

		Item item = new Item();

		item.mapObject = object;

		MapProperties properties = object.getProperties();

		//Cogemos los datos del rectangulo del Item con los metodos de MapProperties

		float width = properties.get("width", Float.class);
		float height = properties.get("height", Float.class);
		float x = properties.get("x", Float.class);
		float y = properties.get("y",Float.class);
		String type = properties.get("type","",String.class);

		item.box = new Rectangle(x, y, width, height);

		try {

			item.itemType = Type.valueOf(type.toUpperCase());
			textures.put(item.itemType, ((TextureMapObject) item.mapObject).getTextureRegion());
		}

		catch (IllegalArgumentException e) {

			Gdx.app.error(TAG, "El tipo de objeto " + type + " no existe!", e);

			item.itemType = Type.UNDEFINED;
		}

		return item;

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

				SpriteManager.audioManager.playSound("picking_wood");

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

				SpriteManager.audioManager.playSound("enchanting");

				break;

			// Aumenta la velocidad del jugador temporalmente

			case SPEED_BOOK:

				SpriteManager.player.increaseSpeed();

				SpriteManager.effectsManager.generateEffect(SpriteManager.player.getCurrentPosition().x + 0.5f,
						SpriteManager.player.getCurrentPosition().y + 0.5f , ParticleEffectsManager.EffectType.SPEED_UPGRADE);

				SpriteManager.audioManager.playSound("speed_up");

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
