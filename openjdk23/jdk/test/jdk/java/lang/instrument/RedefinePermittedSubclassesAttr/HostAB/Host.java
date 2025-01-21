/*
 * StarshipOS Copyright (c) 2020-2025. R.A. James
 */

public sealed class Host permits ClassOne,ClassTwo {
    public static String getID() { return "HostAB/Host.java";}
    public int m() {
        return 1; // original class
    }
}
