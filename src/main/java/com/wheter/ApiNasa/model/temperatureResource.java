package com.wheter.ApiNasa.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class temperatureResource {

	@JsonProperty("Key_SOL")
	private String idSol;
		
	@JsonProperty("Atmosphere_temperature")
	private String name;
		
	private String value;
	
	
	public String getIdSol(){
		return idSol;
	}
			
	public void setIdSol(String idSol){
		this.idSol = idSol;
	}
	
	public String getValue(){
		return value;
	}
			
	public void setValue(String value){
		this.value = value;
	}
	
	public String getName(){
		return name;
	}
			
	public void setName(String name){
		this.name = name;
	}
	
	
}
