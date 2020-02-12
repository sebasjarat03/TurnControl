package model;
import java.util.ArrayList;

public class Client {
	private String typeId;
	private String id;
	private String name;
	private String lastName;
	private String phone;
	private String address;
	private ArrayList<Turn> turns;
	
	public Client(String typeId, String id, String name, String lastName, String phone, String address) {
		this.typeId = typeId;
		this.id = id;
		this.name = name;
		this.lastName = lastName;
		this.phone = phone;
		this.address = address;
		turns = new ArrayList<Turn>();
		
	}

	public String getTypeId() {
		return typeId;
	}

	public void setTypeId(String typeId) {
		this.typeId = typeId;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
	public ArrayList<Turn> getTurns() {
		return turns;
	}
	
	public void setTurn(Turn turn) {
		turns.add(turn);
	}
	
	

}
