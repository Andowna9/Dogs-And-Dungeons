package com.gdx.dogs_and_dungeons;

import com.badlogic.gdx.Gdx;
import java.sql.SQLException;
import java.sql.Connection;
import java.sql.DriverManager;

// Clase con métodos estáticos para gestionar el acceso a la base de datos y
// definir operaciones frecuentes

public class DBManager {

    private static final String TAG = DBManager.class.getSimpleName();


    public static void connect() {

        String var = "jdbc:sqlite:" + Gdx.files.internal("core/database") + "/prueba.bd";

        try {

            // Creación de conexión de base de datos a partir de jdbc:sqlite

            Connection conn = DriverManager.getConnection(var);
        }

        catch (SQLException e) {

           Gdx.app.error(TAG, "Error al conectar con la base de datos!", e);
        }


    }
}
