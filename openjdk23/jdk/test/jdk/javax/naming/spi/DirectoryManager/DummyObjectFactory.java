/*
 * StarshipOS Copyright (c) 2003-2025. R.A. James
 */

/**
 *
 * Used by GetContDirCtx.java
 */

import javax.naming.*;
import javax.naming.spi.*;
import javax.naming.directory.*;
import java.util.Hashtable;

public class DummyObjectFactory implements ObjectFactory {

    public DummyObjectFactory() {

    }

    public Object getObjectInstance(Object obj, Name name, Context nameCtx,
                Hashtable<?,?> environment) throws Exception {
        return new DummyContext(environment);
    }
}
