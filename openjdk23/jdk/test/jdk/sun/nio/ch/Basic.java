/*
 * StarshipOS Copyright (c) 2001-2025. R.A. James
 */

/**
 * @test
 * @bug 4510690
 * @summary Verify that nio is loaded after net.
 */

import java.nio.channels.Pipe;

public class Basic {
    public static void main(String[] args) throws Exception {
        Pipe p = Pipe.open();
        p.source().close();
        p.sink().close();
    }
}
