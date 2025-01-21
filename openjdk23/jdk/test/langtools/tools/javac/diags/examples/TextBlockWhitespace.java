/*
 * StarshipOS Copyright (c) 2019-2025. R.A. James
 */

 // key: compiler.warn.inconsistent.white.space.indentation
 // key: compiler.warn.trailing.white.space.will.be.removed
 // options: -Xlint:text-blocks

class TextBlockWhitespace {
    String m() {
        return """
\u0009\u0009\u0009\u0009tab indentation
\u0020\u0020\u0020\u0020space indentation and trailing space\u0020
\u0020\u0020\u0020\u0020""";
    }
}
