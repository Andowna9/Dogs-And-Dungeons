package com.gdx.dogs_and_dungeons.managers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.gdx.dogs_and_dungeons.entities.npcs.NPC;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;

public class DialogManager {

    private static final String TAG = DialogManager.class.getSimpleName();

    private HashMap<String,String> dialogs;

    public DialogManager() {

        dialogs = new HashMap<>();

        readDialogsFromFile();
    }

    public void readDialogsFromFile() {

        FileHandle handle = Gdx.files.internal("dialogs/dialogs.txt");

        // Obtenemos un buffer de lectura con tamaño máximo de 1024 caracteres (en principio suficiente)

        try(BufferedReader bf = handle.reader(1024,"UTF-8")) {

            String nextLine;

            while ((nextLine = bf.readLine()) != null) {

                String [] dialog = nextLine.split(":\\s*");

                dialogs.put(dialog[0], dialog[1]);
            }

        } catch (IOException e) {

           Gdx.app.error(TAG, "Error al leer el fichero de díalogos", e);
        }

        // Si la expresión regular por la que se hace split no es correcta de acuerdo con la organización
        // de la información en el fichero, se produce esta excepción

        catch (NullPointerException e) {

            Gdx.app.error(TAG, "La expresión regular para hacer split no es apropiada!", e);
        }


    }

    public  String getDialog(NPC npc) {

        return dialogs.get(npc.getName());
    }


}
