package com.gdx.dogs_and_dungeons.managers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Timer;
import com.gdx.dogs_and_dungeons.DogsAndDungeons;

import java.util.HashMap;

public class AudioManager {

    private static final String TAG = AudioManager.class.getSimpleName();

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

        loadSound("speed_up.ogg");

        loadSound("enchanting.ogg");

        loadSound("magic_sound.ogg");

        loadSound("picking_wood.ogg");


        // Music

        loadMusic("titlescreen.mp3");

        loadMusic("graveyard.mp3");

        loadMusic("ghostzone.mp3");

        loadMusic("victory.mp3");

        loadMusic("gameover.mp3");

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

            long id = s.play();

            // Volumen con rango entre 0 y 1

            s.setVolume(id, DogsAndDungeons.GamePreferences.volume);
        }
    }

    // Método para reproducir música

    public void playMusic(final String name) {

        if (DogsAndDungeons.GamePreferences.musicOn) {

            final Music m = music.get(name);

            if (m != null) {

                // Iniciamos la música en otro hilo para evitar una disminución de fotogramas, ya que esta se carga directamente de disco

                Thread musicThread = new Thread(new Runnable() {
                    @Override
                    public void run() {

                        // Reproducción en bucle por defecto

                        m.setLooping(true);

                        m.play();

                        m.setVolume(DogsAndDungeons.GamePreferences.volume);

                        Gdx.app.debug(TAG, "Música empezada: " + name);

                    }
                });

                musicThread.start();
            }

        }
    }

    // Método para parar la reproducción de música (la próxima vez se iniciará desde el principio)

    public void stopMusic(final String name) {

        final Music m = music.get(name);

        if (m != null) {

            // Fade (suavizado) al desaparecer la música

            Timer t = new Timer();

            t.scheduleTask(new Timer.Task() {
                @Override
                public void run() {

                    if ( m.getVolume() >= 0.01f) {

                        m.setVolume(m.getVolume() - 0.01f);
                    }

                    else {

                        m.stop();

                        Gdx.app.debug(TAG,"Música finalizada: " + name);

                        cancel();
                    }
                }
            },0f,0.01f);

        }
    }

    // Para toda aquella música que se esté reproduciendo

    public void stopAllMusic() {

        for (String music: music.keySet()) {

            stopMusic(music);
        }
    }

}
