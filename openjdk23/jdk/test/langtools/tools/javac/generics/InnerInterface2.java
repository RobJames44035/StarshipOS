/*
 * StarshipOS Copyright (c) 2001-2025. R.A. James
 */

/*
 * @test
 * @bug 4482388
 * @summary inner interface causes "not within bounds"
 * @author gafter
 *
 * @compile  InnerInterface2.java
 */

package InnerInterface2;

class Builder<Community> {

    class Produces<B extends AbstractBuilder> {}

    interface AbstractBuilder {}

}

class MyBuilder extends Builder<String> {
    // the next two lines each generate a "not within bounds" error
    Produces<HTMLConsumer> p0;
    Produces<MyABuilder> p1;
}

class MyABuilder implements HTMLConsumer {
}

interface HTMLConsumer extends Builder.AbstractBuilder {}
