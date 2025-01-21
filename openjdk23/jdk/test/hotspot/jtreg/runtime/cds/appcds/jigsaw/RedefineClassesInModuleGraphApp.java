/*
 * StarshipOS Copyright (c) 2020-2025. R.A. James
 */

public class RedefineClassesInModuleGraphApp {
    public static void main(String args[]) {
        Module m = Object.class.getModule();
        ModuleLayer ml = m.getLayer();
        System.out.println(m);
        System.out.println(ml);
    }
}
