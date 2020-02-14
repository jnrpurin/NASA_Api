package com.weather.ApiNasa.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.weather.ApiNasa.Json.JSONObject;
import com.weather.ApiNasa.model.MarsWeatherService;

@RestController
public class temperatureController {

	@RequestMapping("/getTemperature")
	public String temperatureRequest(@RequestParam(value = "value",
	defaultValue = "") String name) {
		
		String temperature = "";
		JSONObject jsonObj = new JSONObject(getJsonNASAAPI());
		
		if (name.isEmpty())
		{
			temperature = "Average temperature "+ getGeneralAvgValue(jsonObj);
		}
		else
		{
			temperature = getTemperatureByKey(jsonObj, name.toString());
		}
		
		return temperature;
	}
	
	
	private String getJsonNASAAPI()
	{
		URL nasaURL;
		HttpURLConnection con = null;
		try 
		{
			nasaURL = new URL("https://api.nasa.gov/insight_weather/?api_key=yZEBh8wGDueGtPni5N3Bc8XLIZb80QoCYEuyjuRY&feedtype=json&ver=1.0");			
		
			con = (HttpURLConnection)nasaURL.openConnection();
			con.setRequestMethod("GET");
			con.setRequestProperty("User-Agent", null);
			con.getResponseCode();
			
			BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
			String inputLine;
			StringBuffer response = new StringBuffer();
			
			while ((inputLine = in.readLine()) != null)
			{
				response.append(inputLine); 
			}
			in.close();			
			
			return response.toString().trim();			
		} catch (MalformedURLException e) {
			return e.getMessage();			
	    } catch (IOException e) {
	    	return e.getMessage();
	    }		
	}

	
	private String getTemperatureByKey(JSONObject parJsonObj, String key)
	{
		String retValue;
		try
		{
			if (tryParseStringInt(key)) {
				double tempCel = getAtValue(parJsonObj.getJSONObject(key));
				double tempFah = celsiusToFahrenheit(tempCel);
				retValue = "Day avg temperature "+ key +": "+ Double.toString(tempFah);
			}	    
			else
			{
				retValue = "Temperature key '" + key + "' not found.";
			}
					
			return retValue;
		}
		catch (Exception e)
		{
			return e.getMessage();
		}
	}
	
	
	private String getGeneralAvgValue(JSONObject jsonObjTemp)
	{
		ArrayList<MarsWeatherService> newMWS = new ArrayList<MarsWeatherService>();
		double avgTemp = 0;
		double count = 0;
		
		try 
		{
			Iterator<String> keys = jsonObjTemp.keys();
			while (keys.hasNext()) {
				String key = keys.next();
				if (jsonObjTemp.get(key) instanceof JSONObject) {			          
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
			double avgValue = celsiusToFahrenheit(avgTemp/count);
			return Double.toString(avgValue);				
		}
		catch (Exception e)
		{
			return e.getMessage();
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
	
	
	private double celsiusToFahrenheit(double celsius)
	{		
		double temp = ((celsius*9/5) + 32); 
		return Math.round(temp * 100.0) / 100.0;
	}
	//(-59 °C × 9/5) + 32
	
}
