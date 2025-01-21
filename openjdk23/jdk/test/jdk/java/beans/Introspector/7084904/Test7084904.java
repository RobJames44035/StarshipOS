/*
 * StarshipOS Copyright (c) 2014-2025. R.A. James
 */
import bean.Derived;
import java.lang.reflect.Method;
/*
 * @test
 * @bug 7084904
 * @summary Compares reflection and bean introspection
 * @library ..
 * @run main Test7084904
 * @author Sergey Malenkov
 */
public class Test7084904 {
    public static void main(String[] args) throws Exception {
        Derived bean = new Derived();
        Class<?> type = bean.getClass();
        Method method1 = test("reflection", bean, type.getMethod("isAllowed"));
        Method method2 = test("bean introspection", bean, BeanUtils.getPropertyDescriptor(type, "allowed").getReadMethod());
        if (!method1.equals(method2)) {
            throw new Error("first method is not equal to the second one");
        }
    }

    private static Method test(String name, Object bean, Method method) throws Exception {
        System.out.println("\n === use " + name + " ===");
        System.out.println(method);
        System.out.println("declaring " + method.getDeclaringClass());
        System.out.println("invocation result: " + method.invoke(bean));
        return method;
    }
}
