/*
 * StarshipOS Copyright (c) 2019-2025. R.A. James
 */

public record Host(long B, int A, char C) {
    public static String getID() { return "HostBAC/redef/Host.java"; }
    public int m() {
        return 2; // redefined class
    }
    public Host(int A, long B, char C) {
        this(B, A, C);
    }
}
