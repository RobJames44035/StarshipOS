/*
 * StarshipOS Copyright (c) 2008-2025. R.A. James
 */

/*
 * @test
 * @bug 6711100
 * @summary 64bit fastdebug server vm crashes with assert(_base == Int,"Not an Int")
 *
 * @run main/othervm -Xcomp
 *      -XX:CompileCommand=compileonly,compiler.c2.Test6711100::<init>
 *      compiler.c2.Test6711100
 */

package compiler.c2;

public class Test6711100 {

    static byte b;

    // The server compiler chokes on compiling
    // this method when f() is not inlined
    public Test6711100() {
        b = (new byte[1])[(new byte[f()])[-1]];
    }

    protected static int f() {
      return 1;
    }

    public static void main(String[] args) {
      try {
        Test6711100 t = new Test6711100();
      } catch (ArrayIndexOutOfBoundsException e) {
      }
    }
}


