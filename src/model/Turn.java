package model;


public class Turn {
	public static final String PENDING = "PENDING";
	public static final String CALLED = "CALLED";
	public static final String ATTENDED = "ATTENDED";
	public static final String LEFT = "LEFT";
	
	private String name;
	private String status;
	private String clientStatus;
	private Client client;
	
	public Turn(String n, String s, Client c) {
		this.name = n;
		this.status = s;
		this.client = c;
		this.clientStatus = "";
		
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

	
	
	

}
