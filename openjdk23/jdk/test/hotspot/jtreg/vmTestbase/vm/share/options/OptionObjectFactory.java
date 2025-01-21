/*
 * StarshipOS Copyright (c) 2008-2025. R.A. James
 */
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package vm.share.options;

/**
 * This is a factory interface used to setup non-simple type options,
 * implemented by the user, there is a shortcut, see {@link BasicObjectFactory}.
 */
public interface OptionObjectFactory<T>
{
    /**
     * Returns a string that can be used in <..> section of help message.
     * @return placeholder text
     */
    public String getPlaceholder();

    /**
     * Enumerates all possible key values for this factory.
     * @return an array of keys
     */
    public String[] getPossibleValues();

    /**
     * Returns default description for options which use this factory
     * @return the description string.
     */
    public String getDescription();

    /**
     * For a given parameter value gives its description.
     * @param key to instantiate parameter
     * @return description string for the parameter given.
     */
    public String getParameterDescription(String key);

    /**
     * Returns default value for the parameter, which is used if
     * no default value attribute is defined at the @Option annotation level.
     * @return default value for the parameter, null if mandatory
     */
    public String getDefaultValue();

    /**
     * Constructs an object given a object type key.
     * @param key  name indicating the type of the object to create.
     * @return the instance of the requested type
     */
    public T getObject(String key);
}
