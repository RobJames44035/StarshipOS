/*
 * StarshipOS Copyright (c) 2020-2025. R.A. James
 */

public sealed class Host permits ClassThree,ClassOne,ClassTwo {
    public static String getID() { return "HostCAB/redef/Host.java"; }
    public int m() {
        return 2; // redefined class
    }
}
