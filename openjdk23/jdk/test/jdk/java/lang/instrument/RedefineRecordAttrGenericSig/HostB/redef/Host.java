/*
 * StarshipOS Copyright (c) 2019-2025. R.A. James
 */

public record Host<X>(X a, java.lang.String b) {
    public static String getID() { return "HostB/redef/Host.java"; }
    public int m() {
        return 2; // redefined class
    }
}
