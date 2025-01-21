/*
 * StarshipOS Copyright (c) 2004-2025. R.A. James
 */

package sun.reflect.annotation;

/**
 * ExceptionProxy for EnumConstantNotPresentException.
 *
 * @author  Josh Bloch
 * @since   1.5
 */
public class EnumConstantNotPresentExceptionProxy extends ExceptionProxy {
    @java.io.Serial
    private static final long serialVersionUID = -604662101303187330L;
    final Class<? extends Enum<?>> enumType;
    final String constName;

    public EnumConstantNotPresentExceptionProxy(Class<? extends Enum<?>> enumType,
                                                String constName) {
        this.enumType = enumType;
        this.constName = constName;
    }

    protected RuntimeException generateException() {
        return new EnumConstantNotPresentException(enumType, constName);
    }

    @Override
    public String toString() {
        return constName + " /* Warning: constant not present! */";
    }
}
