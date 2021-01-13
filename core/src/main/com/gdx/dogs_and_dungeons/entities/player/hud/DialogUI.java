package com.gdx.dogs_and_dungeons.entities.player.hud;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.RunnableAction;
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

    // Método para dividir el texto hasta
    // Divide el texto en palabras y prueba a ir concatenando hasta que se produce overflow y elimina la última palabra añadida

    private void divideText(String text) {

        String words = "";

        String [] textArray = text.split("\\s");

        boolean isContinuation = false;

        for (int i = 0; i < textArray.length; i++) {

            // Se añade espacio al principio solo si no se trata de la primera palabra

            if (!words.isEmpty()) {

                words += " ";
            }

            // Y si se trata de la primera palabra (sindo una continuación)

            else if (isContinuation) {

                words += "... ";
            }

            // Se añade la palabra em cuestión

            words += textArray[i];

            textLabel.setText(words);

            // Si la anchura del label con el texto hasta ahora supera la del diálogo (menos un margen en píxeles)

            if (textLabel.getPrefWidth() > dialogWidth - 60) {

                // Se modifica el texto actual quitando la última palabra y espacio (+1)

                words = words.substring(0, words.length() - (textArray[i].length() + 1));

                // Además si no acaba en punto, exclamación o interrogación, se ponen puntos suspensivos

                if (!words.matches("[.!?]$")) {

                    words += " ...";

                    isContinuation = true;
                }

                else isContinuation = false;

                // Se añade el texto a la cola

                dividedText.addLast(words);

                // Se vacía el texto y se resta 1 al índice para seguir la iteración desde esta palabra

                words = "";

                i--;
            }
        }

        // Añadimos el último fragmento, ya que es aquel que no se ha salido del diálogo

        dividedText.addLast(words);
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
