package com.gdx.dogs_and_dungeons.others;

import java.io.FileWriter;
import java.util.ArrayList;

public class Dialogue {
    // Each dialogue (differentiated by ID) between two or more players is displayed by
    // displaying an ArrayList that saves Strings by order

    protected int dialID;
    protected ArrayList<String> dialogueContent= new ArrayList<String>;

    //Getters and setters
    public int getDialID() {
        return dialID;
    }

    public void setDialID(int dialID) {
        this.dialID = dialID;
    }

    public ArrayList<String> getDialogueContent() {
        return dialogueContent;
    }

    public void setDialogueContent(ArrayList<String> dialogueContent) {
        this.dialogueContent = dialogueContent;
    }


    // Method that allows to use a recycled dialogue
    public void useAnotherDialogue(ArrayList<String> i){
        dialogueContent = new ArrayList<String>(i);
    }
    public void add(String e){
        dialogueContent.add(e);
    }

    // Method that allows to use a recycled dialogue
    public useAnotherDialogue(ArrayList<String> i){
        dialogueContent = new ArrayList<String>(i);
        return dialogueContent;
    }

    //Show dialogue on screen
    public show(){
        for (int i = 0; i < dialogueContent.size(); i++){
            //show on screen code here
            // if keyboard is pressed i = i+1 to display following string
        }
    }


    public void writeFile(String name){  // Trying to write file to Dialogues folder in assets
        try {
            FileWriter fichero = new FileWriter(name + this.getDialID() + ".txt")
            fichero.write(this.getDialID(), this.getDialogueContent());
        fichero.close();
        }
        catch (Exception e){
            System.out.println("Error de escritura:" + e.getMessage());
            System.out.println("Error de escritura:" + e.getStackTrace());

        }
    }

}
