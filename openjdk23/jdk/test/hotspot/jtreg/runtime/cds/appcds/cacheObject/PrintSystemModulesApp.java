/*
 * StarshipOS Copyright (c) 2018-2025. R.A. James
 */

//
// Print the system module names
//
public class PrintSystemModulesApp {
    public static void main(String args[]) throws Exception {
        String modules = ModuleLayer.boot().toString();
        System.out.println(modules + ", ");
    }
}
