package com.wheter.ApiNasa;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.google.gson.Gson;
import com.wheter.ApiNasa.Json.JSONArray;
import com.wheter.ApiNasa.Json.JSONObject;
import com.wheter.ApiNasa.model.temperatureResource;

import antlr.collections.List;
import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;

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
			System.out.println("Response code = "+responseCode);
			
			BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
			String inputLine;
			StringBuffer response = new StringBuffer();
			
			while ((inputLine = in.readLine()) != null)
			{
				response.append(inputLine);
			}
			
			
			JSONObject jsonObjTemp = new JSONObject(response.toString());
			System.out.println(jsonObjTemp);
			
			JSONObject jsonObjNumber = jsonObjTemp.getJSONObject("425");
			JSONObject jsonObjAT = jsonObjNumber.getJSONObject("AT");
			double jsonObjAv = jsonObjAT.getDouble("av");
			
			System.out.println(jsonObjAv); //retorna o valor de av do Json
			
			
			
			/*ArrayList<temperatureResource> listOfTemperatureResource = new ArrayList<temperatureResource>();
			
			double totalTemp = 0;
			
			Iterator<String> iTemperatureResource = jsonObjTemp.keys(); 
			while(iTemperatureResource.hasNext()) {
				
				JSONObject newObjTemp = jsonObjTemp.getJSONObject(iTemperatureResource.next());
				System.out.println(newObjTemp.toString());
				
				JSONArray newArrayTemp = newObjTemp.getJSONArray("av"); 				
				System.out.println(newArrayTemp.toString());
				
				totalTemp += newArrayTemp.getDouble(0);
				
				/*temperatureResource newTemp = new temperatureResource();
				newTemp.setIdSol(newJsonTemp.getString("AT"));
				newTemp.setName(newJsonTemp.getString(""));
				newTemp.setValue(newJsonTemp.getString("av"));					
			};
			
			System.out.println(totalTemp);*/
					
			in.close();
			
			//String data = response.toString();
			//System.out.println(data);

			//temperatureResource msg = new Gson().fromJson(data, temperatureResource.class);			
	
		} catch (MalformedURLException ex) {
			//Log
	    } catch (IOException ex) {
	        //Log
	    } finally {
	       if (con != null) {
	    	   try {
	    		   con.disconnect();
	    	   } catch (Exception ex) {
	    		   //Log
	    	   }
	       }
	    }
	}
}
