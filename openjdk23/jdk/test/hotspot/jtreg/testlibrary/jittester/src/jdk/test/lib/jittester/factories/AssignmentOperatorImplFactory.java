/*
 * StarshipOS Copyright (c) 2015-2025. R.A. James
 */

package jdk.test.lib.jittester.factories;

import jdk.test.lib.util.Pair;
import jdk.test.lib.jittester.BinaryOperator;
import jdk.test.lib.jittester.IRNode;
import jdk.test.lib.jittester.OperatorKind;
import jdk.test.lib.jittester.ProductionFailedException;
import jdk.test.lib.jittester.Rule;
import jdk.test.lib.jittester.Type;
import jdk.test.lib.jittester.TypeList;
import jdk.test.lib.jittester.VariableBase;
import jdk.test.lib.jittester.utils.TypeUtil;
import jdk.test.lib.jittester.VariableInfo;
import jdk.test.lib.jittester.types.TypeKlass;
import jdk.test.lib.jittester.utils.PseudoRandom;

class AssignmentOperatorImplFactory extends BinaryOperatorFactory {
    AssignmentOperatorImplFactory(long complexityLimit, int operatorLimit, TypeKlass ownerClass,
            Type resultType, boolean exceptionSafe, boolean noconsts) {
        super(OperatorKind.ASSIGN, complexityLimit, operatorLimit, ownerClass, resultType, exceptionSafe, noconsts);
    }

    @Override
    protected boolean isApplicable(Type resultType) {
        return true;
    }

    @Override
    protected Pair<Type, Type> generateTypes() {
        return new Pair<>(resultType, PseudoRandom.randomElement(
                TypeUtil.getImplicitlyCastable(TypeList.getAll(), resultType)));
    }

    @Override
    protected BinaryOperator generateProduction(Type leftOperandType, Type rightOperandType)
            throws ProductionFailedException {
        long leftComplexityLimit = (long) (PseudoRandom.random() * complexityLimit);
        long rightComplexityLimit = complexityLimit - leftComplexityLimit;
        int leftOperatorLimit = (int) (PseudoRandom.random() * operatorLimit);
        int rightOperatorLimit = operatorLimit = leftOperatorLimit;
        IRNodeBuilder builder = new IRNodeBuilder().setOwnerKlass((TypeKlass) ownerClass)
                .setExceptionSafe(exceptionSafe)
                .setNoConsts(noconsts)
                .setComplexityLimit(leftComplexityLimit)
                .setOperatorLimit(leftOperatorLimit)
                .setResultType(leftOperandType)
                .setIsConstant(false);
        Rule<VariableBase> rule = new Rule<>("assignment");
        rule.add("initialized_nonconst_var", builder.setIsInitialized(true).getVariableFactory());
        rule.add("uninitialized_nonconst_var", builder.setIsInitialized(false).getVariableFactory());
        VariableBase leftOperandValue = rule.produce();
        IRNode rightOperandValue = builder.setComplexityLimit(rightComplexityLimit)
                .setOperatorLimit(rightOperatorLimit)
                .setResultType(rightOperandType)
                .getExpressionFactory()
                .produce();
        try {
            if ((leftOperandValue.getVariableInfo().flags & VariableInfo.INITIALIZED) == 0) {
                leftOperandValue.getVariableInfo().flags |= VariableInfo.INITIALIZED;
            }
        } catch (Exception e) {
            throw new ProductionFailedException(e.getMessage());
        }
        return new BinaryOperator(opKind, resultType, leftOperandValue, rightOperandValue);
    }
}
