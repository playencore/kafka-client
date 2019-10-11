package com.charida.kafka.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.charida.kafka.hadoop.HdfsCommander;
import com.charida.kafka.json.Event;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class Consumer {
	private static final Logger logger = 
			LoggerFactory.getLogger(Consumer.class);
	@Autowired
	private HdfsCommander hdfsCommander;
	@Value("${custom.upload.dir}")
	private String dirPath;
	
	/**
	 * 지정한 토픽 Listener
	 * @param event
	 */
	@KafkaListener(topics = "pred-topic", groupId = "group_a")
	public void consumeJson(Event event) {
		ObjectMapper mapper = new ObjectMapper();
		
		String result=null;
		try {
			result = mapper.writeValueAsString(event);
			logger.info(result);			
			hdfsCommander.uploadFileToHdfs(true, dirPath, result);
		} catch (JsonProcessingException e) {
			logger.info("Deserializer err");
		}
	}
}
