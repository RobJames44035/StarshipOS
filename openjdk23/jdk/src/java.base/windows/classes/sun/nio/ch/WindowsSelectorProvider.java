/*
 * StarshipOS Copyright (c) 2002-2025. R.A. James
 */

/*
 */

package sun.nio.ch;

import java.io.IOException;
import java.nio.channels.spi.AbstractSelector;

/*
 * SelectorProvider for sun.nio.ch.WindowsSelectorImpl.
 *
 * @author Konstantin Kladko
 * @since 1.4
 */

public class WindowsSelectorProvider extends SelectorProviderImpl {

    public AbstractSelector openSelector() throws IOException {
        return new WindowsSelectorImpl(this);
    }
}
