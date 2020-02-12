package com.wheter.ApiNasa;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import com.wheter.ApiNasa.Json.JSONObject;
import com.wheter.ApiNasa.model.MarsWeatherService;

@SpringBootApplication
public class ApiNasaApplication {

	private static final String USER_AGENT = null;

	public static void main(String[] args) throws Exception {
		//SpringApplication.run(ApiNasaApplication.class, args);
		ApiNasaApplication apiNasaTemperature = new ApiNasaApplication();
		apiNasaTemperature.sendGet("https://api.nasa.gov/insight_weather/?api_key=yZEBh8wGDueGtPni5N3Bc8XLIZb80QoCYEuyjuRY&feedtype=json&ver=1.0");
	}

	private void sendGet(String url) throws Exception
	{
		HttpURLConnection con = null;
		try 
		{
			URL obj = new URL(url);
			con = (HttpURLConnection)obj.openConnection();
			con.setRequestMethod("GET");
			con.setRequestProperty("User-Agent", USER_AGENT);
			
			int responseCode = con.getResponseCode();
			System.out.println("Get from URL = "+url);
			System.out.println("Response code = "+responseCode+"\n");
			
			BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
			String inputLine;
			StringBuffer response = new StringBuffer();
			
			while ((inputLine = in.readLine()) != null)
			{
				response.append(inputLine);
			}
			
			JSONObject jsonObjTemp = new JSONObject(response.toString().trim());
			Iterator<String> keys = jsonObjTemp.keys();					
//			System.out.println(jsonObjTemp);
			
			ArrayList<MarsWeatherService> newMWS = new ArrayList<MarsWeatherService>();
			double avgTemp = 0;
			double count = 0;
			while (keys.hasNext()) {
				String key = keys.next();
				if (jsonObjTemp.get(key) instanceof JSONObject) {			          
					//System.out.println(jsonObjTemp.get(key));					
					if (tryParseStringInt(key)) {
						double valTemp = getAtValue(jsonObjTemp.getJSONObject(key));
						MarsWeatherService marsWeatherService = 
								new MarsWeatherService(
										Integer.parseInt(key),
										valTemp,
										"",
										""
										);
						newMWS.add(marsWeatherService);
						avgTemp += valTemp;
						count++;
					}
			    }
			}
			double avg = (avgTemp/count);
			System.out.println("AVG Mars Weather Service API: " + avg);	
							
			in.close();
			
		} catch (MalformedURLException ex) {
			// TODO Log
	    } catch (IOException ex) {
	        // TODO Log
	    } finally {
	       if (con != null) {
	    	   try {
	    		   con.disconnect();
	    	   } catch (Exception ex) {
	    		   // TODO Log
	    	   }
	       }
	    }
	}

	private double getAtValue(JSONObject obj) {
		JSONObject jsonObjAT = obj.getJSONObject("AT");
		return jsonObjAT.getDouble("av");
	}

	private boolean tryParseStringInt(String value) {
	    try {
	    	Integer.parseInt(value);
	        return true;
	    } catch (NumberFormatException e) {
	        return false;
	    }
	}
	
}
