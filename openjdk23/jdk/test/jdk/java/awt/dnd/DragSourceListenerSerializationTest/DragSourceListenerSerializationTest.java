/*
 * StarshipOS Copyright (c) 2014-2025. R.A. James
 */

/*
  @test
  @key headful
  @bug 4422345 8039083
  @summary tests serialization of DragSourceListeners
  @author das@sparc.spb.su area=dnd
  @library /test/lib
  @build jdk.test.lib.Asserts
  @run main/othervm DragSourceListenerSerializationTest
*/

import java.awt.Button;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DragGestureEvent;
import java.awt.dnd.DragGestureRecognizer;
import java.awt.dnd.DragSource;
import java.awt.dnd.DragSourceAdapter;
import java.awt.dnd.DragSourceContext;
import java.awt.dnd.DragSourceListener;
import java.awt.dnd.DragSourceMotionListener;
import java.awt.event.InputEvent;
import java.awt.event.MouseEvent;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Arrays;
import java.util.TooManyListenersException;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static jdk.test.lib.Asserts.assertEquals;

public class DragSourceListenerSerializationTest {
    public static void main(String[] args) throws Exception {
        DragSource ds = new DragSource();
        TestDragSourceAdapter dsa1 = new TestDragSourceAdapter(1);
        TestDragSourceAdapter dsa2 = new TestDragSourceAdapter(2);
        Component c = new Button();
        DragGestureRecognizer dgr = ds.createDefaultDragGestureRecognizer(c,
                DnDConstants.ACTION_COPY,
                e -> e.startDrag(null, null));
        MouseEvent me = new MouseEvent(c, MouseEvent.MOUSE_PRESSED, 0,
                InputEvent.CTRL_MASK, 100, 100, 0, false);
        DragGestureEvent dge = new DragGestureEvent(dgr, DnDConstants.ACTION_COPY,
                new Point(100, 100),
                Arrays.asList(me));
        DragSourceContext dsc = new DragSourceContext(
                dge,
                new Cursor(Cursor.HAND_CURSOR),
                null, null, new StringSelection("TEXT"), null);

        ds.addDragSourceListener(dsa1);
        ds.addDragSourceListener(dsa2);
        ds.addDragSourceListener(dsa2);
        ds.addDragSourceMotionListener(dsa1);
        ds.addDragSourceMotionListener(dsa1);
        ds.addDragSourceMotionListener(dsa2);
        dsc.addDragSourceListener(dsa2);

        byte[] serialized;
        try (ByteArrayOutputStream bos = new ByteArrayOutputStream();
             ObjectOutputStream oos = new ObjectOutputStream(bos)) {
            oos.writeObject(dsc);
            serialized = bos.toByteArray();
        }

        DragSourceContext dsc_copy;
        try (ByteArrayInputStream bis = new ByteArrayInputStream(serialized);
             ObjectInputStream ois = new ObjectInputStream(bis)) {
            dsc_copy = (DragSourceContext) ois.readObject();
        }

        try {
            dsc_copy.addDragSourceListener(dsa1);
            throw new RuntimeException("Test failed. Listener addition succeeded");
        } catch (TooManyListenersException ignored) {
        }

        try {
            dsc_copy.addDragSourceListener(dsa2);
            throw new RuntimeException("Test failed. Listener addition succeeded");
        } catch (TooManyListenersException ignored) {
        }

        try (ByteArrayOutputStream bos = new ByteArrayOutputStream();
             ObjectOutputStream oos = new ObjectOutputStream(bos)) {
            oos.writeObject(ds);
            serialized = bos.toByteArray();
        }

        DragSource ds_copy;
        try (ByteArrayInputStream bis = new ByteArrayInputStream(serialized);
             ObjectInputStream ois = new ObjectInputStream(bis)) {
             ds_copy = (DragSource) ois.readObject();
        }

        DragSourceListener[] dsls = ds_copy.getDragSourceListeners();
        assertEquals(3, dsls.length, "DragSourceListeners number");
        assertEquals(1, Stream.of(dsls).filter(dsa1::equals).collect(Collectors.counting()).intValue());
        assertEquals(2, Stream.of(dsls).filter(dsa2::equals).collect(Collectors.counting()).intValue());

        DragSourceMotionListener[] dsmls = ds_copy.getDragSourceMotionListeners();
        assertEquals(3, dsmls.length, "DragSourceMotionListeners number");
        assertEquals(2, Stream.of(dsmls).filter(dsa1::equals).collect(Collectors.counting()).intValue());
        assertEquals(1, Stream.of(dsmls).filter(dsa2::equals).collect(Collectors.counting()).intValue());
    }
}

class TestDragSourceAdapter extends DragSourceAdapter implements Serializable {
    final int id;

    TestDragSourceAdapter(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public boolean equals(Object obj) {
        if (obj instanceof TestDragSourceAdapter) {
            TestDragSourceAdapter tdsa = (TestDragSourceAdapter) obj;
            return tdsa.getId() == getId();
        }
        return false;
    }
}
