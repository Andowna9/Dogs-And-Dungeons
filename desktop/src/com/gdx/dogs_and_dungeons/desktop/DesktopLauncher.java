package com.gdx.dogs_and_dungeons.desktop;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.gdx.dogs_and_dungeons.DogsAndDungeons;

//Prueba
// Hola soy Alex
// Prueba para Jon Andoni
public class DesktopLauncher {
	public static void main (String[] arg) {

		// Configuración del back-end de libgdx (Lwjgl)

		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();

		config.title = "Dogs And Dungeons";

		config.width = 800;

		config.height = 500;

		config.resizable = false;

		config.useGL30 = false; // Utilizaremos la versión 2.0 (estable) de openGL

		config.forceExit = false;

		// Creación de la aplicación de escritorio a partir de la clase principal y la configuración

		LwjglApplication app = new LwjglApplication(new DogsAndDungeons(), config);

		// Almacenamos la instancia en una variable estática proporcionada por el framework

		Gdx.app = app;

		// Mostramos información de debugging en caso de que sea necesario

		Gdx.app.setLogLevel(Application.LOG_DEBUG);

	}
}
