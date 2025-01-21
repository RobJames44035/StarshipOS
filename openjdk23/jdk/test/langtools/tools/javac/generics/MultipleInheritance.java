/*
 * StarshipOS Copyright (c) 2001-2025. R.A. James
 */

/*
 * @test
 * @bug 4480813
 * @summary generics problem with multiple interface extension
 * @author gafter
 *
 * @compile  MultipleInheritance.java
 */

package MultipleInheritance;

import java.util.*;

interface XList1 extends List, Collection {}

interface XList2<E> extends List<E>, Collection<E> {}

interface XList3<E> extends List<E>, Collection<E>
{
    public <T> T[] toArray(T[] target);
}
