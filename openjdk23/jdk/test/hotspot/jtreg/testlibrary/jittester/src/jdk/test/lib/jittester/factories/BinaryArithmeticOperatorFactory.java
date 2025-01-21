/*
 * StarshipOS Copyright (c) 2015-2025. R.A. James
 */

package jdk.test.lib.jittester.factories;

import jdk.test.lib.util.Pair;
import jdk.test.lib.jittester.BuiltInType;
import jdk.test.lib.jittester.OperatorKind;
import jdk.test.lib.jittester.ProductionFailedException;
import jdk.test.lib.jittester.Type;
import jdk.test.lib.jittester.TypeList;
import jdk.test.lib.jittester.utils.TypeUtil;
import jdk.test.lib.jittester.types.TypeKlass;
import jdk.test.lib.jittester.utils.PseudoRandom;

import java.util.Collection;

class BinaryArithmeticOperatorFactory extends BinaryOperatorFactory {
    BinaryArithmeticOperatorFactory(OperatorKind opKind, long complexityLimit, int operatorLimit,
            TypeKlass ownerClass, Type resultType, boolean exceptionSafe, boolean noconsts) {
        super(opKind, complexityLimit, operatorLimit, ownerClass, resultType, exceptionSafe, noconsts);
    }

    @Override
    protected boolean isApplicable(Type resultType) {
        // arithmetic for built-in types less capacious than "int" is not supported.
        if (TypeList.isBuiltIn(resultType)) {
            BuiltInType builtInType = (BuiltInType) resultType;
            return builtInType.equals(TypeList.INT) || builtInType.isMoreCapaciousThan(TypeList.INT);
        } else {
            return false;
        }
    }

    @Override
    protected Pair<Type, Type> generateTypes() {
        Collection<Type> castableFromResultType = TypeUtil.getImplicitlyCastable(TypeList.getBuiltIn(), resultType);
        // built-in types less capacious than int are automatically casted to int in arithmetic.
        final Type leftType = PseudoRandom.randomElement(castableFromResultType);
        final Type rightType = resultType.equals(TypeList.INT) ?
                PseudoRandom.randomElement(castableFromResultType) : resultType;
        //TODO: is there sense to swap them randomly as it was done in original code?
        return PseudoRandom.randomBoolean() ? new Pair<>(leftType, rightType) : new Pair<>(rightType, leftType);
    }
}
