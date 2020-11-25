package com.gdx.dogs_and_dungeons.others;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Scanner;

public class DialogueManager {
    public HashMap<Integer, ArrayList<String>> allDialogs = new HashMap<Integer, ArrayList<String>>();

  /*  public HashMap<Integer, ArrayList<String>> readFile(String name){ //Example readFile(dialogue12.txt)
        File fichero = new File(name);
        ArrayList<String> importedDialog = new ArrayList<String>();
        try {
            Scanner s = new Scanner(fichero);
         /*   for(String content : s){
                importedDialog.add(content);
            } */
        /*    allDialogs.put(null, importedDialog);
       } catch (FileNotFoundException e) {
            System.out.println("Error while importing file" + Arrays.toString(e.getStackTrace()));
        }
        return allDialogs;
    } */
}
