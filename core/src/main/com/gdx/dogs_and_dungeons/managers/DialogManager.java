package com.gdx.dogs_and_dungeons.managers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.gdx.dogs_and_dungeons.entities.npcs.NPC;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;

public class DialogManager {

    private HashMap<String,String> dialogs;

    public DialogManager() {

        dialogs = new HashMap<>();

        readDialogsFromFile();
    }

    public void readDialogsFromFile() {

        FileHandle handle = Gdx.files.internal("dialogs/dialogs.txt");

        try(BufferedReader bf = handle.reader(1024)) {

            String nextLine;

            while ((nextLine = bf.readLine()) != null) {

                String [] dialog = nextLine.split(":\\s*");

                dialogs.put(dialog[0], dialog[1]);
            }

        } catch (IOException e) {

            e.printStackTrace();
        }


    }

    public  String getDialog(NPC npc) {

        return dialogs.get(npc.getName());
    }


}
