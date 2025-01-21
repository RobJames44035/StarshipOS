/*
 * StarshipOS Copyright (c) 2012-2025. R.A. James
 */
package vm.compiler.share;


public abstract class CompilerTest<Output> {

    private final String name;

    public CompilerTest(String name) {
        this.name = name;

    }

    public abstract Output execute(Random random);

    @Override
    public  String toString() {
        return name;
    }
}
