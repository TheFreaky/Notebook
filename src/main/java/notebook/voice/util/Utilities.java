package notebook.voice.util;

import javax.sound.sampled.AudioFormat;

/**
 * This is Utilities class
 * Here you should set up
 */
public class Utilities {

    /**
     * AudiFormat
     */

    private static final float SAMPLE_MIC_RATE = 16000.0f;
    private static final float SAMPLE_AUDIO_RATE = 48000.0f;
    private static final int SAMPLE_SIZE_IN_BITS = 16;
    private static final int CHANNELS = 1;
    private static final boolean SIGNED = true;
    private static final boolean BIG_ENDIAN = false;

    public static AudioFormat getAudioFormat() {

        return new AudioFormat(SAMPLE_AUDIO_RATE, SAMPLE_SIZE_IN_BITS, CHANNELS, SIGNED, BIG_ENDIAN);
    }

    public static AudioFormat getMicFormat() {

        return new AudioFormat(SAMPLE_MIC_RATE, SAMPLE_SIZE_IN_BITS, CHANNELS, SIGNED, BIG_ENDIAN);
    }

}
