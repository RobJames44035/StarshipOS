/*
 * StarshipOS Copyright (c) 2011-2025. R.A. James
 */

package vm.mlvm.meth.share;

public class RandomValueGen {

    private static final int MAX_DISTINCT_TRIES = 11;

    /*
     * Primitive values are returned boxed. void is returned as null String
     * contains 0..100 random characters
     */
    public static Object next(Class<?> type) throws InstantiationException, IllegalAccessException {
        return TestTypes.nextRandomValueForType(type);
    }

    public static Object nextDistinct(Class<?> type, Object notEqualTo) throws InstantiationException, IllegalAccessException {
        Object nonEqualValue;
        for ( int i = MAX_DISTINCT_TRIES; i > 0; i -- ){
            nonEqualValue = next(type);
            if ( ! nonEqualValue.equals(notEqualTo) )
                return nonEqualValue;
        }

        // A workaround for booleans. Sometimes RNG produces long series of trues or falses
        if ( type.equals(Boolean.class) || type.equals(boolean.class) )
            return Boolean.valueOf(! (Boolean) notEqualTo);

        throw new InstantiationException("Can't create distinct value for type=[" + type.getName() + "]; value=[" + notEqualTo + "]");
    }

    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) {
            Class<?> type = RandomTypeGen.next();
            Object value;
            try {
                value = next(type);
                System.out.println("type=[" + type + "], value=[" + value + "]");
            } catch (Exception e) {
                System.err.println("type=[" + type + "]");
                e.printStackTrace();
            }
        }
    }


}
