/*
 * StarshipOS Copyright (c) 2018-2025. R.A. James
 */


// key: compiler.note.verbose.l2m.deduplicate
// options: -g:none --debug=dumpLambdaToMethodDeduplication

import java.util.function.Function;

public class LambdaDeduplicate {
  Function<Integer, Integer> f1 = x -> x;
  Function<Integer, Integer> f2 = x -> x;
}
