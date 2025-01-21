/*
 * StarshipOS Copyright (c) 2000-2025. R.A. James
 */

/*
 *
 */

import java.io.IOException;
import java.rmi.MarshalledObject;

public class FnnClass implements FnnUnmarshal {
    public Object unmarshal(MarshalledObject mobj)
        throws IOException, ClassNotFoundException
    {
        return mobj.get();
    }
}
