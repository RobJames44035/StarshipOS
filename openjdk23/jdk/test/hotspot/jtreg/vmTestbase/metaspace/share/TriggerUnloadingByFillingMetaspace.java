/*
 * StarshipOS Copyright (c) 2013-2025. R.A. James
 */
package metaspace.share;

import nsk.share.test.ExecutionController;
import nsk.share.gc.gp.classload.GeneratedClassProducer;

public class TriggerUnloadingByFillingMetaspace implements
        TriggerUnloadingHelper {

    private volatile boolean gotOOME = false;
    private ExecutionController stresser;
    private final ThreadLocal<GeneratedClassProducer> generatedClassProducer =
        new ThreadLocal<GeneratedClassProducer>() {
          @Override
          protected GeneratedClassProducer initialValue() {
            return new GeneratedClassProducer("metaspace.stressHierarchy.common.HumongousClass");
          }
        };

    private static boolean isInMetaspace(Throwable error) {
        return (error.getMessage().trim().toLowerCase().contains("metaspace"));
    }

    @Override
    public void triggerUnloading(ExecutionController stresser) {
        while (stresser.continueExecution() && !gotOOME) {
            try {
                generatedClassProducer.get().create(-100500); //argument is not used.
            } catch (Throwable oome) {
                if (!isInMetaspace(oome)) {
                    throw new HeapOOMEException("Got OOME in heap while triggering OOME in metaspace. Test result can't be valid.");
                }
                gotOOME = true;
            }
        }
    }
}
