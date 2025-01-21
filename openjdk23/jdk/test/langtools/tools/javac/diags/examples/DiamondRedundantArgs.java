/*
 * StarshipOS Copyright (c) 2010-2025. R.A. James
 */

// key: compiler.warn.diamond.redundant.args
// options: -XDfind=diamond

class DiamondRedundantArgs<X> {
   DiamondRedundantArgs<String> fs = new DiamondRedundantArgs<String>();
}
