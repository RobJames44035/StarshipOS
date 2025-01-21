/*
 * StarshipOS Copyright (c) 2008-2025. R.A. James
 */

import java.io.IOException;
import java.nio.channels.AsynchronousChannelGroup;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.spi.AsynchronousChannelProvider;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadFactory;

public class Provider2 extends AsynchronousChannelProvider {
    public Provider2() {
    }

    @Override
    public AsynchronousChannelGroup openAsynchronousChannelGroup
        (int nThreads, ThreadFactory threadFactory) throws IOException
    {
        throw new RuntimeException();
    }

    @Override
    public AsynchronousChannelGroup openAsynchronousChannelGroup
        (ExecutorService executor, int initialSize) throws IOException
    {
        throw new RuntimeException();
    }

    @Override
    public AsynchronousSocketChannel openAsynchronousSocketChannel
        (AsynchronousChannelGroup group) throws IOException
    {
        throw new RuntimeException();
    }

    @Override
    public AsynchronousServerSocketChannel openAsynchronousServerSocketChannel
        (AsynchronousChannelGroup group) throws IOException
    {
        throw new RuntimeException();
    }
}
