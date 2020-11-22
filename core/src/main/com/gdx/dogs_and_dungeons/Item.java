package com.gdx.dogs_and_dungeons;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.math.Rectangle;

//Clase Item para gestionar los objetos que el jugador podra conseguir (trozos de madera, pociones de regeneración, etc.)
public class Item {
	
	private String name;	//Nombre del objeto
	private Rectangle box;	//Hitbox del objeto que sera un rectangulo
	private MapObject mapObject;

	public Item (MapObject object) {

	    mapObject = object;

		name = object.getName();

		MapProperties properties = object.getProperties();

		//Cogemos los datos del rectangulo del Item con los metodos de MapProperties

		float width = properties.get("width", Float.class);
        float height = properties.get("height", Float.class);
        float x = properties.get("x", Float.class);
        float y = properties.get("y",Float.class);

        box = new Rectangle(x, y, width, height);
		
	}
	
	//Metodo que devuelve True si la hitbox del jugador y de un objeto Item se solapan 

	public boolean isTriggered(Rectangle playerBox) {

		if(box.overlaps(playerBox)) {

			return true;
		}
		return false;
	}


	public String getName() {

		return name;
	}


	public MapObject getMapObject() {

	    return mapObject;
    }
	
}
