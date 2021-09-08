package edu.escuelaing.arep.weatherService;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpServiceUserSearch {
		private WeatherService weatherAPI = new WeatherService();
		
	    public String getDataService() throws IOException {
	    	String responseData = "";
	        URL obj = new URL(weatherAPI.getUrlWS());
	        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
	        con.setRequestMethod("GET");
	        int responseCode = con.getResponseCode();
	        System.out.println("GET Response Code :: " + responseCode);
	        if (responseCode == HttpURLConnection.HTTP_OK) {
	            BufferedReader in = new BufferedReader(new InputStreamReader(
	                    con.getInputStream()));
	            String inputLine;
	            StringBuffer response = new StringBuffer();

	            while ((inputLine = in.readLine()) != null) {
	                response.append(inputLine);
	            }
	            in.close();
	            responseData = response.toString();
	        } 
	        return responseData;
	    }
	    
	    public void setWS(String cityDWS) {
	    	weatherAPI.setcityDWS(cityDWS);
	    }
	    
}
