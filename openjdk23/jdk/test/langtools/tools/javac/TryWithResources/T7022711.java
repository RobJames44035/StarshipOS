/*
 * StarshipOS Copyright (c) 2025. R.A. James
 */

import java.io.*;

class T7022711 {
    public static void meth() {
        // declared resource
        try (DataInputStream is = new DataInputStream(new FileInputStream("x"))) {
            while (true) {
                is.getChar();  // method not found
            }
        } catch (EOFException e) {
        }

        // resource as variable
        DataInputStream is = new DataInputStream(new FileInputStream("x"));
        try (is) {
            while (true) {
                is.getChar();  // method not found
            }
        } catch (EOFException e) {
        }
    }
}

