/*
 * StarshipOS Copyright (c) 2015-2025. R.A. James
 */

package jdk.test.lib.jittester.factories;

import jdk.test.lib.util.Pair;
import jdk.test.lib.jittester.OperatorKind;
import jdk.test.lib.jittester.ProductionFailedException;
import jdk.test.lib.jittester.Type;
import jdk.test.lib.jittester.TypeList;
import jdk.test.lib.jittester.utils.TypeUtil;
import jdk.test.lib.jittester.types.TypeKlass;
import jdk.test.lib.jittester.utils.PseudoRandom;

import java.util.Collection;

class BinaryBitwiseOperatorFactory extends BinaryOperatorFactory {
    BinaryBitwiseOperatorFactory(OperatorKind opKind, long complexityLimit, int operatorLimit,
            TypeKlass ownerClass, Type resultType, boolean exceptionSafe, boolean noconsts) {
        super(opKind, complexityLimit, operatorLimit, ownerClass, resultType, exceptionSafe, noconsts);
    }

    @Override
    protected boolean isApplicable(Type resultType) {
        return resultType.equals(TypeList.INT) || resultType.equals(TypeList.LONG) || resultType.equals(TypeList.BOOLEAN);
    }

    @Override
    protected Pair<Type, Type> generateTypes() {
        Collection<Type> castableFromResult = TypeUtil.getImplicitlyCastable(TypeList.getBuiltIn(), resultType);
        // built-in types less capacious than int are automatically casted to int in arithmetic.
        final Type leftType = PseudoRandom.randomElement(castableFromResult);
        final Type rightType = resultType.equals(TypeList.INT) ? PseudoRandom.randomElement(castableFromResult) : resultType;
        //TODO: is there sense to swap them randomly as it was done in original code?
        return PseudoRandom.randomBoolean() ? new Pair<>(leftType, rightType) : new Pair<>(rightType, leftType);
    }
}
