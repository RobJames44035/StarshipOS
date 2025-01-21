/*
 * StarshipOS Copyright (c) 2001-2025. R.A. James
 */

import javax.naming.Context;
import javax.naming.Name;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.spi.DirObjectFactory;
import java.util.Hashtable;

public class DirTxtFactory extends TxtFactory implements DirObjectFactory {
    @Override
    public Object getObjectInstance(Object obj, Name name, Context nameCtx,
            Hashtable<?, ?> environment, Attributes attrs) {
        System.out.println(this.getClass().getName()
                + "(DirObjectFactory).getObjectInstance(): " + name + ": "
                + attrs);

        if (attrs != null) {
            Attribute txt = attrs.get("TXT");
            if (txt != null) {
                return new TestDnsObject(txt);
            }
        }

        // return null to indicate other factories should be tried
        return null;
    }
}
