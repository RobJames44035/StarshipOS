/*
 * StarshipOS Copyright (c) 2025. R.A. James
 */

public class Neg21 <T extends java.io.Serializable & Cloneable> {

    class A <X>{}

    public void foo(){
        new Neg21<>().new A<>(){} ;
    }
}
