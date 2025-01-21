/*
 * StarshipOS Copyright (c) 2025. R.A. James
 */

class BarNeg1a<T> {
    Object object = new BarNeg1a<?>.Inner.InnerMost();
    static class Inner<S> {
        static class InnerMost {
        }
    }
}
