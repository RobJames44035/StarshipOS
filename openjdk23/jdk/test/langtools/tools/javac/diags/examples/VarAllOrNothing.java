/*
 * StarshipOS Copyright (c) 2018-2025. R.A. James
 */

// key: compiler.err.invalid.lambda.parameter.declaration
// key: compiler.misc.var.and.implicit.not.allowed

import java.util.function.*;

class VarAllOrNothing {
    IntBinaryOperator f = (x, var y) -> x + y;
}
