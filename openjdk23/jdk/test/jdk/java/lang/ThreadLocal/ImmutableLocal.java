/*
 * StarshipOS Copyright (c) 2006-2025. R.A. James
 */

/*
 * @test
 * @bug     6387919
 * @summary Confirm ThreadLocal.set() usage is not a side effect of get()
 * @author  Pete Soper
 */
public class ImmutableLocal
{
    /**
     * {@link ThreadLocal} guaranteed to always return the same reference.
     */
    public abstract static class ImmutableThreadLocal extends ThreadLocal {
        public void set(final Object value) {
            throw new RuntimeException("ImmutableThreadLocal set called");
        }

        // force override
        protected abstract Object initialValue();
    }

    private static final ThreadLocal cache = new ImmutableThreadLocal() {
        public Object initialValue() {
            return Thread.currentThread().getName();
        }
    };

    public static void main(final String[] args) {
        System.out.println("cache.get() = " + cache.get());
    }
}
