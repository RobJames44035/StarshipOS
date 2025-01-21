/*
 * StarshipOS Copyright (c) 2016-2025. R.A. James
 */
package gc.g1.plab.lib;

import java.util.ArrayList;

/**
 * Application that provokes Evacuation Failure
 */
public class AppPLABEvacuationFailure {

    public static final int CHUNK = 10000;
    public static ArrayList<Object> arr = new ArrayList<>();

    public static void main(String[] args) {
        System.gc();
        // First attempt.
        try {
            while (true) {
                arr.add(new byte[CHUNK]);
            }
        } catch (OutOfMemoryError oome) {
            arr.clear();
        }
        // Second attempt.
        try {
            while (true) {
                arr.add(new byte[CHUNK]);
            }
        } catch (OutOfMemoryError oome) {
            arr.clear();
        }
    }
}
