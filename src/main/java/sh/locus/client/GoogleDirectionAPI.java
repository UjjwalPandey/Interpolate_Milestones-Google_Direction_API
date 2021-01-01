package sh.locus.client;

import org.json.JSONException;
import org.json.JSONObject;
import sh.locus.pojo.Point;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public class GoogleDirectionAPI {

    /**
     *
     * @param p1 Source
     * @param p2 Destination
     * @return  Polyline String (Decoded)
     * @throws IOException if some issue in fetching API
     */
    public String getResponse(Point p1, Point p2) throws IOException {
        String url = "https://maps.googleapis.com/maps/api/directions/json";
        String API_KEY = "AIzaSyAb8ohmBXqtK4y2_a5CFnFnfLGiOsuwjIo";
        String readLine = null;

        String query = String.format("origin=%s&destination=%s&key=%s",
                URLEncoder.encode(p1.toString(), StandardCharsets.UTF_8),
                URLEncoder.encode(p2.toString(), StandardCharsets.UTF_8),
                URLEncoder.encode(API_KEY, StandardCharsets.UTF_8));

        HttpURLConnection connection = (HttpURLConnection) new URL(url+"?"+query).openConnection();
        connection.setRequestMethod("GET");

        int responseCode = connection.getResponseCode();

        if(responseCode == HttpURLConnection.HTTP_OK){
            // Get API Response
            BufferedReader in = new BufferedReader(new InputStreamReader((connection.getInputStream())));
            StringBuilder response = new StringBuilder();
            while ((readLine = in.readLine()) != null){
                response.append(readLine);
            }
            in.close();

            // Reading JSON Response
            try {
                JSONObject obj = new JSONObject(response.toString());
                JSONObject routes = (JSONObject) (obj.getJSONArray("routes").get(0));
                return routes.getJSONObject("overview_polyline").getString("points");
            }catch(JSONException e){
                System.out.println("JSONException: "+e.toString());
                return "";
            }
        }else{
            System.out.println("Error in Response. Response Code = "+responseCode);
        }
        return "";
    }
}


