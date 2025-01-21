/*
 * StarshipOS Copyright (c) 2018-2025. R.A. James
 */

/**
 * @test
 * @bug 8130264 8214552 8214558
 * @summary verify the PrinterJob implementation class name is not
 *          polluting system properties
 */

public class CheckPrinterJobSystemProperty {

     public static void main(String[] args) {
         String pjProp = System.getProperty("java.awt.printerjob");
         if (pjProp != null) {
             throw new RuntimeException("pjProp = " + pjProp);
         }
     }
}
