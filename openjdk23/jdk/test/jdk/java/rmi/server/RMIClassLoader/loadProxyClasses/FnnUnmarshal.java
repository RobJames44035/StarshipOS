/*
 * StarshipOS Copyright (c) 2000-2025. R.A. James
 */

/*
 *
 */

import java.io.IOException;
import java.rmi.MarshalledObject;

public interface FnnUnmarshal {
    Object unmarshal(MarshalledObject mobj)
        throws IOException, ClassNotFoundException;
}
