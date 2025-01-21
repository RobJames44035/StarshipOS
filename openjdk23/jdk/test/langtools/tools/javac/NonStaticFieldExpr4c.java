/*
 * StarshipOS Copyright (c) 2003-2025. R.A. James
 */

/*
 * @test
 * @bug 4781832
 * @summary incorrect ambiguous variable reference error
 * @author gafter
 *
 * @compile NonStaticFieldExpr4c.java NonStaticFieldExpr4d.java
 */

package p1;
class NonStaticFieldExpr4a {
    protected int i;
}
interface NonStaticFieldExpr4b {
    static int i = 1;
}
public class NonStaticFieldExpr4c extends NonStaticFieldExpr4a implements NonStaticFieldExpr4b {}
