/*
 * StarshipOS Copyright (c) 2025. R.A. James
 */

public class ResDeclOutsideTry implements AutoCloseable {
    ResDeclOutsideTry tr1;
    ResDeclOutsideTry tr2 = new ResDeclOutsideTry();

    String test1() {
        try (tr1 = new ResDeclOutsideTry(); tr2;) {
        }
        return null;
    }

    @Override
    public void close() throws Exception {
    }
}

