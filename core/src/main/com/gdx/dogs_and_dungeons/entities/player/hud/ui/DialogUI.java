package com.gdx.dogs_and_dungeons.entities.player.hud.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Queue;
import com.gdx.dogs_and_dungeons.Utility;


public class DialogUI extends Table {

    private static final String TAG = DialogUI.class.getSimpleName();

    // Atributos básicos

    private static final float dialogWidth = 600f;
    private static final float dialogHeight = 150f;

    // Parte visual

    private Label.LabelStyle style;
    private Dialog dialog;
    private Label textLabel;
    private Image pressImage;

    // Parte lógica

    private Queue<String> dividedText;
    private float interval;
    private float time;


    public DialogUI() {

        pressImage = new Image(new Texture(Gdx.files.internal("HUD/press_enter.png")));

        pressImage.setVisible(false);

        // Cola que se utiliza en caso de que el texto no quepa en el diálogo

        dividedText = new Queue<>();

        // La tabla ocupa el espacio que tiene disponible

        setFillParent(true);

        // Creamos el diálogo para gestionar las conversaciones

        dialog = new Dialog("", Utility.DEFAULT_SKIN);

        dialog.setColor(Color.BROWN);

        // dialog.getTitleLabel().setAlignment(Align.center);

        // Label usado para el texto

        style = new Label.LabelStyle();
        style.font = Utility.mainFont;
        style.font.getData().setScale(1.05f);
        style.fontColor = Color.WHITE;

        textLabel = new Label("", style);
        textLabel.setAlignment(Align.center);

        System.out.println();
        dialog.getContentTable().add(textLabel);
        dialog.getContentTable().row();
        dialog.getContentTable().add(pressImage);

        // El diálogo se muestra desde el inferior a 20 píxeles

        bottom();
        padBottom(20);

        // Se añade a la tabla con un tamaño establecido

        add(dialog).height(dialogHeight).width(dialogWidth);

    }

    // Establece el título del diálogo, por ejemplo el nombre del NPC

    public void setTitle(String title) {

        dialog.getTitleLabel().setText(title);
    }

    // Establece el texto que se quiere mostrar

    public void setText(String text) {

        divideText(text);

        textLabel.setText(dividedText.removeFirst());
    }


    // Divide el texto en palabras y prueba a ir concatenando hasta que se produce overflow y lo corrige saltando de línea
    // Por otro lado, añade las frases a la cola para ir mostrándolas en orden posteriormente

    private void divideText(String text) {

        String words = "";

        String [] textArray = text.split("\\s");

        for (int i = 0; i < textArray.length; i++) {

            String nextWord = textArray[i];

            // Se añade espacio al principio solo si no se trata de la primera palabra

            if (!words.isEmpty()) {

                words += " ";
            }

            textLabel.setText(words + nextWord);

            // Si la anchura del label con el texto hasta ahora supera la del diálogo (menos un margen en píxeles)

            if (textLabel.getPrefWidth() > dialogWidth - 60) {

                // Saltamos de línea para evitar que se supere la anchura

                words += "\n";
            }

            // Se añade la palabra em cuestión

            words += nextWord;

            // Si la siguiente palabra tiene un número indefinido de caracteres y acaba en punto, exclamación o interrogación, se añade la frase a la cola

            if (nextWord.matches(".*[!?.]")) {

                dividedText.addLast(words);

                words = "";

            }
        }
    }


    // Actualización del diálogo: Al pulsar ENTER el texto cambia hasta que no queden más elementos en la cola

    public void update(float delta) {

        time += delta;

        if (time >= 0.8f) {

            interval += delta;

            if (interval >= 0.5f) {

                interval = 0;

                pressImage.setVisible(!pressImage.isVisible());
            }

        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER) && time >= 1) {

            pressImage.setVisible(false);

            if (!dividedText.isEmpty()) {

                textLabel.setText(dividedText.removeFirst());

                time = 0;

                interval = 0;

            }

            else {

                setVisible(false);
            }
        }
    }



}
