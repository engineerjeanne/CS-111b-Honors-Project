package gg.jeanne.HttpCalls;

import java.util.ArrayList;

/**
 * This class will be the class where I use HTTP requests to pull from BART's public RTD API.
 * I have made a Prediction class with standard getters/setters to store the data I pull from the API.
 * This will be used in the 'Main' class, initialised once, and constantly polled in the background.
 */
public class BartRTD {

    // https://api.bart.gov/api/etd.aspx?cmd=etd&orig=EMBR&plat=1&key=MW9S-E7SL-26DU-VV8V&json=y


    public BartRTD() {

    }

    public ArrayList<Prediction> getPredictions() {
        ArrayList<Prediction> predictions = new ArrayList<Prediction>();

        return predictions;
    }

}
