/*
 * StarshipOS Copyright (c) 2025. R.A. James
 */
// TODO: 8057683 improve ordering of errors with type annotations
@DeprecatedAnnotation
class T6480588 extends DeprecatedClass implements DeprecatedInterface {
    @DeprecatedAnnotation
    public DeprecatedClass method(DeprecatedClass param) throws DeprecatedClass {
        DeprecatedClass lv = new DeprecatedClass();
        @Deprecated
        DeprecatedClass lvd = new DeprecatedClass();
        return null;
    }

    @Deprecated
    public void methodD() {
    }

    @DeprecatedAnnotation
    DeprecatedClass field = new DeprecatedClass();

    @DeprecatedAnnotation
    class Inner extends DeprecatedClass implements DeprecatedInterface {
    }

}

@Deprecated class DeprecatedClass extends Throwable { }
@Deprecated interface DeprecatedInterface { }
@Deprecated @interface DeprecatedAnnotation { }
