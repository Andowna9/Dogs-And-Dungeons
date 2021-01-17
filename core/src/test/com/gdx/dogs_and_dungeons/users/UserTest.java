package com.gdx.dogs_and_dungeons.users;

import org.junit.Test;

import static org.junit.Assert.*;


public class UserTest {

    @Test
    public void testAll(){

        User user1 = new User("Alex", "Nitu", "Alex99");

        assertEquals("Alex", user1.getName());
        assertEquals("Nitu", user1.getSurname());
        assertEquals("Alex99", user1.getNickname());

    }

}
