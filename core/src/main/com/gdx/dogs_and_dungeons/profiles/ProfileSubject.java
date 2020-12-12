package com.gdx.dogs_and_dungeons.profiles;


import com.badlogic.gdx.utils.Array;

public class ProfileSubject {

    private Array<ProfileObserver> observers;

    public ProfileSubject() {

        observers = new Array<>();
    }

    public void addObserver(ProfileObserver observer) {

        observers.add(observer);

    }

    // Identity es igual a true porque nos interesa comparar los valores por referencia y no mediante equals
    // teniendo en cuenta que apuntan a instancias de observadores que se están usando en memoria por el juego

    public void removeObserver(ProfileObserver observer) {

        observers.removeValue(observer, true);

    }

    public void removeAllObservers() {


        observers.removeAll(observers,true);
    }

    public void notifyObservers(ProfileManager subject, ProfileObserver.ProfileEvent event) {

        for (ProfileObserver observer: observers) {

            observer.onNotify(subject, event);
        }

    }
}
