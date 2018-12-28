package com.example.theatre.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.properties.Item;
import com.example.theatre.entity.Actor;
import com.example.theatre.entity.City;
import com.example.theatre.service.ActorService;
import com.example.theatre.service.CityService;

@RestController
@RequestMapping("/theatre/actor")
public class ActorController {

	private final static Logger logger = LoggerFactory.getLogger(ActorController.class);
	@Autowired
	private Item item;	
	@Resource
	private Environment environment;
	@Autowired
	private ActorService actorService;
	@Autowired
	private CityService cityService;

	@RequestMapping(path = "/helloworld", method = RequestMethod.GET)
	public void helloWorld(HttpServletRequest request, HttpServletResponse response) {
		
		logger.info("--------------------------------------");
		logger.info(environment.getProperty("item.name"));
		logger.info(environment.getProperty("server.servlet.context-path"));
		logger.error(environment.getProperty("server.servlet.context-path"));
		logger.info("--------------------------------------");
		
		try {
			Actor actor = actorService.findActor(1);
			City city = cityService.findCity(1);		
			PrintWriter writer = response.getWriter();
			writer.print("hello world\n");
			writer.print("name: " + item.getName() + "\n");
			writer.print("manufacturer: " + item.getManufacturer() + "\n");
			writer.print("count: " + item.getCount() + "\n");
			writer.println("actor: " + actor.getFirstName() + " " + actor.getLastName());
			writer.println("city:" + city.getName());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
