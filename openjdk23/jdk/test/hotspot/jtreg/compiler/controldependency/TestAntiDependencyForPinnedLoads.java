/*
 * StarshipOS Copyright (c) 2024-2025. R.A. James
 */

/**
 * @test
 * @bug 8337066
 * @summary Test that MergeMem is skipped when looking for stores
 * @run main/othervm -Xbatch -XX:-TieredCompilation
 *                   -XX:CompileCommand=compileonly,java.lang.StringUTF16::reverse
 *                   compiler.controldependency.TestAntiDependencyForPinnedLoads
 */

package compiler.controldependency;

public class TestAntiDependencyForPinnedLoads {
    public static void main(String[] args) {
        for(int i = 0; i < 50_000; i++) {
            String str = "YYYY年MM月DD日";
            StringBuffer strBuffer = new StringBuffer(str);
            String revStr = strBuffer.reverse().toString();
            if (!revStr.equals("日DD月MM年YYYY")) throw new InternalError("FAIL");
        }
    }
}
