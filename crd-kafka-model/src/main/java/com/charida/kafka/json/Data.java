package com.charida.kafka.json;

import com.fasterxml.jackson.annotation.JsonRootName;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeInfo.As;

@JsonTypeInfo(
    use = JsonTypeInfo.Id.NAME, 
    include = As.PROPERTY, //type 속성 직렬화일 때 안나오도록
    								// 포함시킬려면 PROPERTY
    								// 비포함 EXISTING_PROPERTY
    property = "type")
@JsonSubTypes({
    @JsonSubTypes.Type(value = AppData.class, name = "app"),
    @JsonSubTypes.Type(value = SuggData.class, name = "sugg")
})
@JsonRootName(value = "data")// json 최상위값 설정
public class Data {
	
	private String appId;

	public String getAppId() {
		return appId;
	}
	public void setAppId(String appId) {
		this.appId = appId;
	}
	
	@Override
	public String toString() {
		
		return "{"+appId+"}";
	}
}
