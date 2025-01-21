/*
 * StarshipOS Copyright (c) 2004-2025. R.A. James
 */

/*
 * @test
 * @bug 4933139
 * @summary StackOverflowError from javac
 * @author gafter
 *
 * @compile JsrRet.java
 */

package jsr.ret;

class T {
    {
        try {} finally {
        try {} finally {
        try {} finally {
        try {} finally {
        try {} finally {
        try {} finally {
        try {} finally {
        try {} finally {
        try {} finally {
        try {} finally {
        try {} finally {
        try {} finally {
        try {} finally {
        try {} finally {
        try {} finally {
        try {} finally {
        }
        }
        }
        }
        }
        }
        }
        }
        }
        }
        }
        }
        }
        }
        }
        }
    }
}
