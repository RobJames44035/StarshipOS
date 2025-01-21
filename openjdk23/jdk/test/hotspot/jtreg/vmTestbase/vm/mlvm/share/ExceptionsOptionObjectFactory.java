/*
 * StarshipOS Copyright (c) 2014-2025. R.A. James
 */

package vm.mlvm.share;

import java.util.List;
import java.util.ArrayList;
import vm.share.options.OptionObjectFactory;
import java.util.regex.Pattern;

/**
 * Implementation of vm.share.options.OptionObjectFactory interface.
 * Parses the comma-separated list of exception class names.
 *
 * @see vm.mlvm.share.MlvmTest
 * @see vm.mlvm.share.MlvmTestExecutor#launch(Class<?> testClass, Object[] constructorArgs)
 *
 */

public class ExceptionsOptionObjectFactory implements OptionObjectFactory<List<Class<? extends Throwable>>> {

    private static final String DESCRIPTION = "list of exception class names separated by comma";

    @Override
    public String getPlaceholder() {
        return DESCRIPTION;
    }

    @Override
    public String getDescription() {
        return DESCRIPTION;
    }

    @Override
    public String getParameterDescription(String param) {
        return "exception of type " + param;
    }

    @Override
    public String[] getPossibleValues() {
        return new String[] { Throwable.class.getName() };
    }

    @Override
    public String getDefaultValue() {
        return "";
    }

    @Override
    public List<Class<? extends Throwable>> getObject(String classNameList) {
        List<Class<? extends Throwable>> result = new ArrayList<>();
        classNameList = classNameList.trim();

        if (!classNameList.isEmpty()) {
            for (String className : classNameList.split(",")) {
                result.add(getClassFor(className.trim()));
            }
        }

        return result;
    }

    private static Class<? extends Throwable> getClassFor(String className) {
        try {
            return Class.forName(className).asSubclass(Throwable.class);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Cannot find class '" + className + "'", e);
        } catch (ClassCastException e) {
            throw new RuntimeException("Subclass of " + Throwable.class.getName() + " should be specified. Cannot cast '" + className + "' to the Throwable", e);
        }
    }
}
