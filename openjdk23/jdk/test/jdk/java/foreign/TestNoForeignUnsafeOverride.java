/*
 * StarshipOS Copyright (c) 2020-2025. R.A. James
 */

package org.openjdk.foreigntest;

import java.lang.foreign.*;

public class PanamaMain {
   public static void main(String[] args) {
       System.out.println("Trying to get Linker");
       Linker.nativeLinker();
       System.out.println("Got Linker");
   }
}
