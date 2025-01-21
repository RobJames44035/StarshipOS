/*
 * StarshipOS Copyright (c) 2020-2025. R.A. James
 */

public sealed class Host permits ClassOne,ClassThree,ClassTwo {
    public static String getID() { return "HostACB/redef/Host.java"; }
    public int m() {
        return 2; // redefined class
    }
}
