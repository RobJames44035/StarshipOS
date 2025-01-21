/*
 * StarshipOS Copyright (c) 2002-2025. R.A. James
 */

import javax.sound.midi.Instrument;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.Soundbank;
import javax.sound.midi.Synthesizer;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Mixer;

/**
 * @test
 * @key sound
 * @bug 4429762
 * @summary Some instrument names in some soundbanks include bad extra characters
 */
public class ExtraCharInSoundbank {

    private static void printName(String loadedName)
    {
        System.out.println("Loaded Name: " + loadedName);
        byte[] theLoadedNameByteArray = loadedName.getBytes();

        System.out.print("Name Bytes: ");
        for(int i = 0; i < theLoadedNameByteArray.length; i++)
            System.out.print((Integer.toHexString((int)theLoadedNameByteArray[i]).toUpperCase()) + " ");
        System.out.println("");
        System.out.println("");
    }

    private static boolean containsControlChar(String name) {
        byte[] bytes = name.getBytes();
        for (int i = 0; i < bytes.length; i++) {
            if (bytes[i] < 32) {
                return true;
            }
        }
        return false;
    }

    public static boolean checkInstrumentNames(Synthesizer theSynthesizer)
    {
        boolean containsControlCharacters = false;

        Instrument[] theLoadedInstruments = theSynthesizer.getLoadedInstruments();

        System.out.println("Checking soundbank...");
        for(int theInstrumentIndex = 0; theInstrumentIndex < theLoadedInstruments.length; theInstrumentIndex++) {
            String name = theLoadedInstruments[theInstrumentIndex].getName();
            if (containsControlChar(name)) {
                containsControlCharacters = true;
                System.out.print("Instrument[" + theInstrumentIndex + "] contains unexpected control characters: ");
                printName(name);
            }
        }
        return !containsControlCharacters;
    }

    public static void main(String[] args) throws Exception {
        // the internal synthesizer needs a soundcard to work properly
        if (!isSoundcardInstalled()) {
            return;
        }
        Synthesizer theSynth = MidiSystem.getSynthesizer();
        System.out.println("Got synth: "+theSynth);
        theSynth.open();
        try {
            Soundbank theSoundbank = theSynth.getDefaultSoundbank();
            System.out.println("Got soundbank: "+theSoundbank);
            theSynth.loadAllInstruments(theSoundbank);
            try {
                    if (!checkInstrumentNames(theSynth)) {
                            throw new Exception("Test failed");
                    }
            } finally {
                    theSynth.unloadAllInstruments(theSoundbank);
            }
        } finally {
            theSynth.close();
        }
        System.out.println("Test passed.");
    }

    /**
     * Returns true if at least one soundcard is correctly installed
     * on the system.
     */
    public static boolean isSoundcardInstalled() {
        boolean result = false;
        try {
            Mixer.Info[] mixers = AudioSystem.getMixerInfo();
            if (mixers.length > 0) {
                result = AudioSystem.getSourceDataLine(null) != null;
            }
        } catch (Exception e) {
            System.err.println("Exception occured: "+e);
        }
        if (!result) {
            System.err.println("Soundcard does not exist or sound drivers not installed!");
            System.err.println("This test requires sound drivers for execution.");
        }
        return result;
    }
}
