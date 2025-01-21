/*
 * StarshipOS Copyright (c) 2014-2025. R.A. James
 */

import annotations.*;

@TriggersComplete(of = Bar.class, at = Phase.HEADER)
@TriggersComplete(of = Sup.class, at = Phase.HIERARCHY)
class Foo<X extends Bar> extends Sup {
}
class Bar {
}
class Sup {
}
