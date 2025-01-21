/*
 * StarshipOS Copyright (c) 2015-2025. R.A. James
 */

package compiler.compilercontrol.share.method;

import java.lang.reflect.Constructor;
import java.lang.reflect.Executable;

/**
 * Represents a method in CompileControl method signature
 */
public class MethodType extends MethodElementType {
    private static final char[] INVALID_CHARS = { '.', '/' };

    public MethodType(Executable method) {
        // Use pack/subpack/Class::method separators style
        super(MethodDescriptor.Separator.DOT);
        if (method instanceof Constructor) {
            element = "<init>";
        } else {
            element = method.getName();
        }
        regexp = element;
    }

    @Override
    public boolean isValid() {
        for (char ch : INVALID_CHARS) {
            if (element.indexOf(ch) != -1) {
                return false;
            }
        }
        if (element.isEmpty()) {
            // Shouldn't be empty
            return false;
        }
        if (element.contains("<") || element.contains(">")) {
            return element.matches("(\\*)?<(cl)?init>(\\*)?");
        }
        return super.isValid();
    }

    @Override
    public void setPattern(MethodDescriptor.PatternType patternType) {
        switch (patternType) {
            case EXACT:
                break;
            case PREFIX:
                regexp = ".*" + regexp;
                element = "*" + element;
                break;
            case ANY:
                regexp = "[^(]*";
                element = "*";
                break;
            case SUFFIX:
                regexp = regexp + "[^(]*";
                element = element + "*";
                break;
            case SUBSTRING:
                setPattern(MethodDescriptor.PatternType.PREFIX);
                setPattern(MethodDescriptor.PatternType.SUFFIX);
                break;
            default:
                throw new IllegalArgumentException("ERROR: wrong pattern type "
                        + patternType);
        }
    }
}
