/*
 * StarshipOS Copyright (c) 2025. R.A. James
 */
public class DefiniteAssignment2 {

    public static void meth() {
        int a = 0;
        boolean b = true;
        boolean t;

        {
            int x;

            t = (b && switch(a) {
                case 0: yield (x = 1) == 1 || true;
                default: yield false;
            }) || x == 1;
        }

        {
            int x;

            t = (switch(a) {
                case 0: yield (x = 1) == 1;
                default: yield false;
            }) || x == 1;
        }

        {
            int x;

            t = (switch(a) {
                case 0: x = 1; yield true;
                case 1: yield (x = 1) == 1;
                default: yield false;
            }) || x == 1;
        }

        {
            int x;

            t = (switch(a) {
                case 0: yield true;
                case 1: yield (x = 1) == 1;
                default: yield false;
            }) && x == 1;
        }

        {
            int x;

            t = (switch(a) {
                case 0: yield false;
                case 1: yield isTrue() || (x = 1) == 1;
                default: yield false;
            }) && x == 1;
        }

        {
            int x;

            t = (switch(a) {
                case 0: yield false;
                case 1: yield isTrue() ? true : (x = 1) == 1;
                default: yield false;
            }) && x == 1;
        }

        {
            final int x;

            t = (switch(a) {
                case 0: yield false;
                case 1: yield isTrue() ? true : (x = 1) == 1;
                default: yield false;
            }) && (x = 1) == 1;
        }
    }

    private static boolean isTrue() {
        return true;
    }

}
