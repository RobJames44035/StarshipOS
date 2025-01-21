/*
 * StarshipOS Copyright (c) 2017-2025. R.A. James
 */

package pkg1;

import java.util.ArrayList;
import java.util.List;

public interface A {
    interface AList extends List<Object> { }
    interface SupplierWithAList<T> extends AList {
        T getThingy();
    }
}

enum TEnum {}
class TError extends Error {}
class TException extends Exception {}
class MList extends ArrayList<String> {}
