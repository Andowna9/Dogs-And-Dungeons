package com.gdx.dogs_and_dungeons.users;

public class User implements Comparable<User> {

    private int id = 0;

    private String name;

    private String surname;

    private String nickname;

    public User() {}

    public User(String name, String surname, String nickname) {

        this.name = name;

        this.surname = surname;

        this.nickname = nickname;
    }

    public User(String nickname) {

        this.nickname = nickname;

        this.name = "";

        this.surname = "";
    }

    public String getNickname() {

        return nickname;
    }

    public String getSurname() {

        return surname;
    }

    public String getName() {

        return name;
    }


    public int getId() {

        return id;
    }

    public void setId(int newId) {

        // El id solo se podrá modificar la primera vez (cuando su valor es 0 - valor por defecto)

        if (id == 0) {

            id = newId;
        }

    }

    public void setName(String newName) {

        name = newName;
    }

    public void setSurname(String newSurname) {

        surname = newSurname;
    }

    public  void setNickname(String newNickname) {

        nickname = newNickname;
    }


    @Override
    public String toString() {

        if (name.isEmpty() || surname.isEmpty()) {

            return nickname;
        }

        return String.format("%s %s (alias %s)",nickname,name,surname);
    }

    // Dos usuarios son iguales si tienen el mismo id

    @Override
    public boolean equals(Object o) {

        if (!(o instanceof User)) {

            return false;
        }

        User u = (User) o;

        return nickname.equals(u.nickname);
    }

    // Orden natural de los usuarios que se utilizará para ordenarlos antes de mostrarlos en pantalla

    @Override
    public int compareTo(User o) {

        return this.nickname.compareTo(o.nickname);
    }
}
