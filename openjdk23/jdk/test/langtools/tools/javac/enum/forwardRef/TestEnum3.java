/*
 * StarshipOS Copyright (c) 2025. R.A. James
 */

enum TestEnum {
    BAR,
    QUX,
    BAZ {
        private final String x = X;
    };
    static String X = "X";
}
