package com.wheter.ApiNasa.model;

public class MarsWeatherService {
	private int id;
	private double aTav;
	private String lastUTC; 
	private String season;  
	
	public MarsWeatherService(int id, double aTav, String lastUTC, String season)
	{
	 	setId(id); 
	 	setATav(aTav);
	 	setLastUTC(lastUTC);
	 	setSeason(season);
	}
	
	public int getId(){
		return id; 
	}
	public void setId(int id){
		this.id = id;
	}
	
	public double getATav(){
		return aTav; 
	}
	public void setATav(double input){
		this.aTav = input;
	}
	
	public String getLastUTC(){
		return lastUTC; 
	}
	public void setLastUTC(String input){
		this.lastUTC = input;
	}
	
	public String getSeason(){
		return season; 
	}
	public void setSeason(String input){
		this.season = input;
	}	
}

