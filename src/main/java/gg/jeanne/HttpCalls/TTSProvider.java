package gg.jeanne.HttpCalls;

import com.voicerss.tts.AudioCodec;
import com.voicerss.tts.AudioFormat;
import com.voicerss.tts.Languages;
import com.voicerss.tts.SpeechDataEvent;
import com.voicerss.tts.SpeechDataEventListener;
import com.voicerss.tts.SpeechErrorEvent;
import com.voicerss.tts.SpeechErrorEventListener;
import com.voicerss.tts.VoiceParameters;
import com.voicerss.tts.VoiceProvider;

import javax.sound.sampled.*;
import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.HashMap;

public class TTSProvider {
    private final HashMap<String, String> LINE_ABBREVIATIONS;
    private final HashMap<String, String> STATION_ABBREVIATIONS;

    public TTSProvider() {
        this.LINE_ABBREVIATIONS = new HashMap<>();
        this.STATION_ABBREVIATIONS = new HashMap<>();

        this.LINE_ABBREVIATIONS.put("RD", "red");
        this.LINE_ABBREVIATIONS.put("OR", "orange");
        this.LINE_ABBREVIATIONS.put("YL", "yellow");
        this.LINE_ABBREVIATIONS.put("GN", "green");
        this.LINE_ABBREVIATIONS.put("BL", "blue");
        this.LINE_ABBREVIATIONS.put("PR", "purple");
        this.LINE_ABBREVIATIONS.put("null", "unknown");

        this.STATION_ABBREVIATIONS.put("ANTC", "Antioch");
        this.STATION_ABBREVIATIONS.put("BERY", "Berryessa");
        this.STATION_ABBREVIATIONS.put("RICH", "Richmond");
        this.STATION_ABBREVIATIONS.put("DUBL", "Dublin Pleasanton");
        this.STATION_ABBREVIATIONS.put("DALY", "Daly City");
        this.STATION_ABBREVIATIONS.put("MLBR", "Millbrae");
        this.STATION_ABBREVIATIONS.put("SFIA", "San Francisco Airport");
        this.STATION_ABBREVIATIONS.put("PITT", "Pittsburg Bay Point");
    }

    public void playTTS(ArrayList<Prediction> predictions) {
        VoiceProvider tts = new VoiceProvider("74af2dba616a4940b8bad81b27464219");
        StringBuilder message = new StringBuilder();

        for(Prediction prediction : predictions) {
            // Ths was actually on purpose btw, with the "minute". That's how BART TTS is.
            message.append("{CAR} car, {LINE} line train to {DEST} in {MIN} minute. "
                    .replace("{CAR}", String.valueOf(prediction.getCarriages()))
                    .replace("{LINE}", this.LINE_ABBREVIATIONS.get(prediction.getLine()) != null ? this.LINE_ABBREVIATIONS.get(prediction.getLine()) : "unknown")
                    .replace("{DEST}", this.STATION_ABBREVIATIONS.get(prediction.getDestination()))
                    .replace("{MIN}", String.valueOf(prediction.getPredictions(1)))
            );
        }

        VoiceParameters params = new VoiceParameters(
                message.toString(),
                Languages.English_UnitedStates);
        params.setCodec(AudioCodec.WAV);
        params.setVoice("John");
        params.setBase64(false);
        params.setSSML(false);
        params.setRate(0);

        tts.addSpeechErrorEventListener(new SpeechErrorEventListener() {
            @Override
            public void handleSpeechErrorEvent(SpeechErrorEvent e) {
                System.err.println("Error: " + e.getException().getMessage());
            }
        });

        tts.addSpeechDataEventListener(new SpeechDataEventListener() {
            @Override
            public void handleSpeechDataEvent(SpeechDataEvent e) {
                try {
                    byte[] voice = (byte[]) e.getData();

                    // Play the audio directly
                    playAudio(voice);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });

        tts.speechAsync(params);
    }

    private static void playAudio(byte[] audioData) throws Exception {
        try (ByteArrayInputStream bais = new ByteArrayInputStream(audioData);
             AudioInputStream audioStream = AudioSystem.getAudioInputStream(bais)) {

            javax.sound.sampled.AudioFormat format = audioStream.getFormat();
            DataLine.Info info = new DataLine.Info(SourceDataLine.class, format);

            if (!AudioSystem.isLineSupported(info)) {
                throw new UnsupportedAudioFileException("Audio format not supported");
            }

            SourceDataLine audioLine = (SourceDataLine) AudioSystem.getLine(info);
            audioLine.open(format);
            audioLine.start();

            byte[] buffer = new byte[4096];
            int bytesRead;

            while ((bytesRead = audioStream.read(buffer)) != -1) {
                audioLine.write(buffer, 0, bytesRead);
            }

            audioLine.drain();
            audioLine.stop();
            audioLine.close();
        }
    }
}
