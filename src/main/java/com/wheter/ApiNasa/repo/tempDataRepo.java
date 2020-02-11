package com.wheter.ApiNasa.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import com.wheter.ApiNasa.model.temperatureResource;

public interface tempDataRepo extends JpaRepository<temperatureResource, String>{

}
