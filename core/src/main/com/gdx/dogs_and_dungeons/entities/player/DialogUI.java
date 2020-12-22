package com.gdx.dogs_and_dungeons.entities.player;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.gdx.dogs_and_dungeons.Utility;

public class DialogUI extends Table {

    private static final String TAG = StatusUI.class.getSimpleName();
    private Label.LabelStyle style;
    private String dialog;

    public DialogUI(String dialog){

        setFillParent(true);
        style = new Label.LabelStyle();
        style.font = Utility.gameFont;
        style.fontColor = Color.BLACK;

        bottom();
        add(new Label(dialog, style));


    }

    public String getDialog() {
        return dialog;
    }


}
