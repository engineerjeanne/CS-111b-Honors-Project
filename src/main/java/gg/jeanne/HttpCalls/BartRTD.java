package gg.jeanne.HttpCalls;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import static gg.jeanne.StationAbbreviation.getColorAbbrev;

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
        HashMap<String, Prediction> hashedPredictions = new HashMap<>();

        HttpRequest request = HttpRequest.newBuilder(
                URI.create("https://api.bart.gov/api/etd.aspx?cmd=etd&orig=BALB&plat=1&key=MW9S-E7SL-26DU-VV8V&json=y"))
                .GET()
                .build();

        HttpClient connection = HttpClient.newHttpClient();
        try {
            HttpResponse<String> response = connection.send(request, HttpResponse.BodyHandlers.ofString());

            JSONArray json = new JSONObject(response.body())
                    .getJSONObject("root")
                    .getJSONArray("station").getJSONObject(0)
                    .getJSONArray("etd");

            ArrayList<Estimate> estimates = new ArrayList<>();
            for(Object obj : json) {
                JSONObject prediction = (JSONObject) obj;

                for(Object estimate : prediction.getJSONArray("estimate")) {
                    JSONObject parsedEstimate = (JSONObject) estimate;
                    try {
                        estimates.add(new Estimate(
                                parsedEstimate.getInt("delay"),
                                parsedEstimate.getInt("minutes"),
                                parsedEstimate.getInt("length"),
                                parsedEstimate.getInt("platform"),
                                parsedEstimate.getString("color"),
                                parsedEstimate.getString("hexcolor"),
                                parsedEstimate.getString("direction"),
                                prediction.getString("abbreviation"),
                                parsedEstimate.getInt("dynamicflag"),
                                parsedEstimate.getInt("bikeflag"),
                                parsedEstimate.getInt("cancelflag")
                        ));
                    } catch(JSONException ignored) {
                        estimates.add(new Estimate(
                                parsedEstimate.getInt("delay"),
                                0,
                                parsedEstimate.getInt("length"),
                                parsedEstimate.getInt("platform"),
                                parsedEstimate.getString("color"),
                                parsedEstimate.getString("hexcolor"),
                                parsedEstimate.getString("direction"),
                                prediction.getString("abbreviation"),
                                parsedEstimate.getInt("dynamicflag"),
                                parsedEstimate.getInt("bikeflag"),
                                parsedEstimate.getInt("cancelflag")
                        ));
                    }
                }
            }

            for(Estimate estimate : estimates) {
                hashedPredictions.putIfAbsent(estimate.getDestination(), new Prediction(
                        getColorAbbrev(estimate.getColor()),
                        estimate.getDestination(),
                        estimate.getLength()
                ));

                hashedPredictions.get(estimate.getDestination())
                        .addMinutes(estimate.getMinutes());
            }
        } catch (IOException | InterruptedException ignored) {
        }

        return new ArrayList<>(hashedPredictions.values());
    }

}
