/*
 * StarshipOS Copyright (c) 2006-2025. R.A. James
 */

/*
 * @test
 * @bug 5023552
 * @summary Tests reference count updating
 * @run main/othervm Test5023552
 * @author Sergey Malenkov
 */

import java.beans.Encoder;
import java.beans.Expression;
import java.beans.PersistenceDelegate;
import java.beans.XMLEncoder;

public final class Test5023552 extends AbstractTest {
    public static void main(String[] args) {
        new Test5023552().test();
    }

    protected Object getObject() {
        Component component = new Component();
        return component.create(component);
    }

    protected void initialize(XMLEncoder encoder) {
        encoder.setPersistenceDelegate(Container.class, new PersistenceDelegate() {
            protected Expression instantiate(Object oldInstance, Encoder out) {
                Container container = (Container) oldInstance;
                Component component = container.getComponent();
                return new Expression(container, component, "create", new Object[] {component});
            }
        });
    }

    public static final class Component {
        public Container create(Component component) {
            return new Container(component);
        }
    }

    public static final class Container {
        private final Component component;

        public Container(Component component) {
            this.component = component;
        }

        public Component getComponent() {
            return this.component;
        }
    }
}
