package com.charida.kafka.json;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

/**
 * JSON 데이터 모델
 * {"createdAt":"2019-09-12 11:22:25","userId":"aaaa"
 * ,"data":{"type":"app","count":"959"}}
 *
 */
public class Event {
	@JsonFormat(
		      shape = JsonFormat.Shape.STRING,
		      pattern = "yyyy-MM-dd hh:mm:ss")//serialize 포맷
	@JsonDeserialize(using = DateDeserializer.class)//deserialize 포맷터
	private Date createdAt;
	private String userId;
	private Data data;
	
	public Date getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public Data getData() {
		return data;
	}
	public void setData(Data data) {
		this.data = data;
	}
	
	
}
