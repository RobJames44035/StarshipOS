/*
 * StarshipOS Copyright (c) 2020-2025. R.A. James
 */

package com.bars;
public class Main {
    public static void main(String... args) {
        try {
             System.out.println("Main.class from " + Main.class.getModule());
             Class.forName("com.foos.Test");
             System.out.println("com.foos.Test found!");
        } catch (ClassNotFoundException e) {
             System.out.println("ClassNotFoundException " + e);
        }
    }
}
