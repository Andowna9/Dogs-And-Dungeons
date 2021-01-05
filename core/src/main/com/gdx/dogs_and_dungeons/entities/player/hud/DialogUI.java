package com.gdx.dogs_and_dungeons.entities.player.hud;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.gdx.dogs_and_dungeons.Utility;

public class DialogUI extends Table {

    private static final String TAG = StatusUI.class.getSimpleName();
    private Label.LabelStyle style;
    private Dialog dialog;

    public DialogUI(String title, String text){

        // La tabla ocupa el espacio que tiene disponible

        setFillParent(true);

        // Creamos el diálogo para gestionar las conversaciones

        dialog = new Dialog(title, Utility.DEFAULT_SKIN);

        // dialog.getTitleLabel().setAlignment(Align.center);

        // Label usado para el texto

        style = new Label.LabelStyle();
        style.font = Utility.gameFont;
        style.fontColor = Color.BLACK;

        Label textLabel = new Label(text, style);

        System.out.println();
        dialog.getContentTable().add(textLabel);


        // El diálogo se muestra desde el inferior a 20 píxeles

        bottom();
        padBottom(20);

        // Se añade a la tabla con un tamaño establecido

        add(dialog).height(150).width(600);


    }



}
