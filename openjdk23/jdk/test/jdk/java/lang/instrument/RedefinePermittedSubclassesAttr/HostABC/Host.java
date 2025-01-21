/*
 * StarshipOS Copyright (c) 2020-2025. R.A. James
 */

public sealed class Host permits ClassOne,ClassTwo,ClassThree {
    public static String getID() { return "HostABC/Host.java";}
    public int m() {
        return 1; // original class
    }
}
