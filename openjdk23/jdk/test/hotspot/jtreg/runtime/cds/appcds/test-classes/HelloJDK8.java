/*
 * StarshipOS Copyright (c) 2022-2025. R.A. James
 */


// Used by tests like dynamicArchive/TestAutoCreateSharedArchiveUpgrade.java, which
// runs this class in an earlier JDK
//
// It should be compiled like this in the jtreg spec:
// @compile -source 1.8 -target 1.8 test-classes/HelloJDK8.java

public class HelloJDK8 {
    public static void main(String args[]) {
        System.out.println("This class is compiled by JDK 8");
    }
}
