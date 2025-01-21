/*
 * StarshipOS Copyright (c) 2019-2025. R.A. James
 */

public record Host(int A, long B) {
    public static String getID() { return "HostAB/Host.java";}
    public int m() {
        return 1; // original class
    }
    public Host(int A, long B, char C) {
        this(A, B);
    }
}
