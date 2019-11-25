package com.kaps.learning.rest.webservices.restfulwebservices.filtering;

import java.util.Arrays;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FilteringController {
	
	@GetMapping(path = "/filtering")
	public AwesomeBean retrieveSomeBean() {
		return new AwesomeBean("value-1","value-2","value-3");
	}
	
	@GetMapping(path = "/filtering-list")
	public List<AwesomeBean> retrieveListOfSomeBeans() {
		return Arrays.asList(new AwesomeBean("value-1","value-2","value-3"), new AwesomeBean("sum-1","sum-2","sum-3"));
	}

}
