/*
 * StarshipOS Copyright (c) 2020-2025. R.A. James
 */

import java.util.HashMap;
import jdk.test.whitebox.WhiteBox;

public class UsedAllArchivedLambdasApp {
    public static boolean isRuntime;
    public static int NUM_CLASSES = 3;
    public static WhiteBox wb = WhiteBox.getWhiteBox();
    public static HashMap<Class<?>, Class<?>> inArchiveMap = new HashMap<>();
    public static HashMap<Class<?>, Class<?>> notInArchiveMap = new HashMap<>();

    public static void main(String args[]) {
        isRuntime = (args.length == 1 && args[0].equals("run")) ? true : false;
        {Runnable run1 = UsedAllArchivedLambdasApp::myrun; run1.run();}
        {Runnable run1 = UsedAllArchivedLambdasApp::myrun; run1.run();}
        {Runnable run1 = UsedAllArchivedLambdasApp::myrun; run1.run();}
        if (isRuntime) {
            {Runnable run1 = UsedAllArchivedLambdasApp::myrun; run1.run();}
            {Runnable run1 = UsedAllArchivedLambdasApp::myrun; run1.run();}
            {Runnable run1 = UsedAllArchivedLambdasApp::myrun; run1.run();}
        }

        int mapSize = 0;

        if (isRuntime) {
            mapSize = inArchiveMap.size();
            System.out.println("Number of lambda classes in archive: " + mapSize);
            if (mapSize != NUM_CLASSES) {
                throw new RuntimeException("Expected number of lambda classes in archive is " +
                    NUM_CLASSES + " but got " + mapSize);
            }
            mapSize = notInArchiveMap.size();
            System.out.println("Number of lambda classes in archive: " + mapSize);
            if (mapSize != NUM_CLASSES) {
                throw new RuntimeException("Expected number of lambda classes NOT in archive is " +
                    NUM_CLASSES + " but got " + mapSize);
            }
        }
    }

    static void myrun() {
        Class<?> c = LambdaVerification.getCallerClass(1);
        if (isRuntime) {
            if (wb.isSharedClass(c)) {
                System.out.println(c.getName() + " is a shared class");
                inArchiveMap.put(c,c);
            } else {
                System.out.println(c.getName() + " is NOT a shared class");
                notInArchiveMap.put(c,c);
            }
        }
    }
}
