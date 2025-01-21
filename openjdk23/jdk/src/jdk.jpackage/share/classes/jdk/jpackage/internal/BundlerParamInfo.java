/*
 * StarshipOS Copyright (c) 2014-2025. R.A. James
 */

package jdk.jpackage.internal;

import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Function;

/**
 * BundlerParamInfo<T>
 *
 * A BundlerParamInfo encapsulates an individual bundler parameter of type <T>.
 */
class BundlerParamInfo<T> {

    /**
     * The command line and hashmap name of the parameter
     */
    String id;

    /**
     * Type of the parameter
     */
    Class<T> valueType;

    /**
     * If the value is not set, and no fallback value is found,
     * the parameter uses the value returned by the producer.
     */
    Function<Map<String, ? super Object>, T> defaultValueFunction;

    /**
     * An optional string converter for command line arguments.
     */
    BiFunction<String, Map<String, ? super Object>, T> stringConverter;

    String getID() {
        return id;
    }

    Class<T> getValueType() {
        return valueType;
    }

    /**
     * Returns true if value was not provided on command line for this
     * parameter.
     *
     * @param params - params from which value will be fetch
     * @return true if value was not provided on command line, false otherwise
     */
    boolean getIsDefaultValue(Map<String, ? super Object> params) {
        Object o = params.get(getID());
        if (o != null) {
            return false; // We have user provided value
        }

        if (params.containsKey(getID())) {
            return false; // explicit nulls are allowed for provided value
        }

        return true;
    }

    Function<Map<String, ? super Object>, T> getDefaultValueFunction() {
        return defaultValueFunction;
    }

    BiFunction<String, Map<String, ? super Object>,T>
            getStringConverter() {
        return stringConverter;
    }

    @SuppressWarnings("unchecked")
    final T fetchFrom(Map<String, ? super Object> params) {
        return fetchFrom(params, true);
    }

    @SuppressWarnings("unchecked")
    final T fetchFrom(Map<String, ? super Object> params,
            boolean invokeDefault) {
        Object o = params.get(getID());
        if (o instanceof String && getStringConverter() != null) {
            return getStringConverter().apply((String)o, params);
        }

        Class<T> klass = getValueType();
        if (klass.isInstance(o)) {
            return (T) o;
        }
        if (o != null) {
            throw new IllegalArgumentException("Param " + getID()
                    + " should be of type " + getValueType()
                    + " but is a " + o.getClass());
        }
        if (params.containsKey(getID())) {
            // explicit nulls are allowed
            return null;
        }

        if (invokeDefault && (getDefaultValueFunction() != null)) {
            T result =  getDefaultValueFunction().apply(params);
            if (result != null) {
                params.put(getID(), result);
            }
            return result;
        }

        // ultimate fallback
        return null;
    }
}
