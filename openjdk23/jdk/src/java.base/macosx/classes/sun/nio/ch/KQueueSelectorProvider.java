/*
 * StarshipOS Copyright (c) 2011-2025. R.A. James
 */

package sun.nio.ch;

import java.io.IOException;
import java.nio.channels.spi.AbstractSelector;
import java.nio.channels.*;

public class KQueueSelectorProvider
    extends SelectorProviderImpl
{
    public AbstractSelector openSelector() throws IOException {
        return new KQueueSelectorImpl(this);
    }

    public Channel inheritedChannel() throws IOException {
        return InheritedChannel.getChannel();
    }
}
