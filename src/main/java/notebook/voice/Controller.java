package notebook.voice;

import notebook.exceptions.AudioCaptureException;
import notebook.exceptions.AudioPlayException;
import notebook.exceptions.RecognitionSpeechException;
import notebook.exceptions.TextRecognitionException;
import notebook.voice.control.AudioCapture;
import notebook.voice.control.AudioPlay;

import java.util.ArrayList;

public class Controller {

    private static AudioPlay audioPlay;
    private static AudioCapture audioCapture;



    public static void record() throws AudioCaptureException {
        if (audioPlay != null && audioPlay.isAudioPlay()) {
            throw new AudioCaptureException("At the moment sound playing in");
        }

//        audioCapture = new AudioCapture();
        audioCapture = AudioCapture.getInstance();
    }

    public static void play(String text) throws AudioPlayException, TextRecognitionException {
        if (text == null || text.equals("")) {
            throw new TextRecognitionException("Too few symbols in text");
        }

        //waiting for the audio bytes
        byte[] bytes = SpeechKit.sendGET(text);
        System.out.println("Send");

        audioPlay = new AudioPlay(bytes);
        System.out.println("Play");

    }

    public static ArrayList<String> recognize() throws RecognitionSpeechException {
        if (audioCapture.isCapture()) {
            throw new RecognitionSpeechException("Audio is capturing now");
        }

        if (audioCapture == null) {
            throw new RecognitionSpeechException("Audio isn't captured");
        }

        byte[] data = audioCapture.getAudioBytes();

        if (data.length > 0) {
            return SpeechKit.sendPOST(data);
        } else {
            throw new RecognitionSpeechException("Recorded audio is empty");
        }
    }

    public static void stopCapture() {
        audioCapture.setCapture(false);
    }
}
