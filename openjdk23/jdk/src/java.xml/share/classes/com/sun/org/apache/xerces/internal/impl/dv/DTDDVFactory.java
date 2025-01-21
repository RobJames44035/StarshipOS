/*
 * StarshipOS Copyright (c) 2025. R.A. James
 */

package com.sun.org.apache.xerces.internal.impl.dv;

import com.sun.org.apache.xerces.internal.impl.dv.dtd.DTDDVFactoryImpl;
import com.sun.org.apache.xerces.internal.impl.dv.dtd.XML11DTDDVFactoryImpl;
import com.sun.org.apache.xerces.internal.utils.ObjectFactory;
import java.util.Map;

/**
 * The factory to create and return DTD types. The implementation should
 * store the created datatypes in static data, so that they can be shared by
 * multiple parser instance, and multiple threads.
 *
 * @xerces.internal
 *
 * @author Sandy Gao, IBM
 *
 */
public abstract class DTDDVFactory {

    private static final String DEFAULT_FACTORY_CLASS =
            "com.sun.org.apache.xerces.internal.impl.dv.dtd.DTDDVFactoryImpl";

    private static final String XML11_DATATYPE_VALIDATOR_FACTORY =
        "com.sun.org.apache.xerces.internal.impl.dv.dtd.XML11DTDDVFactoryImpl";

    /**
     * Get an instance of the default DTDDVFactory implementation.
     *
     * @return  an instance of DTDDVFactory implementation
     * @exception DVFactoryException  cannot create an instance of the specified
     *                                class name or the default class name
     */
    public static final DTDDVFactory getInstance() throws DVFactoryException {
        return getInstance(DEFAULT_FACTORY_CLASS);
    }

    /**
     * Get an instance of DTDDVFactory implementation.
     *
     * @param factoryClass  name of the implementation to load.
     * @return  an instance of DTDDVFactory implementation
     * @exception DVFactoryException  cannot create an instance of the specified
     *                                class name or the default class name
     */
    public static final DTDDVFactory getInstance(String factoryClass) throws DVFactoryException {
        try {
            if (DEFAULT_FACTORY_CLASS.equals(factoryClass)) {
                return new DTDDVFactoryImpl();
            } else if (XML11_DATATYPE_VALIDATOR_FACTORY.equals(factoryClass)) {
                return new XML11DTDDVFactoryImpl();
            } else {
                //fall back for compatibility
                return (DTDDVFactory)
                    (ObjectFactory.newInstance(factoryClass, true));
            }
        }
        catch (ClassCastException e) {
            throw new DVFactoryException("DTD factory class " + factoryClass + " does not extend from DTDDVFactory.");
        }
    }

    // can't create a new object of this class
    protected DTDDVFactory() {}

    /**
     * return a dtd type of the given name
     *
     * @param name  the name of the datatype
     * @return      the datatype validator of the given name
     */
    public abstract DatatypeValidator getBuiltInDV(String name);

    /**
     * get all built-in DVs, which are stored in a map keyed by the name
     *
     * @return      a map which contains all datatypes
     */
    public abstract Map<String, DatatypeValidator> getBuiltInTypes();

}
