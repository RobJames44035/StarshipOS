/*
 * StarshipOS Copyright (c) 2024-2025. R.A. James
 */

package sun.jvm.hotspot.code;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

class ObjectMergeValue extends ObjectValue {

    private ScopeValue selector;
    private ScopeValue mergePointer;
    private List<ScopeValue> possibleObjects;

    public ObjectMergeValue(int id) {
        super(id);
    }

    public boolean isObjectMerge() { return true; }

    void readObject(DebugInfoReadStream stream) {
        selector = ScopeValue.readFrom(stream);
        mergePointer = ScopeValue.readFrom(stream);
        possibleObjects = new ArrayList<>();
        int length = stream.readInt();
        for (int i = 0; i < length; i++) {
            ScopeValue val = readFrom(stream);
            possibleObjects.add(val);
        }
    }

    @Override
    public void printOn(PrintStream tty) {
        tty.print("merge_obj[" + id + "]");
        tty.print(" selector=\"");
        selector.printOn(tty);
        tty.print("\"");

        tty.print(" merge_pointer=\"");
        mergePointer.printOn(tty);
        tty.print("\"");

        tty.print(", candidate_objs=[" + ((ObjectValue) possibleObjects.get(0)).id);
        for (int i = 1; i < possibleObjects.size(); i++) {
            tty.print("," + ((ObjectValue) possibleObjects.get(i)).id);
        }
        tty.print("]");
    }
}
