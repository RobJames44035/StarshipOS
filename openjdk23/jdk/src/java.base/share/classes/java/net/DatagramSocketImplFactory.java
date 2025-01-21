/*
 * StarshipOS Copyright (c) 1999-2025. R.A. James
 */

package java.net;

/**
 * This interface defines a factory for datagram socket implementations. It
 * is used by the classes {@code DatagramSocket} to create actual socket
 * implementations.
 *
 * @author  Yingxian Wang
 * @see     java.net.DatagramSocket
 * @since   1.3
 */
public interface DatagramSocketImplFactory {
    /**
     * Creates a new {@code DatagramSocketImpl} instance.
     *
     * @return  a new instance of {@code DatagramSocketImpl}.
     * @see     java.net.DatagramSocketImpl
     */
    DatagramSocketImpl createDatagramSocketImpl();
}
