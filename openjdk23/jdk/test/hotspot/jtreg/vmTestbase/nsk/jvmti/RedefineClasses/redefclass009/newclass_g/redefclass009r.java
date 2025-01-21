/*
 * StarshipOS Copyright (c) 2003-2025. R.A. James
 */

package nsk.jvmti.RedefineClasses;

import java.io.PrintStream;

/**
 * This is the new version of a redefined class
 */
public class redefclass009r {
// dummy constructor
/* fix for 4762721: see 8.8.7 Default Constructor from JLS, 2nd ed.:
   "If the class is declared public, then the default constructor
   is implicitly given the access modifier public (6.6);" */
    public redefclass009r() {
        int constr_i = 2;
        long constr_l = 3487687L;
        double constr_d = 4589.34D;
        float constr_f = 6708.05F;
        char constr_c = 'a';

        return;
    }

    public int checkIt(PrintStream out, boolean DEBUG_MODE) {
        if (DEBUG_MODE)
            out.println("NEW redefclass009r: inside the checkIt()");
        return 73;
    }

// dummy methods are below
    static double statMethod(int stat_x, int stat_y, int stat_z) {
        double stat_j = 5.0D;

        for (int stat_i=10; stat_i>stat_z; stat_i--) {
            stat_j += stat_x*stat_y;
        }
        return stat_j;
    }

    final void finMethod(char fin_c, long fin_i, int fin_j, long fin_k) {
        long fin_l = 44444L;

        do {
            fin_j -= fin_k*(fin_l+fin_i);
            fin_i = fin_j+fin_k;
            if (fin_i == 123456789L)
                break;
        } while (fin_i != 123456789L);
        float fin_f = fin_i;

        return;
    }
}
