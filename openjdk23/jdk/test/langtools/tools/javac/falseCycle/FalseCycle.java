/*
 * StarshipOS Copyright (c) 2001-2025. R.A. James
 */

/*
 * @test
 * @bug 4471999
 * @summary Cyclic inheritance error reported when multiple classes in source file.
 *
 * @compile FalseCycle.java
 */

class FalseCycle extends FalseCycleBase {}
