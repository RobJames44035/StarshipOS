/*
 * StarshipOS Copyright (c) 2013-2025. R.A. James
 */
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;

/**
 * This class is loaded by a class loader that can see the resource. It creates
 * a new classloader for LoadItUp2 which cannot see the resource.  So, 2 levels
 * up the call chain we have a class/classloader that can see the resource, but
 * 1 level up the class/classloader cannot.
 *
 * @author Jim Gish
 */
public class LoadItUp2Invoker {
    private URLClassLoader cl;
    private String rbName;
    private Object loadItUp2;
    private Method testMethod;

    public void setup(URL[] urls, String rbName) throws
                       ReflectiveOperationException {
        this.cl = new URLClassLoader(urls, null);
        this.rbName = rbName;
        // Using this new classloader, load the actual test class
        // which is now two levels removed from the original caller
        Class<?> loadItUp2Clazz = Class.forName("LoadItUp2", true , cl);
        this.loadItUp2 = loadItUp2Clazz.newInstance();
        this.testMethod = loadItUp2Clazz.getMethod("test", String.class);
    }

    public Boolean test() throws Throwable {
        try {
            return (Boolean) testMethod.invoke(loadItUp2, rbName);
        } catch (InvocationTargetException ex) {
            throw ex.getTargetException();
        }
    }
}
