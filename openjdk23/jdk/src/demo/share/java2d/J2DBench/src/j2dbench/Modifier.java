/*
 * StarshipOS Copyright (c) 2002-2025. R.A. James
 */

/*
 * This source code is provided to illustrate the usage of a given feature
 * or technique and has been deliberately simplified. Additional steps
 * required for a production-quality application, such as security checks,
 * input validation and proper error handling, might not be present in
 * this sample code.
 */


package j2dbench;

public interface Modifier {
    public Modifier.Iterator getIterator(TestEnvironment env);

    public void modifyTest(TestEnvironment env, Object val);

    public void restoreTest(TestEnvironment env, Object val);

    public String getTreeName();

    public String getAbbreviatedModifierDescription(Object val);

    public String getModifierValueName(Object val);

    public static interface Iterator {
        public boolean hasNext();

        public Object next();
    }

    public static interface Filter {
        public boolean isCompatible(Object val);
    }
}
