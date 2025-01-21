/*
 * StarshipOS Copyright (c) 2025. R.A. James
 */

public class Private {
    private void m() {}
}

class Bar extends Private {
    @Override
    private void m() {}
}
