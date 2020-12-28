package com.gdx.dogs_and_dungeons.managers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.files.FileHandle;

import java.util.HashMap;

public class AudioManager {

    // Mapas que contienen el audio con su nombre como clave

    private static final String SOUNDS_PATH = "audio/sounds/";

    private HashMap<String, Sound> sounds;

    private static final String MUSIC_PATH = "audio/music/";

    private HashMap<String, Music> music;

    public AudioManager() {

        sounds = new HashMap<>();

        music = new HashMap<>();

        // Sounds

        loadSound("daggerSlice.ogg");

        loadSound("drawDagger.ogg");

    }

    private void loadSound(String file) {

        FileHandle soundFile = Gdx.files.internal(SOUNDS_PATH + file);

        if (soundFile.exists()) {

            sounds.put(soundFile.nameWithoutExtension(), Gdx.audio.newSound(soundFile));
        }


    }

    private void loadMusic(String file) {

        FileHandle musicFile = Gdx.files.internal(MUSIC_PATH + file);

        if (musicFile.exists()) {

            music.put(musicFile.nameWithoutExtension(), Gdx.audio.newMusic(musicFile));

        }

    }

    // Método para reproducir un sonido

    public void playSound(String name) {

        Sound s = sounds.get(name);

        if (s != null) {

            s.play();
        }
    }

}
