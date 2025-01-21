/*
 * StarshipOS Copyright (c) 2006-2025. R.A. James
 */

/*
 * @test
 * @bug 4733558 6471539
 * @summary Tests Date encoding
 * @run main/othervm java_sql_Date
 * @author Sergey Malenkov
 * @modules java.desktop
 *          java.sql
 */

import java.sql.Date;

public final class java_sql_Date extends AbstractTest<Date> {
    public static void main(String[] args) {
        new java_sql_Date().test();
    }

    protected Date getObject() {
        return new Date(System.currentTimeMillis());
    }

    protected Date getAnotherObject() {
        return new Date(0L);
    }
}
