package com.gdx.dogs_and_dungeons.profiles;

// Patrón singleton (un único punto de acceso)

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.ObjectMap;
import java.util.HashMap;


public class ProfileManager extends ProfileSubject {

    private static final String TAG = ProfileManager.class.getSimpleName();

    private static ProfileManager instance;

    // Perfil por defecto

    private static final String DEFAULT_PROFILE = "default";

    // Sufijo que se utiliza para identificar los ficheros con datos guardados

    private static final String SAVE_SUFFIX = ".sav";

    // Directorio

    private static final String DIR = "saved_profiles/";

    // Clase de utilidad para leer u escribir ficheros en formato json (clave : valor)

    private Json json;

    private HashMap<String, FileHandle> profiles;

    private ObjectMap<String,Object> profileProperties;

    private String currentProfile;

    // Constructor privado para evitar la creación de instancias fuera de la clase

    private ProfileManager() {

        json = new Json();

        profiles = new HashMap<>();

        profileProperties = new ObjectMap<>();

        currentProfile = DEFAULT_PROFILE;

        loadAllProfiles();

    }

    // Devuelve la instancia única asociada a la clase

    public static ProfileManager getInstance() {

        if (instance == null) {

            instance = new ProfileManager();
        }

        return instance;
    }

    // Devuelve la lista con los perfiles de juego guardados para más adelante visualizarlos

    public Array<String> getProfileList() {

        Array<String> profileList = new Array<>();

        for (String profile: profiles.keySet()) {

            profileList.add(profile);
        }

        return profileList;
    }

    public void setProperty(String key, Object value) {

        profileProperties.put(key,value);
    }

    // El tipo es necesario para poder hacer un cast de Object correctamente

    public <T extends Object> T getProperty(String key, Class<T> type, T defaultValue) {

        T property = (T) profileProperties.get(key);

        if (property == null) return defaultValue;

        return property;

    }

    public boolean profileExists(String profile) {

        return profiles.containsKey(profile);
    }

    // Se obtiene la ruta donde se encuentra el fichero asociado al perfil

    public FileHandle getProfileFile(String profile) {

        if (!profileExists(profile)) {

            return null;
        }

        return profiles.get(profile);
    }

    // Método para establecer el perfil actual

    public void setCurrentProfile(String profile) {

        // Se resetea el mapa con las propiedades

        profileProperties.clear();

        currentProfile = profile;
    }

    public void loadAllProfiles() {

        // Si el almacenamiento local está disponible se listan los ficheros acabados en el sufijo de guardado
        // y se añaden al mapa para usarlos más adelante

        if (Gdx.files.isLocalStorageAvailable()) {

            // El método list devuelve las rutas a los hijos de un directorio, por lo que se empieza en la raíz

            FileHandle [] files = Gdx.files.local(DIR).list(SAVE_SUFFIX);

            for (FileHandle file: files) {

                profiles.put(file.nameWithoutExtension(),file);
            }
        }
    }

    // IMPORTANTE: Las rutas internas se utilizan para lectura y las locales para lectura y escritura

    private void writeProfile(String profileName, String fileData, boolean overwrite) {

        // Obtenemos el nombre completo del fichero

        String fullFileName = DIR + profileName + SAVE_SUFFIX;

        // Si el fichero existe y no se quiere modificar, se sale de la función

        if (Gdx.files.internal(fullFileName).exists() && !overwrite) return;

        if (Gdx.files.isLocalStorageAvailable()) {

            FileHandle file = Gdx.files.local(fullFileName);

            // Guardamos el string con los datos (serializados en json)
            // El segundo parámetro (boolean append) especifica si queremos añadir nuevos datos al final o desde 0
            // Por ello, será falso cuando queramos sobrescribir y viceversa (de ahí la negación a overwrite)

            file.writeString(fileData, !overwrite);

            profiles.put(profileName,file);
        }
    }

    // Guarda perfil

    public void saveProfile() {

        notifyObservers(this, ProfileObserver.ProfileEvent.SAVING_PROFILE);

        String text = json.prettyPrint(json.toJson(profileProperties));

        writeProfile(currentProfile,text,true);

        Gdx.app.debug(TAG, "Perfil guardado: " + currentProfile);

    }

    // Carga perfil

    public void loadProfile() {

        String fullProfileName = DIR + currentProfile + SAVE_SUFFIX;

        // El fichero de propiedades no existe

        if (!Gdx.files.internal(fullProfileName).exists()) {

            Gdx.app.log(TAG, String.format("Fichero con perfil %s no encontrado", currentProfile));

        }

        else {

            profileProperties = json.fromJson(ObjectMap.class, profiles.get(currentProfile));

        }

        // Se avisa a los observadores. En caso de que el mapa no tenga las claves correspondientes, se les devolverán valores por defecto

        notifyObservers(this, ProfileObserver.ProfileEvent.LOADING_PROFILE);
    }

    // Método para borrar un perfil

    public void deleteProfile(String profile) {

        // Borrado en memoria de la entrada en el mapa de perfiles

        profiles.remove(profile);

        // Borrado en disco

        String fullProfileName = DIR + profile + SAVE_SUFFIX;

        FileHandle file = Gdx.files.local(fullProfileName);

        if (file.exists()) {

            boolean isDeleted = file.delete();

            if (isDeleted) {

                Gdx.app.debug(TAG, "Se ha borrado el perfil: " + profile);
            }
        }
    }

    public void deleteCurrentProfile() {

        // Se borra el perfil actual si existe

        deleteProfile(currentProfile);
    }








}
