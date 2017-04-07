package notebook.voice.control;

import notebook.exceptions.AudioPlayException;
import notebook.voice.util.Utilities;

import javax.sound.sampled.*;
import java.io.ByteArrayInputStream;
import java.io.IOException;

/**
 * This class plays audio
 */

public class AudioPlay implements Runnable {

    private static boolean isAudioPlay;


    private AudioInputStream audioInputStream;
    private AudioFormat audioFormat;
    private SourceDataLine sourceDataLine;


    public AudioPlay(byte[] audioBytes) throws AudioPlayException {

        try {
            while (isAudioPlay) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            audioFormat = Utilities.getAudioFormat();

            int length = audioBytes.length / audioFormat.getFrameSize();

            audioInputStream = new AudioInputStream(new ByteArrayInputStream(audioBytes), audioFormat, length);

            sourceDataLine = (SourceDataLine) AudioSystem.getLine(new DataLine.Info(SourceDataLine.class, Utilities.getAudioFormat()));

            sourceDataLine.open(audioFormat);

            sourceDataLine.start();

            isAudioPlay = true;

            Thread t = new Thread(this);
            t.start();

        } catch (LineUnavailableException e) {
            throw new AudioPlayException("Can't play audio : " + e.getMessage());
        }
    }


    public boolean isAudioPlay() {
        return isAudioPlay;
    }

    @Override
    public void run() {

        byte buffer[] = new byte[(int) audioFormat.getSampleRate() * audioFormat.getFrameSize()];

        try {

            int count;
            while ((count = audioInputStream.read(buffer, 0, buffer.length)) > 0) {

                if (count > 0) {
                    sourceDataLine.write(buffer, 0, count);
                }

            }

            isAudioPlay = false;


        } catch (IOException e) {
            isAudioPlay = false;
            e.printStackTrace();
        }

        sourceDataLine.drain();

        sourceDataLine.close();
    }
}
