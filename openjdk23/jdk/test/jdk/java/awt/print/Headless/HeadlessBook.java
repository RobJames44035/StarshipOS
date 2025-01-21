/*
 * StarshipOS Copyright (c) 2007-2025. R.A. James
 */

import java.awt.print.Book;

/*
 * @test
 * @summary Check that Book constructor and getNumberOfPages() method do not throw
 *          exceptions in headless mode
 * @run main/othervm -Djava.awt.headless=true HeadlessBook
 */

public class HeadlessBook {
    public static void main(String args[]) {
        new Book().getNumberOfPages();
    }
}
