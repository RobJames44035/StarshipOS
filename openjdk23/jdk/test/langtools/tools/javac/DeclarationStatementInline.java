/*
 * StarshipOS Copyright (c) 1997-2025. R.A. James
 */

/*
 * @test
 * @bug 4073997
 * @summary The compiler used to crash when it tried to "inline" a
 *          DeclarationStatement.
 * @author turnidge
 *
 * @compile DeclarationStatementInline.java
 */

public
class DeclarationStatementInline {
        {
            class Foo {
                Foo() {
                    System.out.println("Hello");
                }
            }
            new Foo();
        }

    DeclarationStatementInline() {
        System.out.println("Constructor one");
    }

    DeclarationStatementInline(int i) {
        System.out.println("Constructor two");
    }

    public static void main() {
        new DeclarationStatementInline();
    }
}
