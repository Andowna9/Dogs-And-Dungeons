package com.gdx.dogs_and_dungeons;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.gdx.dogs_and_dungeons.users.SecurePassword;
import com.gdx.dogs_and_dungeons.users.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

// Clase con métodos estáticos para gestionar el acceso a la base de datos y
// definir operaciones frecuentes

public class DBManager {

    private static final String TAG = DBManager.class.getSimpleName();

    // Conexión a la base de datos

    private Connection conn = null;

    // Patrón singleton (solo puede haber un gestor de base de datos)

    private static DBManager instance = null;

    // Constructor del manager restringido a la clase para que no se puedan crear nuevas instancias

    private DBManager() {

        super();
    }

    // Método estático para obtener la instancia asociada

    public static DBManager getInstance() {

        if (instance == null) {

            instance = new DBManager();
        }

        return instance;

    }

    // Método para crear una conexión con la base de datos (se guarda en variable local)

    public void connect(String dbPath) {

        String var = "jdbc:sqlite:" + dbPath;

        try {

            // Creación de conexión de base de datos a partir de jdbc:sqlite

            conn = DriverManager.getConnection(var);

            Gdx.app.log(TAG,"Conexión con la base de datos realizada correctamente");
        }

        catch (SQLException e) {

           Gdx.app.error(TAG, "Error al conectar!", e);
        }


    }


    // Método para desconectarse y eliminar recursos

    public void disconnect() {

        try {

            conn.close();

            Gdx.app.log(TAG, "Conexión cerrada sin problemas");

            }

        catch (SQLException e) {

            Gdx.app.error(TAG, "No se ha podido cerrar la conexión", e);
        }
    }

    // Método para crear la tabla de usuarios

    public void createUserTable() {

        try (Statement st = conn.createStatement()) {

            st.executeUpdate("CREATE TABLE IF NOT EXISTS User (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "Name TEXT," +
                    "Surname TEXT," +
                    "Nickname TEXT NOT NULL UNIQUE," +
                    "hash TEXT NOT NULL," +
                    "Salt TEXT NOT NULL)");
        }

        catch (SQLException e) {

            Gdx.app.error(TAG, "Problema al crear la tabla user", e);
        }
    }

    // Vista con aquellos datos de lectura que se podrán mostrar en las ventanas con el objetivo separarlos de los ocultos (contraseña, salt ...)

    public void createUserView() {

        try(Statement st = conn.createStatement()) {

            st.executeUpdate("CREATE VIEW IF NOT EXISTS UserView AS SELECT id, name, surname, nickname FROM User");
        }

        catch (SQLException e) {

            Gdx.app.error(TAG, "No se ha podido crear la vista para la tabla Usuario!",e);
        }
    }

    // Método para insertar un usuario en la tabla Usuario de la base de datos

    public void storeUser(User user, String pass) {

        try (PreparedStatement pst = conn.prepareStatement("INSERT INTO User (name, surname, nickname, hash, salt) VALUES (?, ?, ?, ?, ?)")) {

            try(Statement stforId = conn.createStatement()) {

                pst.setString(1, user.getName());

                pst.setString(2, user.getSurname());

                pst.setString(3, user.getNickname());

                byte [] salt = SecurePassword.getSalt();

                String hashedPassword = SecurePassword.getHashedPassword(pass, salt);

                pst.setString(4, hashedPassword);

                pst.setString(5, SecurePassword.getEncodedSalt(salt));

                pst.executeUpdate();

                // Se obtiene el id automático que se genera al insertar dicho usuario

                ResultSet rs = stforId.executeQuery("SELECT last_insert_rowid() AS id FROM User");

                if (rs.next()) {

                    int id = rs.getInt("id");

                    user.setId(id);

                }

                Gdx.app.log(TAG,String.format("El id del del usuario %s es: %d",user.getNickname(),user.getId()));

            }
            catch (SQLException e) {

                Gdx.app.error(TAG, "No se pudo obtener el id del usuario al introducirlo en la base de datos!", e);
            }
        }

        catch (SQLException e) {

            Gdx.app.error(TAG, "El usuario " + user.toString() + " no se ha podido guardar", e);
        }
    }

