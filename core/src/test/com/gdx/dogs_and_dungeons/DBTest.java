package com.gdx.dogs_and_dungeons;

import com.gdx.dogs_and_dungeons.users.User;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;


public class DBTest extends GdxTest {

    private DBManager dbManager;

    @Before
    public void setUpConnection() {

        dbManager = DBManager.getInstance();

        // Al principio de cada test borramos la base de datos para hacer los tests correctamente

        dbManager.deleteDatabase("database/user.db");

        // La conexión, crea la base de datos automáticamente

        dbManager.connect("database/user.db");

        // A continuación creamos la tabla usuario

        dbManager.createUserTable();
    }

    @After
    public void endConnection() {

        // Antes de finalizar los tests, cerramos la conexión creada

        dbManager.disconnect();
    }

    @Test
    public void testStoreUser() {

        System.out.println(dbManager.getNumberOfUsers());

        // Usuario que ha proporcionado todos los datos

        dbManager.storeUser(new User("Carlos","García","carlitos","F6817"));

        // Usuario que no ha proporcionado (nombre y apellido reales)

        dbManager.storeUser(new User("asier09","091IL"));

        // Comprobamos que el número de usuarios introducidos es el esperado

        assertEquals(2,dbManager.getNumberOfUsers());

    }

    @Test
    public void testDeleteUser() {

        User user1 = new User("Juan2t","AHJAH");

        User user2 = new User("KK","8278QH");

        // Al guardar los usuarios, se les asignará un id en la base de datos

        dbManager.storeUser(user1);

        dbManager.storeUser(user2);

        assertEquals(2,dbManager.getNumberOfUsers());

        // Borramos al primer usuario

        dbManager.deleteUser(user1);

        assertEquals(1,dbManager.getNumberOfUsers());

        // Borramos al segundo usuario

        dbManager.deleteUser(user2);

        assertEquals(0, dbManager.getNumberOfUsers());
    }

    @Test
    public void testGetAllUsers() {

        dbManager.storeUser(new User("Victor40","27217G"));

        dbManager.storeUser(new User("LucasPL","928SJ"));

        dbManager.storeUser(new User("Mike977","oqis009"));

        assertEquals(3, dbManager.getNumberOfUsers());

        List<User> users = dbManager.getAllUsers();

        List<User> expected = Arrays.asList(
                new User("Victor40","27217G"),
                new User("LucasPL","928SJ"),
                new User("Mike977","oqis009"));

        assertEquals(expected,users);

    }


    @Test
    public void testDropUserTable() {

        dbManager.dropUserTable();

    }

}
