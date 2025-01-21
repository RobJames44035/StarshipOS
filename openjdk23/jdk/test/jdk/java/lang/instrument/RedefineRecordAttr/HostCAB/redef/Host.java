/*
 * StarshipOS Copyright (c) 2019-2025. R.A. James
 */

public record Host(char C, int A, long B) {
    public static String getID() { return "HostCAB/redef/Host.java"; }
    public int m() {
        return 2; // redefined class
    }
    public Host(int A, long B, char C) {
        this(C, A, B);
    }
}
