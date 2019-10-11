package com.charida.kafka.json;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

public class DateDeserializer extends StdDeserializer<Date> {
	
	private static SimpleDateFormat df
    = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
	
	public DateDeserializer() { 
        this(null); 
    } 
	public DateDeserializer(Class<?> vc) {
		super(vc);
	}

	@Override
	public Date deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
		String date = p.getText();
		
		try {
			return df.parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
	}
	

}
