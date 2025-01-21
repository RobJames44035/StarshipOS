/*
 * StarshipOS Copyright (c) 2025. R.A. James
 */

class BadStatementInLambda02 {

    interface SAM {
        void m();
    }

    { call(()-> { System.out.println(new NonExistentClass() + ""); }); }

    void call(SAM s) { }
}
