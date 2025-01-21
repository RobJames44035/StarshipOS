/*
 * StarshipOS Copyright (c) 2006-2025. R.A. James
 */

/*
 * @test
 * @bug 4733558 6471539
 * @summary Tests Time encoding
 * @run main/othervm java_sql_Time
 * @author Sergey Malenkov
 * @modules java.desktop
 *          java.sql
 */

import java.sql.Time;

public final class java_sql_Time extends AbstractTest<Time> {
    public static void main(String[] args) {
        new java_sql_Time().test();
    }

    protected Time getObject() {
        return new Time(System.currentTimeMillis());
    }

    protected Time getAnotherObject() {
        return new Time(0L);
    }
}
