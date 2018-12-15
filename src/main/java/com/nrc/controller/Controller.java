package com.nrc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller {

	@Autowired
	private Environment env;

	// retrieve all tickets
	@GetMapping("/")
	public String getInfo() {
		return new java.util.Date() + " env-param: " + env.getProperty("param");
	}

}
