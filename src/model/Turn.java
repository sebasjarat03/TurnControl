package model;

import java.time.LocalDateTime;

public class Turn {
	public static final String PENDING = "PENDING";
	public static final String CALLED = "CALLED";
	public static final String ATTENDED = "ATTENDED";
	public static final String LEFT = "LEFT";
	public static final float TURN_CHANGE_TIME = (float) 0.25;
	
	private String name;
	private String status;
	private String clientStatus;
	private Client client;
	private TurnType type;
	private LocalDateTime timeToStart;
	
	public Turn(String n, String s, Client c, TurnType type, LocalDateTime time) {
		this.name = n;
		this.status = s;
		this.client = c;
		this.clientStatus = "";
		this.type = type;
		this.timeToStart = time;
		
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Client getClient() {
		return client;
	}
	
	public void setClientStatus(String clientStatus) {
		this.clientStatus = clientStatus;
	}
	
	public String getClientStatus() {
		return this.clientStatus;
	}
	
	public TurnType getTurnType() {
		return type;
	}
	
	public LocalDateTime getTimeToStart() {
		return timeToStart;
	}
	
	public LocalDateTime getTimeOfFinish() {
		float duration = type.getDuration() + TURN_CHANGE_TIME;
		long seconds = (long)duration*60;
		
		LocalDateTime temp = timeToStart.plusSeconds(seconds);
		
		
		return temp;
	}

	
	
	

}
