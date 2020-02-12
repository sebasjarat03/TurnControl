package model;
import java.util.ArrayList;

public class Turn {
	public static final String PENDING = "PENDING";
	public static final String ATTENDED = "ATTENDED";
	
	private String name;
	private String status;
	private Client client;
	
	public Turn(String n, String s, Client c) {
		this.name = n;
		this.status = s;
		this.client = c;
		
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

	
	
	

}
