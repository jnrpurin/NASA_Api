package com.wheter.ApiNasa.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.wheter.ApiNasa.model.temperatureResource;
import com.wheter.ApiNasa.repo.tempDataRepo;

@RestController		
public class temperatureController {

	@Autowired
	private tempDataRepo temperatureRepository;
	
	@GetMapping(path = "api/temperature-id/{}")
	public ResponseEntity<Optional<temperatureResource>> buscarTemperatureById(
			@PathVariable(name = "id", required = true)String idSol) {
		return ResponseEntity.ok(temperatureRepository.findById(idSol));
	}
	
}
