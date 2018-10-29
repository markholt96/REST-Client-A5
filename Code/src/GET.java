import java.io.*;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

/**
 * A class that sends an HTTP GET and reads the response from the server
 * @author Markus Holt based on example proven by Girts Strazdins
 */
public class GET {

    public static void main(String[] args) {
        GET get = new GET("104.248.47.74", 80);
        get.doExampleGet();
    }

    private String BASE_URL; // Base URL (address) of the server

    /**
     * Create an HTTP GET example
     *
     * @param host Will send request to this host: IP address or domain
     * @param port Will use this port
     */
    public GET(String host, int port) {
        BASE_URL = "http://" + host + ":" + port + "/";
    }

    /**
     * Send an HTTP GET to a specific path on the web server
     */
    public void doExampleGet() {
        sendGet("dkrest/test/get2");
    }

    /**
     * Send HTTP GET
     *
     * @param path     Relative path in the API.
     */
    private void sendGet(String path) {
        try {
            String url = BASE_URL + path;
            URL urlObj = new URL(url);
            System.out.println("Sending HTTP GET to " + url);
            HttpURLConnection con = (HttpURLConnection) urlObj.openConnection();

            con.setRequestMethod("GET");

            int responseCode = con.getResponseCode();
            if (responseCode == 200) {
                System.out.println("Server reached");
                // Response was OK, read the body (data)
                InputStream stream = con.getInputStream();
                String responseBody = convertStreamToString(stream);
                //String response = JSONParse(stream);
                stream.close();
                System.out.println("Response text from the server:");
                System.out.println(responseBody);

            } else {
                String responseDescription = con.getResponseMessage();
                System.out.println("Request failed, response code: " + responseCode + " (" + responseDescription + ")");
            }
        } catch (ProtocolException e) {
            System.out.println("Protocol not supported by the server");
        } catch (IOException e) {
            System.out.println("Something went wrong: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Read the whole content from an InputStream, return it as a string
     * @param is Inputstream to read the body from
     * @return The whole body as a string
     */
    private String convertStreamToString(InputStream is) {
        BufferedReader in = new BufferedReader(new InputStreamReader(is));
        StringBuilder response = new StringBuilder();
        try {
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
                response.append('\n');
            }
        } catch (IOException ex) {
            System.out.println("Could not read the data from HTTP response: " + ex.getMessage());
        }
        return response.toString();
    }

    /**
     *

    private String JSONParse(InputStream stream){
        String aResponse = null;
        String bResponse = null;
        String cResponse = null;
        String returnString = null;

        try {
            JSONObject jsonObject = new JSONObject();
            if (jsonObject.has("a")) {
                aResponse = jsonObject.getString("a");
            }

            if (jsonObject.has("b")) {
                bResponse = jsonObject.getString("b");
            }
            if (jsonObject.has("c")) {
                cResponse = jsonObject.getString("c");
            }
        }
        catch(JSONException e){
                System.out.println("Exception in JSON parsing: " + e.getMessage());
        }
        returnString = aResponse + bResponse + cResponse;
        return returnString;
    }
     */
}