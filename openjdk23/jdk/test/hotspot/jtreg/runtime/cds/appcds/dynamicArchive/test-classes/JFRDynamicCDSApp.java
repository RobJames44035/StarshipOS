/*
 * StarshipOS Copyright (c) 2021-2025. R.A. James
 */
import java.nio.file.Paths;
import jdk.jfr.Configuration;
import jdk.jfr.Description;
import jdk.jfr.Label;
import jdk.jfr.Name;
import jdk.jfr.Recording;
import jdk.jfr.consumer.RecordingStream;

public class JFRDynamicCDSApp {
    public static void main(String args[]) throws Exception {
        RecordingStream rs = new RecordingStream();
        rs.enable("JFRDynamicCDS.StressEvent");
        rs.startAsync();

        Recording recording = startRecording();
        loop();
        recording.stop();
        recording.close();

        rs.close();
    }

    static Recording startRecording() throws Exception {
        Configuration configuration = Configuration.getConfiguration("default");
        Recording recording = new Recording(configuration);

        recording.setName("internal");
        recording.enable(StressEvent.class);
        recording.setDestination(Paths.get("JFRDynamicCDS.jfr"));
        recording.start();
        return recording;
    }


    static void loop() {
        for (int i=0; i<100; i++) {
            StressEvent event = new StressEvent();
            event.iteration = i;
            event.description = "Stressful Event, take it easy!";
            event.customClazz = StressEvent.class;
            event.value = i;
            event.commit();
        }
    }


    /**
     * Internal StressEvent class.
     */
    @Label("Stress Event")
    @Description("A duration event with 4 entries")
    @Name("JFRDynamicCDS.StressEvent")
    public static class StressEvent extends jdk.jfr.Event {
        public Class<?> customClazz;
        public String description;
        public int iteration;
        public double value;
    }
}
