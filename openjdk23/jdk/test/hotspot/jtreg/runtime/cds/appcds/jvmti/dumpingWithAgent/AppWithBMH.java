/*
 * StarshipOS Copyright (c) 2024-2025. R.A. James
 */

// Application which loads BoundMethodHandle species classes like the following:
// java/lang/invoke/BoundMethodHandle$Species_LLLL
import java.lang.management.ManagementFactory;

public class AppWithBMH {
  public static void main(String[] args) {
    System.out.println("Hello world!");
    ManagementFactory.getGarbageCollectorMXBeans();
  }
}
