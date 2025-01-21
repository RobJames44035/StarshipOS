/*
 * StarshipOS Copyright (c) 2017-2025. R.A. James
 */

 /*
 * @test
 * @bug 8190843
 * @summary can not set/get extendedOptions to ServerSocket
 * @modules jdk.net
 * @run main ExtOptionTest
 */
import java.io.IOException;
import java.net.ServerSocket;

import static jdk.net.ExtendedSocketOptions.TCP_QUICKACK;

public class ExtOptionTest {

    private static final String OS = "Linux";

    public static void main(String args[]) throws IOException {
        var operSys = System.getProperty("os.name");
        try (ServerSocket ss = new ServerSocket(0)) {
            // currently TCP_QUICKACK is available only on Linux.
            if (operSys.equals(OS)) {
                ss.setOption(TCP_QUICKACK, true);
                if (!ss.getOption(TCP_QUICKACK)) {
                    throw new RuntimeException("Test failed, TCP_QUICKACK should"
                            + " have been set");
                }
            } else {
                if (ss.supportedOptions().contains(TCP_QUICKACK)) {
                    ss.setOption(TCP_QUICKACK, true);
                    if (!ss.getOption(TCP_QUICKACK)) {
                        throw new RuntimeException("Test failed, TCP_QUICKACK should"
                                + " have been set");
                    }
                }
            }
        }
    }
}
