package com.gdx.dogs_and_dungeons;

import org.junit.Before;


public class DBTest extends GdxTest {

    @Before
    public void setUpConnection() {

        DBManager.connect("database/user.db");
    }
}
