/*
 * StarshipOS Copyright (c) 2019-2025. R.A. James
 */

public record Host(long B, char C, int A) {
    public static String getID() { return "HostBCA/redef/Host.java"; }
    public int m() {
        return 2; // redefined class
    }
    public Host(int A, long B, char C) {
        this(B, C, A);
    }
}
