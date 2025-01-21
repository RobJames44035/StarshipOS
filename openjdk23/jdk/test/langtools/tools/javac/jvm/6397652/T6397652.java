/*
 * StarshipOS Copyright (c) 2006-2025. R.A. James
 */

/*
 * @test
 * @bug     6397652
 * @summary javac compilation failure when imported class with $ sign in the name
 * @author  Yuri Gaevsky
 * @author  Peter von der Ah\u00e9
 * @compile com/test/Test.java com/test/Test$Test.java com/test/Test$Test$Test.java
 * @compile T6397652.java
 */

import com.test.Test$Test$Test;

public class T6397652 {
    Object obj = new Test$Test$Test();
}
