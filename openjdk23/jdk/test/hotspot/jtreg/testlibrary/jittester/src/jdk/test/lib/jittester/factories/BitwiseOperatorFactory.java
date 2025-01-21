/*
 * StarshipOS Copyright (c) 2015-2025. R.A. James
 */

package jdk.test.lib.jittester.factories;

import jdk.test.lib.jittester.Operator;
import jdk.test.lib.jittester.OperatorKind;
import jdk.test.lib.jittester.ProductionFailedException;
import jdk.test.lib.jittester.Rule;
import jdk.test.lib.jittester.Type;
import jdk.test.lib.jittester.types.TypeKlass;

class BitwiseOperatorFactory extends Factory<Operator> {
    private final Rule<Operator> rule;

    BitwiseOperatorFactory(long complexityLimit, int operatorLimit, TypeKlass ownerClass,
            Type resultType, boolean exceptionSafe, boolean noconsts) throws ProductionFailedException {
        IRNodeBuilder builder = new IRNodeBuilder()
                .setComplexityLimit(complexityLimit)
                .setOperatorLimit(operatorLimit)
                .setOwnerKlass(ownerClass)
                .setResultType(resultType)
                .setExceptionSafe(exceptionSafe)
                .setNoConsts(noconsts);
        rule = new Rule<>("bitwise");
        rule.add("and", builder.setOperatorKind(OperatorKind.BIT_AND).getBinaryOperatorFactory());
        rule.add("or", builder.setOperatorKind(OperatorKind.BIT_OR).getBinaryOperatorFactory());
        rule.add("xor", builder.setOperatorKind(OperatorKind.BIT_XOR).getBinaryOperatorFactory());
        rule.add("not", builder.setOperatorKind(OperatorKind.BIT_NOT).getUnaryOperatorFactory());
        rule.add("shl", builder.setOperatorKind(OperatorKind.SHL).getBinaryOperatorFactory());
        rule.add("shr", builder.setOperatorKind(OperatorKind.SHR).getBinaryOperatorFactory());
        rule.add("sar", builder.setOperatorKind(OperatorKind.SAR).getBinaryOperatorFactory());
    }

    @Override
    public Operator produce() throws ProductionFailedException {
        return rule.produce();
    }
}
