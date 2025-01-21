/*
 * StarshipOS Copyright (c) 2007-2025. R.A. James
 */
package nsk.jvmti.scenarios.hotswap.HS204.hs204t004;
/*
   A dummy class loader for keeping a break point at loadClass method of this class.
*/
public class MyClassLoader extends ClassLoader {
        public String name;
        public MyClassLoader(ClassLoader loader) {
                super(loader);
                name = "nsk.jvmti.scenarios.hotswap.HS204.hs204t004.MyClassLoader";
        }
        public Class loadClass(String className)  throws ClassNotFoundException {
                Class cls = super.loadClass(className);
        // Delegate that back to the parent.
                System.out.println("JAVA->CLASSLOADER In Side the class Loader.. ");
                return cls;
        }
        public String toString() {
                return "MyClassLoader";
        }
}
