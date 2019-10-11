package com.charida.kafka.json;

import com.fasterxml.jackson.annotation.JsonTypeName;

@JsonTypeName("app")
public class AppData extends Data {
	public String count;
}
