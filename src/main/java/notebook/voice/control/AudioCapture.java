package notebook.voice.control;

import notebook.exceptions.AudioCaptureException;
import notebook.voice.util.Utilities;

import javax.sound.sampled.*;
import java.io.ByteArrayOutputStream;

public class AudioCapture implements Runnable {

    private static boolean isCapture;

    private static AudioFormat audioFormat;
    private static TargetDataLine targetDataLine;

    private static byte[] buffer;
    private static ByteArrayOutputStream out; //Thread safe stream

    //    private AudioCapture() throws AudioCaptureException {
    private AudioCapture() {
        try {
            audioFormat = Utilities.getMicFormat();
//            isCapture = true;
            targetDataLine = (TargetDataLine) AudioSystem.getLine(
                    new DataLine.Info(
                            TargetDataLine.class,
                            Utilities.getMicFormat()));

            targetDataLine.open(audioFormat);
//            targetDataLine.start();
//
//            buffer = new byte[(int) (audioFormat.getFrameSize() * audioFormat.getSampleRate())];
//
//            out = new ByteArrayOutputStream();
//
//            Thread t = new Thread(this);
//            t.start();
        } catch (LineUnavailableException e) {
//            throw new AudioCaptureException("Can't capture audio : " + e.getMessage());
        }
    }

    private static class AudioCaptureHolder {
        public static final AudioCapture INSTANCE_HOLDER = new AudioCapture();
    }

    public static AudioCapture getInstance() throws AudioCaptureException {
        AudioCapture audioCapture = AudioCaptureHolder.INSTANCE_HOLDER;
        audioFormat = Utilities.getMicFormat();
        isCapture = true;
        targetDataLine.start();

        buffer = new byte[(int) (audioFormat.getFrameSize() * audioFormat.getSampleRate())];

        out = new ByteArrayOutputStream();

        Thread t = new Thread(AudioCaptureHolder.INSTANCE_HOLDER);
        t.start();
        return audioCapture;
    }


    public boolean isCapture() {
        return isCapture;
    }

//    public AudioCapture() throws AudioCaptureException {
//
//        try {
//
//            audioFormat = Utilities.getMicFormat();
//            isCapture = true;
//            targetDataLine = (TargetDataLine) AudioSystem.getLine(
//                    new DataLine.Info(
//                            TargetDataLine.class,
//                            Utilities.getMicFormat()));
//
//            targetDataLine.open(audioFormat);
//            targetDataLine.start();
//
//            buffer = new byte[(int) (audioFormat.getFrameSize() * audioFormat.getSampleRate())];
//
//            out = new ByteArrayOutputStream();
//
//            Thread t = new Thread(this);
//            t.start();
//        } catch (LineUnavailableException e) {
//            throw new AudioCaptureException("Can't capture audio : " + e.getMessage());
//        }
//    }


    public byte[] getAudioBytes() {
        return out.toByteArray();
    }


    public void setCapture(boolean capture) {
        isCapture = capture;
    }


    private void writeData() {
        int count = targetDataLine.read(buffer, 0, buffer.length);
        if (count > 0) {
            out.write(buffer, 0, count);
        }
    }


    @Override
    public void run() {

        while (isCapture) {

            writeData();
        }

    }


}