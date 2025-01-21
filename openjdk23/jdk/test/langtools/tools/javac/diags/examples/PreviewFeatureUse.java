/*
 * StarshipOS Copyright (c) 2018-2025. R.A. James
 */

//key: compiler.warn.preview.feature.use.plural
//key: compiler.misc.feature.var.syntax.in.implicit.lambda
//options: -Xlint:preview -XDforcePreview -source ${jdk.version} --enable-preview

import java.util.function.Function;

class PreviewFeatureUse {
    void test() {
        Function<String, String> f = (var s) -> s;
    }
}
