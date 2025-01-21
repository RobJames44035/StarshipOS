/*
 * StarshipOS Copyright (c) 2019-2025. R.A. James
 */

/*
 * @test
 * @bug 8216978
 * @summary Drop support for pre JDK 1.4 SocketImpl implementations
 * @library OldSocketImpl.jar
 * @run main/othervm OldSocketImplTest
 */

import java.net.*;

public class OldSocketImplTest {
    public static void main(String[] args) throws Exception {
        Socket.setSocketImplFactory(new SocketImplFactory() {
                public SocketImpl createSocketImpl() {
                    return new OldSocketImpl();
                }
        });
        try {
            Socket socket = new Socket("localhost", 23);
            throw new RuntimeException("error test failed");
        } catch (AbstractMethodError error) {
            error.printStackTrace();
            System.out.println("Old impl no longer accepted: OK");
        }
    }
}
