/*
 * StarshipOS Copyright (c) 2013-2025. R.A. James
 */

/**
 * @test
 * @bug 8014023
 * @summary When a method reference to a local class constructor is contained
 *          in a method whose number of parameters matches the number of
 *          constructor parameters compilation fails
 * @compile TreeMakerParamsIsGoofy.java
 * @run main TreeMakerParamsIsGoofy
 */

public class TreeMakerParamsIsGoofy {

    interface III { }

    interface UO {
        III m(III x);
    }

    public static void main(String[] args) {
        class BA implements III {
            BA(III b) {
            }
        }

        ts(BA::new);
    }

    static void ts(UO ba) {
    }
}
