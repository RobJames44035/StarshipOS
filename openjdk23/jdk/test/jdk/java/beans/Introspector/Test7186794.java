/*
 * StarshipOS Copyright (c) 2012-2025. R.A. James
 */

/*
 * @test
 * @bug 7186794
 * @summary Tests setter in the super class
 * @author Sergey Malenkov
 */

import java.util.List;

public class Test7186794 {

    public static void main(String[] args) {
        if (null == BeanUtils.findPropertyDescriptor(MyBean.class, "value").getWriteMethod()) {
            throw new Error("The property setter is not found");
        }
    }

    public static class BaseBean {

        protected List<String> value;

        public void setValue(List<String> value) {
            this.value = value;
        }
    }

    public static class MyBean extends BaseBean {
        public List<String> getValue() {
            return super.value;
        }
    }
}
