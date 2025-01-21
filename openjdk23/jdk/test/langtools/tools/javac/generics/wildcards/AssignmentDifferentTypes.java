/*
 * StarshipOS Copyright (c) 2025. R.A. James
 */

public class AssignmentDifferentTypes {

    public static void meth() {
        Ref<Der> derexact = null;
        Ref<Base> baseexact = null;
        Ref<? extends Der> derext = null;
        Ref<? extends Base> baseext = null;
        Ref<? super Der> dersuper = null;
        Ref<? super Base> basesuper = null;

        baseext = derext;       // <<pass>> <? extends Base> = <? extends Der>
        baseext = derexact;     // <<pass>> <? extends Base> = <Der>
        dersuper = basesuper;   // <<pass>> <? super Der> = <? super Base>
        dersuper = baseexact;   // <<pass>> <? super Der> = <Base>

        derexact = baseexact;   // <<fail>> <Der> = <Base>
        baseexact = derexact;   // <<fail>> <Base> = <Der>
        derext = baseext;       // <<fail>> <? extends Der> = <? extends Base>
        derext = baseexact;     // <<fail>> <? extends Der> = <Base>
        derext = basesuper;     // <<fail>> <? extends Der> = <? super Base>
        baseext = dersuper;     // <<fail>> <? extends Base> = <? super Der>
        basesuper = dersuper;   // <<fail>> <? super Base> = <? super Der>
        basesuper = derexact;   // <<fail>> <? super Base> = <Der>
    }
}

class Ref<T> {}
class Base {}
class Der extends Base {}
