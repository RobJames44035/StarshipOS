/*
 * StarshipOS Copyright (c) 2006-2025. R.A. James
 */

/*
 * @test
 * @bug     6370653
 * @summary Use iinc opcode for postfix decrement of local variable
 * @run main T6370653
 */

public class T6370653 {
    static boolean test() {
        int x = 10;
        int y = x--;
        // check whether generated code still works correctly
        return (x == 9) && (y == 10);
    }

    public static void main(String[] args) {
        if (!test()) {
            throw new Error("Test failed.");
        }
    }
}
