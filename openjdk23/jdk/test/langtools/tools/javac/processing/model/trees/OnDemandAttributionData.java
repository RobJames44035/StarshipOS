/*
 * StarshipOS Copyright (c) 2014-2025. R.A. James
 */

public class OnDemandAttributionData {

    public OnDemandAttributionData(int i) {
        class Local { }
        new Aux(Gen.C) { };
    }

    class Aux {
        Aux(String str) {}
        Aux(int i) {}
        Aux(long l) {}
    }

    private void methodAttributionTest() {
        System.err.println("");
    }

    enum E {
        A;

        final int i;

        E() {
            i = Integer.parseInt("1");
        }

    }

    enum E2 {
        A;

        {
            int i = Integer.parseInt("1");
        }
    }
}
