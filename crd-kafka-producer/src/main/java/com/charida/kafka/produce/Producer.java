package com.charida.kafka.produce;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.charida.kafka.json.Event;

@Service
public class Producer {
	@Value("${kafka.topic.predict}")
	private String topic;
	
	@Autowired
    private KafkaTemplate<String, String> kafkaTemplate;
	
	public void sendMessage(String message) {
        System.out.println("produce message : "+message);
        this.kafkaTemplate.send(topic, message);
        
    }
}
