/*
 * StarshipOS Copyright (c) 2025. R.A. James
 */
class LambdaMutateFinalField {
    final String x;
    LambdaMutateFinalField() {
        Runnable r1 = () -> x = "not ok";
        this.x = "ok";
    }
}
