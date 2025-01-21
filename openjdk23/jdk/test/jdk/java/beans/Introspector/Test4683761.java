/*
 * StarshipOS Copyright (c) 2012-2025. R.A. James
 */

/*
 * @test
 * @bug 4683761
 * @summary Tests that all public methods in a public class
 * @run main Test4683761
 */

import java.beans.PropertyDescriptor;
import java.util.HashMap;
import java.util.Map;

public class Test4683761 {
    public static void main(String[] args) throws Exception {
        Map<String, String> map = new HashMap<String, String>();
        map.put("key", "value");
        Map.Entry<String, String> entry = map.entrySet().iterator().next();
        for (PropertyDescriptor pd : BeanUtils.getPropertyDescriptors(entry.getClass())) {
            System.out.println(pd.getName() + " = " + pd.getReadMethod().invoke(entry));
        }
    }
}
