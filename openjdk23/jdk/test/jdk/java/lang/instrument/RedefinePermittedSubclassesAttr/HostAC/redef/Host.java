/*
 * StarshipOS Copyright (c) 2020-2025. R.A. James
 */

public sealed class Host permits ClassOne,ClassThree {
    public static String getID() { return "HostAC/redef/Host.java"; }
    public int m() {
        return 2; // redefined class
    }
}
