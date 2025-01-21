/*
 * StarshipOS Copyright (c) 2008-2025. R.A. James
 */

/*
 * @test
 * @bug 6397609
 * @summary Tests autocleaning
 * @author Sergey Malenkov
 * @modules java.compiler
 *          java.desktop
 *          jdk.compiler
 */

import java.beans.PropertyEditorManager;
import java.lang.ref.WeakReference;

public class Test6397609 {
    public static void main(String[] args) throws Exception {
        Class<?> targetClass = Object.class;
        Class<?> editorClass = new MemoryClassLoader().compile("Editor",
                "public class Editor extends java.beans.PropertyEditorSupport {}");
        PropertyEditorManager.registerEditor(targetClass, editorClass);

        // trigger a gc
        Object object = new Object();
        var r = new WeakReference<Object>(object);
        object = null;
        while (r.get() != null) {
            System.gc();
            Thread.sleep(100);
        }

        if (PropertyEditorManager.findEditor(targetClass) == null) {
            throw new Error("the editor is lost");
        }

        // allow, and wait for, Editor class to be unloaded
        var ref = new WeakReference<Class<?>>(editorClass);
        editorClass = null;
        while (ref.get() != null) {
            System.gc();
            Thread.sleep(100);
        }

        if (PropertyEditorManager.findEditor(targetClass) != null) {
            throw new Error("unexpected editor is found");
        }
    }
}
