/*
 * StarshipOS Copyright (c) 2011-2025. R.A. James
 */

package vm.mlvm.meth.share;

import java.lang.invoke.MethodType;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public final class Arguments {

    public static List<Argument> listFromArray(Object[][] a) {
        ArrayList<Argument> result = new ArrayList<Argument>(a.length);

        for (Object[] elem : a) {
            result.add(Argument.fromArray(elem));
        }

        return result;
    }

    public static Argument[] fromArray(Object[][] a) {
        return listFromArray(a).toArray(new Argument[a.length]);
    }

    public static Class<?>[] typesArray(List<Argument> vts) {
        return typesArray(vts.toArray(new Argument[vts.size()]));
    }

    public static Class<?>[] typesArray(Argument[] vts) {
        Class<?>[] result = new Class<?>[vts.length];
        for (int i = 0; i < vts.length; i++)
            result[i] = vts[i].getType();
        return result;
    }

    public static Object[] valuesArray(List<Argument> vts) {
        return valuesArray(vts.toArray(new Argument[vts.size()]));
    }

    public static Object[] valuesArray(Argument[] vts) {
        Object[] result = new Object[vts.length];
        for (int i = 0; i < vts.length; i++)
            result[i] = vts[i].getValue();
        return result;
    }

    public static Argument[] fromMethodType(boolean isVirtual, MethodType t, MethodParameterValueProvider vp) {
        int virtualOffset = isVirtual ? 1 : 0;
        Class<?>[] paramTypes = t.parameterArray();
        Argument[] result = new Argument[paramTypes.length - virtualOffset];
        for (int i = virtualOffset; i < paramTypes.length; i++) {
            result[i - virtualOffset] = new Argument(paramTypes[i], vp.getValue(t, i));
        }
        return result;
    }

    public static int[] findTag(List<Argument> args, String tag) {
        return findTag(args.toArray(new Argument[args.size()]), tag);
    }

    public static int[] findTag(Argument[] args, String tag) {
        int[] result = new int[args.length];
        int resCount = 0;
        for ( int i = 0; i < args.length; i++ ) {
            if ( args[i].getTag().equals(tag) )
                result[resCount++] = i;
        }

        return Arrays.copyOf(result, resCount);
    }

}
