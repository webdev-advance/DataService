package com.Deepak.dataservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Deepak.dataservice.services.DataService;

@RestController
public class DataServiceController {
	
	@Autowired
	private DataService dataService;
	
	@PostMapping(value="/dataservice/load")
	public void runJob() {
		dataService.loadFiles();
	}

}
