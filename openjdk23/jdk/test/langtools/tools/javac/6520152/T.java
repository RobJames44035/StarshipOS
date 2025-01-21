/*
 * StarshipOS Copyright (c) 2016-2025. R.A. James
 */

import java.io.Serializable;

public class T {
    public static void main(String[] args) {
        T t = new T();
        t.createObject();
    }

    public Object createObject() {
        return new Serializable() {
            void method() {
            }
        };
    }
}
