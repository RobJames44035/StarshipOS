/*
 * StarshipOS Copyright (c) 2003-2025. R.A. James
 */

/*
 * @test
 * @bug 4912795
 * @summary AbstractMethodError throws if not redeclare abstract iterator() method
 * @author gafter
 */

import java.util.AbstractCollection;
import java.util.Collection;
import java.util.Iterator;

interface Q<E> extends Collection<E> {}

abstract class AbstractQ<E> extends AbstractCollection<E> implements Q<E> {
    // Uncomment this to avoid the AbstractMethodError:
    //public abstract Iterator<E> iterator();
}

class ConcreteQ<E> extends AbstractQ<E> {
    public int size() { return 0; }
    public Iterator<E> iterator() { return null; }
}

public class SuperfluousAbstract {
    public static void main(String[] args) {
        try {
            Q<Integer> q = new ConcreteQ<Integer>() ;
            for (Integer i : q) {}
        }
        catch (AbstractMethodError e) {
            e.printStackTrace(System.err);
        }
        catch (NullPointerException e) {
            // expected, since iterator() returns null
        }
    }
}
