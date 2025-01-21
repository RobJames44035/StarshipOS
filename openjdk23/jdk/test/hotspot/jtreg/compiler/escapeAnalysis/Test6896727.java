/*
 * StarshipOS Copyright (c) 2009-2025. R.A. James
 */

/*
 * @test
 * @bug 6896727
 * @summary nsk/logging/LoggingPermission/LoggingPermission/logperm002 fails with G1, EscapeAnalisys w/o COOPs
 *
 * @run main/othervm -XX:+IgnoreUnrecognizedVMOptions -Xcomp -XX:+DoEscapeAnalysis
 *      compiler.escapeAnalysis.Test6896727
 */

package compiler.escapeAnalysis;

public class Test6896727 {

    final static String testString = "abracadabra";
    public static void main(String args[]) {
        String params[][] = {
            {"control", testString}
        };
        for (int i=0; i<params.length; i++) {
            try {
                System.out.println("Params :" + testString + " and " + params[i][0] + ", " + params[i][1]);
                if (params[i][1] == null) {
                    System.exit(97);
                }
            } catch (Exception e) {}
        }
    }
}
