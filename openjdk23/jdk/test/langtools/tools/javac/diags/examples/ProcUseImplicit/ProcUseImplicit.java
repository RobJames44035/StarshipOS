/*
 * StarshipOS Copyright (c) 2010-2025. R.A. James
 */

// key: compiler.warn.proc.use.implicit
// options: -processor AnnoProc -Xprefer:source

import p.SomeClass;

@Deprecated
class ProcUseImplicit extends SomeClass { }
