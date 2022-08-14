package com.dtag.bm.udm.service;

import static springfox.documentation.builders.PathSelectors.regex;

import javax.xml.datatype.XMLGregorianCalendar;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.hateoas.HypermediaAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.client.RestTemplate;

import com.dtag.bm.udm.service.service.UdmServiceImpl;
import com.fasterxml.jackson.annotation.JsonIgnore;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
@ComponentScan("com.*")
@EnableAutoConfiguration(exclude = { HypermediaAutoConfiguration.class })
@EnableMongoRepositories(value = { "com.*" })
@EnableJms
@EnableAsync
public class UdmManagementApplication {
	
	@Value("${apiVersion}")
	private String apiVersion;

	public static void main(String[] args) {
		SpringApplication.run(UdmManagementApplication.class, args);
	}
	
	@Bean
	public RestTemplate restTemplate() {
	    return new RestTemplate();
	}
	
	@Bean
	public UdmServiceImpl udmService() {
	    return new UdmServiceImpl();
	}
	
	@Bean
	public Docket newsApi() {
		return new Docket(DocumentationType.SWAGGER_2).groupName("UdmProvisioning").apiInfo(apiInfo()).select()
				.paths(regex("/udmOrdering.*")).build().directModelSubstitute(XMLGregorianCalendar.class, MixIn.class);
	}
	
	public static interface MixIn {
		@JsonIgnore
		public void setYear(int year);
	}

	
	
	private ApiInfo apiInfo() {
		return new ApiInfoBuilder().title("UdmProvisioning Service APIs").description("UdmProvisioning Service APIs").
				termsOfServiceUrl("http://techmahindra.com").contact("TechMahindra").license("Techamhindra Licensed").licenseUrl("http://techmahindra.com").
				version(apiVersion).build();
	}
	

}
