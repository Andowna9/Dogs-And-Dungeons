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

        // A continuación creamos la tabla usuario y contraeña

        dbManager.createUserTable();

        dbManager.createUserView();

    }

    @After
    public void endConnection() {

        // Antes de finalizar los tests, cerramos la conexión creada

        dbManager.disconnect();
    }

    @Test
    public void testStoreUser() {

        // Usuario que ha proporcionado todos los datos

        dbManager.storeUser(new User("Carlos","García","carlitos"),"abc");

        // Usuario que no ha proporcionado (nombre y apellido reales)

        dbManager.storeUser(new User("asier09"),"1234");

        // Comprobamos que el número de usuarios introducidos es el esperado

        assertEquals(2,(long) dbManager.getNumberOfUsers());


    }

    @Test
    public void testDeleteUser() {

        User user1 = new User("Juan2t");

        User user2 = new User("KK");

        // Al guardar los usuarios, se les asignará un id en la base de datos

        dbManager.storeUser(user1, "ajgs");

        dbManager.storeUser(user2, "71267gh");

        assertEquals(2,(long) dbManager.getNumberOfUsers());

        // Borramos al primer usuario

        dbManager.deleteUser(user1);

        assertEquals(1,(long) dbManager.getNumberOfUsers());

        // Borramos al segundo usuario

        dbManager.deleteUser(user2);

        assertEquals(0, (long) dbManager.getNumberOfUsers());
    }

    @Test
    public void testGetAllUsers() {

        dbManager.storeUser(new User("Victor40"), "kkañl");

        dbManager.storeUser(new User("LucasPL"),"1653165");

        dbManager.storeUser(new User("Mike977"),"iaji90");

        assertEquals(3, (long) dbManager.getNumberOfUsers());

        List<User> users = dbManager.getAllUsers();

        List<User> expected = Arrays.asList(
                new User("Victor40"),
                new User("LucasPL"),
                new User("Mike977"));

        assertEquals(expected,users);

    }

    @Test
    public void testIsPassworValid() {

        User user1 = new User("Juan80");

        dbManager.storeUser(user1,"gg091");

        assertFalse(dbManager.isPasswordValid(user1.getId(), "jqhkja"));

        assertFalse(dbManager.isPasswordValid(user1.getId(), "aksljdlk"));

        assertFalse(dbManager.isPasswordValid(user1.getId(), "a9diad"));

        assertTrue(dbManager.isPasswordValid(user1.getId(), "gg091"));

    }


    @Test
    public void testDropUserTable() {

        dbManager.dropUserTable();

    }

    @Test

    public void testDropUserView() {

        dbManager.dropUserView();
    }

}
