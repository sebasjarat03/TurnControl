package model;
import java.util.ArrayList;
import CustomExceptions.*;

public class Control {
	private String turnToAssign;
	private String nextTurn;
	private ArrayList<Client> clients;
	
	public Control() {
		this.turnToAssign = "A00";
		this.nextTurn = "A00";
		clients = new ArrayList<Client>();
	}
	
	public void addClient(String typeId, String id, String name, String lastName, String phone, String address) throws ExistingClientException{
		
		if(search(id) != null) {
			throw new ExistingClientException(id);
		}
		
		
	}
	
	public Client search(String id) {
		Client temp = null;
		boolean found = false;
		for(int i = 0; i<clients.size() && !found; i++) {
			if(clients.get(i).getId().equalsIgnoreCase(id)) {
				temp = clients.get(i);
				found = true;
			}
		}
		return temp;
	}
	
	

}