    public void deleteUser(User user) {

        try(PreparedStatement pst = conn.prepareStatement("DELETE FROM User WHERE id=?")) {

            pst.setInt(1,user.getId());

            pst.executeUpdate();

            Gdx.app.log(TAG, String.format("El usuario %s con id %d ha sido borrado con éxito",user.getNickname(),user.getId()));

        }

        catch (SQLException e) {

            Gdx.app.error(TAG, "Error al borrar el usuario " + user);

        }

    }

    public List<User> getAllUsers() {

        List<User> users = new ArrayList<>();

        try(Statement st = conn.createStatement()) {

            ResultSet rs = st.executeQuery("SELECT id, name, surname, nickname FROM UserView");

            while (rs.next()) {

                User user = new User();

                user.setId(rs.getInt("id"));

                user.setName(rs.getString("Name"));

                user.setSurname(rs.getString("Surname"));

                user.setNickname(rs.getString("Nickname"));

                users.add(user);
            }


        }

        catch (SQLException e) {

            Gdx.app.error(TAG, "Error al obtener lista de usuarios!", e);
        }

        return users;
    }

    public Integer getNumberOfUsers() {

        try (Statement st = conn.createStatement()) {

            ResultSet rs = st.executeQuery("SELECT COUNT(*) FROM UserView");

            if (rs.next()) {

                int numUsers = rs.getInt(1);

                Gdx.app.log(TAG,"Número de usuarios: " + numUsers);

                return numUsers;

            }

            Gdx.app.error(TAG, "El resulset no contiene el número de usuarios");

        }

        catch (SQLException e) {

            Gdx.app.error(TAG, "No se ha obtenido el número de usuarios registrados",e);
        }

        return null;
    }

    // Método genérico para eliminar una tabla específica

    public void dropUserTable() {

        try (Statement st = conn.createStatement()) {

           st.executeUpdate("DROP TABLE IF EXISTS User");

        }

        catch (SQLException e) {

            Gdx.app.error(TAG, "Error al borrar la tabla User");
        }

    }

    public void dropUserView() {

        try (Statement st = conn.createStatement()) {

            st.executeUpdate("DROP VIEW IF EXISTS UserView");

        }

        catch (SQLException e) {

            Gdx.app.error(TAG, "Error al borrar la vista UserView");
        }

}


    // Método para comprobar si la contraseña introducida es correcta comparando con el valor almacenado en la base de datos

    public boolean isPasswordValid(int userId, String userPass) {

        try(PreparedStatement pst = conn.prepareStatement("SELECT hash, salt FROM User WHERE id=?")) {

            pst.setInt(1,userId);

            ResultSet rs = pst.executeQuery();

            if (rs.next()) {

               String storedHash =  rs.getString("hash");

               byte [] storedSalt = SecurePassword.getDecodedSalt(rs.getString("salt"));

               if (SecurePassword.getHashedPassword(userPass, storedSalt).equals(storedHash)) {

                  return true;
              }


            }
        }

        catch (SQLException e) {

            Gdx.app.error(TAG, "Error al validar la contraseña del usuario con id " + userId);

        }

        return false;
    }

    // Para borrar la base de datos basta con eliminar el fichero asociado

    public void deleteDatabase(String dbPath) {

        FileHandle fileHandle = Gdx.files.local(dbPath);

        String fileName = fileHandle.file().getName();

        if (fileHandle.delete()) {

            Gdx.app.log(TAG, "Éxito al borrar la base de datos contenida en: " + fileName);
        }

        else {

            Gdx.app.error(TAG, "No se pudo borrar la base de datos");
        }

    }

}
