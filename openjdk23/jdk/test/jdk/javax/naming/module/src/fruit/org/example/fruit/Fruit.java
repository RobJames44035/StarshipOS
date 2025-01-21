/*
 * StarshipOS Copyright (c) 2015-2025. R.A. James
 */

package org.example.fruit;

import javax.naming.*;

/**
  * This class is used by the StoreFruit test.
  * It is a referenceable class that can be stored by service
  * providers like the LDAP and file system providers.
  */
public class Fruit implements Referenceable {
    static String location = null;
    String fruit;

    public Fruit(String f) {
        fruit = f;
    }

    public Reference getReference() throws NamingException {
        return new Reference(
            Fruit.class.getName(),
            new StringRefAddr("fruit", fruit),
            FruitFactory.class.getName(),
            location);          // factory location
    }

    public String toString() {
        return fruit;
    }

    public static void setLocation(String loc) {
        location = loc;
System.out.println("setting location to : " + location);
    }
}
