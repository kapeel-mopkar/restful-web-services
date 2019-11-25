package com.kaps.learning.rest.webservices.restfulwebservices;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.springframework.context.annotation.Configuration;

//Configuration
//Enable swagger
@Configuration
//@EnableSwagger2
public class SwaggerConfig {
	
	/*
	 * public static final Contact DEFAULT_CONTACT = new Contact("Kapeel Mopkar",
	 * "http://www.kapeelmopkar.com", "kapeelmopkar@gmail.com"); public static final
	 * ApiInfo DEFAULT = new ApiInfo( "Awesome API Title",
	 * "Awesome API Description", "1.0", "urn:tos", DEFAULT_CONTACT, "Apache 2.0",
	 * "http://www.apache.org/licenses/LICENSE-2.0", new
	 * ArrayList<VendorExtension>());
	 */
	private static final Set<String> DEFAULT_PRODUCES_CONSUMES = new HashSet<String>(Arrays.asList("application/json", "application/xml"));
	
	// Bean - Docket
	/*
	 * @Bean public Docket api() { return new Docket(DocumentationType.SWAGGER_2)
	 * .apiInfo(DEFAULT) .produces(DEFAULT_PRODUCES_CONSUMES)
	 * .consumes(DEFAULT_PRODUCES_CONSUMES); }
	 */
		//Swagger 2
		//All the paths
		// All the Apis
	
}
