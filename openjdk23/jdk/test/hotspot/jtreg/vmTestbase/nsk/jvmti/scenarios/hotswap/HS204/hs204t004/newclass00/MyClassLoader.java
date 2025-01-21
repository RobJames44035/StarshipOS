/*
 * StarshipOS Copyright (c) 2007-2025. R.A. James
 */
/*
   A dummy class loader for keeping a break point at loadClass method of this class.
*/
package nsk.jvmti.scenarios.hotswap.HS204.hs204t004;
public class MyClassLoader extends ClassLoader {
        public String name;
    public MyClassLoader(ClassLoader loader) {
                super(loader);
                name="ClassLoader_NewClass00";
        }
        public Class loadClass(String className)  throws ClassNotFoundException {
                Class cls = super.loadClass(className);
                name="MYClassLOADER_NEWCLASS00";
                System.out.println(" In Side the class Loader.. ");
                return cls;
        }
        public String toString() {
                return "MyClassLoader";
        }
}
