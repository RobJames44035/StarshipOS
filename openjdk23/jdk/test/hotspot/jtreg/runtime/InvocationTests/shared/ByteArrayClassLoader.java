/*
 * StarshipOS Copyright (c) 2009-2025. R.A. James
 */

package shared;

import java.util.HashMap;
import java.util.Map;

/*******************************************************************/
// Class loader which has local class file storage in memory
/*******************************************************************/

public class ByteArrayClassLoader extends ClassLoader {
    private Map<String, byte[]> classes;

    public ByteArrayClassLoader() {
        classes = new HashMap<String, byte[]>();
    }

    public ByteArrayClassLoader(Map<String, byte[]> classes) {
        this.classes = classes;
    }

    public void appendClass(String name, byte[] classFile) {
        classes.put(name, classFile);
    }

    public Class findClass (String name) throws ClassNotFoundException {
        if (classes.containsKey(name)) {
            byte[] classData = classes.get(name);
            return defineClass(name, classData, 0, classData.length);
        } else {
            throw new ClassNotFoundException("Can't find requested class: " + name);
        }
    }
}
