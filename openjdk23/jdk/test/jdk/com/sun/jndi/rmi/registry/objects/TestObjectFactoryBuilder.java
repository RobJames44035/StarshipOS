/*
 * StarshipOS Copyright (c) 2024-2025. R.A. James
 */

import javax.naming.ConfigurationException;
import javax.naming.NamingException;
import javax.naming.Reference;
import javax.naming.spi.ObjectFactory;
import javax.naming.spi.ObjectFactoryBuilder;
import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Hashtable;

/**
 * Test library class that implements {@code javax.naming.spi.ObjectFactoryBuilder} interface.
 * Its implementation allows object factory class loading from any remote location.
 */
public class TestObjectFactoryBuilder implements ObjectFactoryBuilder {

    @Override
    public ObjectFactory createObjectFactory(Object obj, Hashtable<?, ?> environment) throws NamingException {
        System.err.println("TestObjectFactoryBuilder: Creating new object factory");
        System.err.println("Builder for object: " + obj);
        System.err.println("And for environment: " + environment);
        // Only objects of the Reference type are supported, others are rejected
        if (obj instanceof Reference ref) {
            String objectFactoryLocation = ref.getFactoryClassLocation();
            try {
                URL factoryURL = new URL(objectFactoryLocation);
                var cl = new URLClassLoader(new URL[]{factoryURL});
                Class<?> factoryClass = cl.loadClass(ref.getFactoryClassName());
                System.err.println("Loaded object factory: " + factoryClass);
                if (ObjectFactory.class.isAssignableFrom(factoryClass)) {
                    return (ObjectFactory) factoryClass
                            .getDeclaredConstructor().newInstance();
                } else {
                    throw new ConfigurationException("Test configuration error -" +
                            " loaded object factory of wrong type");
                }
            } catch (MalformedURLException e) {
                throw new ConfigurationException("Error constructing test object factory");
            } catch (ClassNotFoundException | NoSuchMethodException | InstantiationException |
                     IllegalAccessException | InvocationTargetException e) {
                throw new ConfigurationException("Test configuration error: " +
                        "factory class cannot be loaded from the provided " +
                        "object factory location");
            }
        } else {
            throw new ConfigurationException("Test factory builder " +
                    "supports only Reference types");
        }
    }
}
