/*
 * StarshipOS Copyright (c) 2022-2025. R.A. James
 */

/*
 * @test
 * @bug 8268312
 * @summary Compilation error with nested generic functional interface
 * @compile DiagnosticRewriterTest3.java
 */

import java.util.Optional;

class DiagnosticRewriterTest3 {
    void m() {
        Optional.of("").map(outer -> {
            Optional.of("")
                    .map(inner -> returnGeneric(outer))
                    .ifPresent(String::toString);
            return "";
        });
    }

    <T> T returnGeneric(T generic) {
        return generic;
    }
}
