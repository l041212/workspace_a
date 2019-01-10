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
	@Autowired
	private ActorService actorService;
	@Autowired
	private CityService cityService;
	@Resource
	private Environment environment;

	@RequestMapping(path = "/helloworld", method = RequestMethod.GET)
	public void helloWorld(HttpServletRequest request, HttpServletResponse response) throws InstantiationException, IllegalAccessException {

		logger.info("--------------------------------------");
		logger.info(environment.getProperty("item.name"));
		logger.info(environment.getProperty("server.servlet.context-path"));
		logger.error(environment.getProperty("server.servlet.context-path"));
		logger.info("--------------------------------------");

		try {
			Actor actor = actorService.getItem(1);
			City city = cityService.getItem(1);
			PrintWriter writer = response.getWriter();
			writer.print("hello world\n");
			writer.print("name: " + item.getName() + "\n");
			writer.print("manufacturer: " + item.getManufacturer() + "\n");
			writer.print("count: " + item.getCount() + "\n");
			writer.println("actor: " + actor.getFirstName() + " " + actor.getLastName());
			writer.println("city:" + city.getName());
			/*
			Actor item = new Actor();
			item.setFirstName("Ronie");
			item.setLastName("Wood");
			item.setLastUpdate(new Timestamp(Date.class.newInstance().getTime()));
			item = actorService.insertItem(item);
			writer.println("actor2: " + item.getFirstName() + " " + item.getLastName());
			item.setLastName("West");
			item = actorService.updateItem(item);
			writer.println("actor3: " + item.getFirstName() + " " + item.getLastName());
			actorService.deleteItem(item.getActorId());
			*/
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
