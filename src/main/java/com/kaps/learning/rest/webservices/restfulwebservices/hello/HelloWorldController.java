package com.kaps.learning.rest.webservices.restfulwebservices.hello;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloWorldController {
	
	// GET
	// URI - /hello-world
	//@RequestMapping(method = RequestMethod.GET, path = "/hello-world")
	@GetMapping(path = "hello-world")
	public String helloWorld() {
		return "Hello World!";
	}
	
	@GetMapping(path = "hello-world-bean")
	public HelloWorldBean helloWorldBean() {
		return new HelloWorldBean("Hello World!");
	}
	
	@GetMapping(path = "hello-world-bean/{paramName}")
	public HelloWorldBean helloWorldBeanParam(@PathVariable String paramName) {
		return new HelloWorldBean(String.format("Hello World - %s", paramName));
	}

}