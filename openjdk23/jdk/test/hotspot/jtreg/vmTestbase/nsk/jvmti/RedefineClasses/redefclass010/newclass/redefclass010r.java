/*
 * StarshipOS Copyright (c) 2003-2025. R.A. James
 */

// THIS TEST IS LINE NUMBER SENSITIVE

package nsk.jvmti.RedefineClasses;

import java.io.PrintStream;

/**
 * This is the new version of a redefined class with some empty
 * commented out lines
 */
public class redefclass010r {
// dummy constructor
// fix for 4762721: see 8.8.7 Default Constructor from JLS, 2nd ed.: "If the class is
// declared public, then the default constructor is implicitly given the access modifier public"
    public redefclass010r() {             // redefclass010.c::redf_ln[0][0]
        int constr_i = 2;                 // redefclass010.c::redf_ln[0][1]
        long constr_l = 3487687L;         // redefclass010.c::redf_ln[0][2]
        double constr_d = 4589.34D;       // redefclass010.c::redf_ln[0][3]
        float constr_f = 6708.05F;        // redefclass010.c::redf_ln[0][4]
        char constr_c = 'a';              // redefclass010.c::redf_ln[0][5]
        String constr_s = "Dummy string"; // redefclass010.c::redf_ln[0][6]

        return;                           // redefclass010.c::redf_ln[0][7]
    }

    public int checkIt(PrintStream out, boolean DEBUG_MODE) {
//
        if (DEBUG_MODE)                                              // redefclass010.c::redf_ln[1][0]
//
            out.println("NEW redefclass010r: inside the checkIt()"); // redefclass010.c::redf_ln[1][1]
//
        return 73;                                                   // redefclass010.c::redf_ln[1][2]
    }

// dummy methods are below
    static double statMethod(int stat_x, int stat_y, int stat_z) {
        return 19.73D;                // redefclass010.c::redf_ln[3][0]
    }

    final void finMethod(char fin_c, long fin_i, int fin_j, long fin_k) {
        long fin_l = 44444L;          // redefclass010.c::redf_ln[2][0]

        fin_j -= fin_k*(fin_l+fin_i); // redefclass010.c::redf_ln[2][1]
        fin_i = fin_j+fin_k;          // redefclass010.c::redf_ln[2][2]
        if (fin_i == 123456789L)      // redefclass010.c::redf_ln[2][3]
            return;                   // redefclass010.c::redf_ln[2][4]
        float fin_f = fin_i;          // redefclass010.c::redf_ln[2][5]

        return;                       // redefclass010.c::redf_ln[2][6]
    }
}
