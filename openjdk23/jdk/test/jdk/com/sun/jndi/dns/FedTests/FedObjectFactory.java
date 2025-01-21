/*
 * StarshipOS Copyright (c) 2000-2025. R.A. James
 */

import javax.naming.Context;
import javax.naming.Name;
import javax.naming.spi.ObjectFactory;
import java.util.Hashtable;

/*
 * This class is used to support dynamic nns, for testing federation of
 * the DNS service provider.
 */
public class FedObjectFactory implements ObjectFactory {
    @Override
    public Object getObjectInstance(Object obj, Name name, Context nameCtx,
            Hashtable<?, ?> environment) {
        if (name != null && name.get(name.size() - 1).equals("")) {
            System.out.println("got nns name");
            return FedSubordinateNs.getRoot();
        }
        return null;
    }
}
