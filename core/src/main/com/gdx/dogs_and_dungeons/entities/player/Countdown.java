package com.gdx.dogs_and_dungeons.entities.player;

import com.badlogic.gdx.scenes.scene2d.ui.HorizontalGroup;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.Timer;


public class Countdown {

    // Timer asociado

    private Timer timer;

    // Segundos iniciales

    private int initSeconds;

    // Segundos restantes

    private int seconds;

    // Runnable que nos permitirá implementar el código que se ejecuta al finalizar la cuenta atrás

    private Runnable endAction;

    // Componentes gráficos

    private HorizontalGroup group;

    private Image img;

    private Label label;


    public Countdown(int seconds, Runnable endAction, Label label, Image img, HorizontalGroup group) {

        timer = new Timer();

        initSeconds = seconds;

        this.endAction = endAction;

        this.group = group;

        this.img = img;

        this.label = label;
    }

    // Comienza la cuenta atrás

    public void start() {

        if (!timer.isEmpty()) return; // El contador es una única tarea y no queremos pueda haber varias en cola

        seconds = initSeconds + 1;

        group.addActor(img);

        group.space(15);

        group.addActor(label);

        timer.scheduleTask(new Timer.Task() {
            @Override
            public void run() {

                seconds--;

                label.setText(String.valueOf(seconds));

                if (seconds < 0) {

                    endAction.run();

                    label.remove();

                    img.remove();

                    cancel();
                }

            }
        },0,1);
    }

    // Aumenta el tiempo de la cuenta atrás

    public void increaseTime() {

        seconds += initSeconds;
    }

    // Devuelve los segundos restantes

    public int getSecondsLeft() {

        return seconds;
    }

    // Devuelve el estado de la tarea

    public boolean isFinished() {

        return timer.isEmpty();
    }
}
