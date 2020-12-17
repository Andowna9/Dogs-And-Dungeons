package com.gdx.dogs_and_dungeons.users;

import com.gdx.dogs_and_dungeons.GdxTest;
import org.junit.Test;
import static org.junit.Assert.*;

public class SecurePasswordTest extends GdxTest {

    @Test
    public void testHashing() {

        String pass1 = SecurePassword.getHashedPassword("abc", SecurePassword.getSalt());

        String pass2 = SecurePassword.getHashedPassword("abc", SecurePassword.getSalt());

        // Los hashes de las contraseñas no serán iguales porque se utiliza una sal distinta (generada aleatoriamente)

        assertNotEquals(pass1,pass2);

        // Si conocemos la sal, podremos generar el mismo hash a partir del mismo string

        byte [] salt1 = SecurePassword.getSalt();

        String pass3 = SecurePassword.getHashedPassword("kl90",salt1);

        String pass4 = SecurePassword.getHashedPassword("kl90",salt1);

        assertEquals(pass3, pass4);

    }

    @Test
    public void testEncoding() {

        byte [] salt = SecurePassword.getSalt();

        // Codificación a String

        String encoded = SecurePassword.getEncodedSalt(salt);

        // Decodificación a array de bytes

        byte [] decoded = SecurePassword.getDecodedSalt(encoded);

        // Comprobamos que los arrays de bytes coinciden

        assertArrayEquals(salt, decoded);


    }
}
