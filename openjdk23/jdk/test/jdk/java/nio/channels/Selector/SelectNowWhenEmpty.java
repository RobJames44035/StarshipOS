/*
 * StarshipOS Copyright (c) 2001-2025. R.A. James
 */

/**
 * @test
 * @bug 4473006
 * @summary Test selectNow method with no registered channels
 */

import java.io.*;
import java.nio.*;
import java.nio.channels.*;
import java.nio.channels.spi.SelectorProvider;


public class SelectNowWhenEmpty {
    public static void main(String[] args) throws Exception {
        Selector s = SelectorProvider.provider().openSelector();
        s.selectNow();
    }
}
