package com.charida.kafka.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.charida.kafka.json.Event;
import com.charida.kafka.produce.Producer;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@RequestMapping(value = "/kafka")
public class ProduceController {
	@Autowired
	private Producer producer;

	@PostMapping(value = "/predict")
	public void sendMessageToKafkaTopic(@RequestParam("message") String message) {
		System.out.println("값 : " + message);
		Event bean;
		try {
			bean = new ObjectMapper()
					.readerFor(Event.class)
					.readValue(message);
			this.producer.sendMessage(message);
		} catch (IOException e) {
			System.out.println("Serializer err");
		}

	}
}
