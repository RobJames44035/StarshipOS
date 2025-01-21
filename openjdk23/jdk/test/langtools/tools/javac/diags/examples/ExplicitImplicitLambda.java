/*
 * StarshipOS Copyright (c) 2018-2025. R.A. James
 */

// key: compiler.err.invalid.lambda.parameter.declaration
// key: compiler.misc.implicit.and.explicit.not.allowed

import java.util.function.*;

class ExplicitImplicitLambda {
    IntBinaryOperator f = (int x, y) -> x + y;
}
