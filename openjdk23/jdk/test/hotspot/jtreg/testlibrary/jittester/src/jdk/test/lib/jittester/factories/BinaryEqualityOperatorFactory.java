/*
 * StarshipOS Copyright (c) 2015-2025. R.A. James
 */

package jdk.test.lib.jittester.factories;

import jdk.test.lib.util.Pair;
import jdk.test.lib.jittester.OperatorKind;
import jdk.test.lib.jittester.ProductionFailedException;
import jdk.test.lib.jittester.Type;
import jdk.test.lib.jittester.TypeList;
import jdk.test.lib.jittester.types.TypeKlass;
import jdk.test.lib.jittester.utils.PseudoRandom;

import java.util.ArrayList;
import java.util.List;

class BinaryEqualityOperatorFactory extends BinaryOperatorFactory {
    BinaryEqualityOperatorFactory(OperatorKind opKind, long complexityLimit, int operatorLimit,
            TypeKlass ownerClass, Type resultType, boolean exceptionSafe, boolean noconsts) {
        super(opKind, complexityLimit, operatorLimit, ownerClass, resultType, exceptionSafe, noconsts);
    }

    @Override
    protected boolean isApplicable(Type resultType) {
        return resultType.equals(TypeList.BOOLEAN);
    }

    @Override
    protected Pair<Type, Type> generateTypes() {
        final List<Type> builtInExceptBoolean = new ArrayList<>(TypeList.getBuiltIn());
        builtInExceptBoolean.remove(TypeList.BOOLEAN);
        return new Pair<>(PseudoRandom.randomElement(builtInExceptBoolean), PseudoRandom.randomElement(builtInExceptBoolean));
    }
}
