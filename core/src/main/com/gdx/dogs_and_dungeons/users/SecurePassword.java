package com.gdx.dogs_and_dungeons.users;

// Clase para encriptar contraseñas con un algoritmo de hashing antes de que sea guardada en la base de datos

import com.badlogic.gdx.Gdx;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Arrays;

public class SecurePassword {

    private static final String TAG = SecureRandom.class.getSimpleName();

    // SHA1 utiliza hashes de 160 bits. Cuanto mayor sea este número, más costoso será hallar la contraseña mediande algoritmos de fuerza bruta, pero puede afectar al rendimiento.

    public String getHashedPassword (String password, byte [] salt) {

        String hashedPassword = null;

        try {

            MessageDigest md = MessageDigest.getInstance("SHA1");

            Gdx.app.debug(TAG, "Algoritmo de encriptación usado: " + md.getAlgorithm());

            // Aplicamos la sal

            md.update(salt);

            byte[] bytes = md.digest(password.getBytes());

            // Conversión de array de bytes a String en hexadecimal

            StringBuilder sb = new StringBuilder();

            for (int i = 0; i < bytes.length; i++) {

                sb.append(Integer.toString((bytes[i] & 0xff) + 0x100,16).substring(1));
            }

            hashedPassword = sb.toString();

        } catch (NoSuchAlgorithmException e) {

           Gdx.app.error(TAG, "El algoritmo de seguridad proporcionado no existe! ", e);
        }

        return hashedPassword;
    }

    // Genera una sal (número aleatorio) que se utilizará de forma conjunta con el hash creado a partir de la contraseña para mayor seguridad

    public byte[] getSalt() {

        // El generador utiliza por defecto el algoritmo SHA1PRNG

        SecureRandom sr = new SecureRandom();

        Gdx.app.debug(TAG, "Nombre del algoritmo para generar la sal: " + sr.getAlgorithm());

        byte [] salt = new byte[16];

        sr.nextBytes(salt);

        Gdx.app.debug(TAG,"Sal generada: " + Arrays.toString(salt));

        return salt;

    }


}
