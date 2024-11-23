package gg.jeanne;

import gg.jeanne.HttpCalls.BartRTD;
import gg.jeanne.HttpCalls.Prediction;
import gg.jeanne.HttpCalls.TTSProvider;

import java.util.ArrayList;
import java.util.Comparator;

import static gg.jeanne.StationAbbreviation.getStationFromAbbrev;

public class Main {

    /**
     * Right now, the Main class is really basic.
     * In the future, I will ask the user to input a station code, and the platform.
     * This Main class will be the core of the program, where everything starts, and where everything happens.
     * The BartRTD class will be initiated along with the existing GUI object, and two separate runnables will be created here.
     * @param args: Command Line Arguments
     */
    public static void main(String[] args) {
        GUI gui = new GUI();
        DotMatrixBoard dotMatrixBoard = gui.getDotMatrixBoard();
        BartRTD predictions = new BartRTD();
        TTSProvider ttsProvider = new TTSProvider();

        new Thread(((Runnable) () -> runRecursivePredictionTask(predictions, dotMatrixBoard, ttsProvider))).start();
    }

    public static void runRecursivePredictionTask(BartRTD predictionsHandler, DotMatrixBoard dotMatrixBoard, TTSProvider ttsProvider) {
        ArrayList<Prediction> predictions = predictionsHandler.getPredictions();
        ArrayList<String> test = new ArrayList<>();
        test.add("<hr4/>");

        predictions.sort(Comparator.comparingInt(a -> a.getPredictions(1)));

        for (Prediction prediction : predictions) {
            if(test.size() == 8) continue;
//            System.out.println(prediction.toString());

            test.add("{DEST}<split/> {MIN_1}{MIN_2} MIN"
                    .replace("{DEST}", getStationFromAbbrev(prediction.getDestination().toLowerCase()))
                    .replace("{MIN_1}", String.valueOf(prediction.getPredictions(1)))
                    .replace("{MIN_2}", prediction.getPredictions(2) != 0 ? ", " + prediction.getPredictions(2) : "")
            );
            test.add("{LEN}-CAR, {LINE}-LINE"
                    .replace("{LEN}", String.valueOf(prediction.getCarriages()))
                    .replace("{LINE}", prediction.getLine() != null ? prediction.getLine() : "")
            );
            test.add("<hr4/>");
        }

        new Thread(() -> ttsProvider.playTTS(predictions)).start();
        dotMatrixBoard.addToQueue(test);

        try {
            Thread.sleep(30000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        runRecursivePredictionTask(predictionsHandler, dotMatrixBoard, ttsProvider);
    }

}
