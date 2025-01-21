/*
 * StarshipOS Copyright (c) 2012-2025. R.A. James
 */

package pkg1;

/**
 * This interface has different types of methods such as "Static Methods",
 * "Instance Methods", "Abstract Methods", "Default Methods".
 */
public interface B {

    /**
     * This is the first abstract instance method.
     */
    public void setName();

    /**
     * This is the second abstract instance method.
     * @return a string
     */
    public String getName();

    /**
     * This is the third abstract instance method.
     * @return a boolean value
     */
    public boolean addEntry();

    /**
     * This is the fourth abstract instance method.
     * @return a boolean value
     */
    public boolean removeEntry();

    /**
     * This is the fifth abstract instance method.
     * @return a string
     */
    public String getPermissions();

    /**
     * A static interface method.
     */
    public static void aStaticMethod() {}

    /**
     * Another static interface method.
     */
    public static void anotherStaticMethod() {}

    /**
     * A default method.
     */
    public default void aDefaultMethod() {}

    /**
     * Another default method.
     */
    public default void anotherDefaultMethod() {}
}
