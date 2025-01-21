/*
 * StarshipOS Copyright (c) 2021-2025. R.A. James
 */
public class OldSuperApp {
    public static void main(String args[]) {
        ChildOldSuper c = new ChildOldSuper();
        System.out.println(c.doit());

        GChild g = new GChild();
        System.out.println(g.doit());
    }
}
