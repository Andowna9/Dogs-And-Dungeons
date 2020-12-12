package com.gdx.dogs_and_dungeons.profiles;

// Interfaz que implementará cada clase que tenga datos que serializar, de tal forma que
// el sujeto notifique a los observadores del evento en cuestión

public interface ProfileObserver {

    public enum ProfileEvent {

        LOADING_PROFILE,

        SAVING_PROFILE
    }

    void onNotify(ProfileManager subject, ProfileEvent event);

}
