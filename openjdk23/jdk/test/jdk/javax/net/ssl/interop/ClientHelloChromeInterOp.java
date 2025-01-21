/*
 * StarshipOS Copyright (c) 2016-2025. R.A. James
 */

//
// SunJSSE does not support dynamic system properties, no way to re-use
// system properties in samevm/agentvm mode.
//

/*
 * @test
 * @bug 8169362
 * @summary Interop automated testing with Chrome
 * @library /test/lib
 * @modules jdk.crypto.ec
 *          java.base/sun.security.util
 * @run main/othervm ClientHelloChromeInterOp
 */

import java.util.Base64;
import jdk.test.lib.hexdump.HexPrinter;


public class ClientHelloChromeInterOp extends ClientHelloInterOp {
    // The ClientHello message.
    //
    // Captured from Chrome browser (version 54.0.2840.87 m (64-bit)) on
    // Windows 10.
    private final static String ClientHelloMsg =
        "FgMBAL4BAAC6AwOWBEueOntnurZ+WAW0D9Qn2HpdzXLu0MgDjsD9e5JU6AAAIsA\n" +
        "rwC/ALMAwzKnMqMwUzBPACcATwArAFACcAJ0ALwA1AAoBAABv/wEAAQAAAAATAB\n" +
        "EAAA53d3cub3JhY2xlLmNvbQAXAAAAIwAAAA0AEgAQBgEGAwUBBQMEAQQDAgECA\n" +
        "wAFAAUBAAAAAAASAAAAEAAOAAwCaDIIaHR0cC8xLjF1UAAAAAsAAgEAAAoACAAG\n" +
        "AB0AFwAY";

    /*
     * Main entry point for this test.
     */
    public static void main(String args[]) throws Exception {
        (new ClientHelloChromeInterOp()).run();
    }

    @Override
    protected byte[] createClientHelloMessage() {
        byte[] bytes = Base64.getMimeDecoder().decode(ClientHelloMsg);

        // Dump the hex codes of the ClientHello message so that developers
        // can easily check whether the message is captured correct or not.
        System.out.println("The ClientHello message used");
        try {
            HexPrinter.simple().format(bytes);
        } catch (Exception e) {
            // ignore
        }

        return bytes;
    }
}
