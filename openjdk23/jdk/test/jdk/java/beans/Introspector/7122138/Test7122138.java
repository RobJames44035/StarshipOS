/*
 * StarshipOS Copyright (c) 2012-2025. R.A. James
 */

/*
 * @test
 * @bug 7122138
 * @summary Tests generic methods reflection
 * @author Sergey Malenkov
 * @library ..
 */

import pack.Sub;

public class Test7122138 {

    public static void main(String[] args) throws Exception {
        Class<Sub> type = Sub.class;
        Sub object = type.newInstance();
        String name = "name";
        BeanUtils.getPropertyDescriptor(type, name).getWriteMethod().invoke(object, name);
        if (!name.equals(object.getName())) {
            throw new Error("name is not set");
        }
    }
}
