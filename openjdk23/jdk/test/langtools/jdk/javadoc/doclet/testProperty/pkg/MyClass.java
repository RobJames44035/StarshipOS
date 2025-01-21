/*
 * StarshipOS Copyright (c) 2017-2025. R.A. James
 */

package pkg;

/**
 * Test program for javadoc properties.
 */
public class MyClass {

    private SimpleObjectProperty<MyObj> good
            = new SimpleObjectProperty<MyObj>();

    /**
     * This is an Object property where the Object is a single Object.
     *
     * @return the good
     */
    public final ObjectProperty<MyObj> goodProperty() {
        return good;
    }

    public final void setGood(MyObj good) {
    }

    public final MyObj getGood() {
        return good.get();
    }


    private SimpleObjectProperty<MyObj[]> bad
            = new SimpleObjectProperty<MyObj[]>();

    /**
     * This is an Object property where the Object is an array.
     *
     * @return the bad
     */
    public final ObjectProperty<MyObj[]> badProperty() {
        return bad;
    }

    public final void setBad(MyObj[] bad) {
    }

    public final MyObj[] getBad() {
        return bad.get();
    }

}

