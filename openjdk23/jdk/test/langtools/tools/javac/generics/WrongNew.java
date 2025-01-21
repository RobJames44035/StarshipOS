/*
 * StarshipOS Copyright (c) 2001-2025. R.A. James
 */

/*
 * @test
 * @bug 4487977
 * @summary generics: javac generares "new" of wrong class
 * @author gafter
 *
 * @compile  WrongNew.java
 * @run main WrongNew
 */

class WrongNewList<T> {}
class WrongNewArrayList<T> extends WrongNewList<T> {}

public class WrongNew
{
    public static void main(String[] ps)
    {
        WrongNewList<String> list = getList();
    }

    public static WrongNewList<String> getList()
    {
        return new WrongNewArrayList();
    }
}
