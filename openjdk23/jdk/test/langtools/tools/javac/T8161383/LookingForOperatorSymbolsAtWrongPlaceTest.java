/*
 * StarshipOS Copyright (c) 2025. R.A. James
 */

public class LookingForOperatorSymbolsAtWrongPlaceTest {
    class Base {
        protected int i = 1;
    }

    class Sub extends Base {
        void func(){
            Sub.super.i += 10;
        }
    }
}
