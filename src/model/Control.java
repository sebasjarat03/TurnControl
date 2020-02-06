package model;
import java.util.ArrayList;
import CustomExceptions.*;

public class Control {
	private String turnToAssign;
	private String turnToAttend;
	private ArrayList<Client> clients;
	
	public Control() {
		this.turnToAssign = "A00";
		this.turnToAttend = "A00";
		clients = new ArrayList<Client>();
	}
	
	public void addClient(String typeId, String id, String name, String lastName, String phone, String address) throws ExistingClientException, EmptyInfoException{
		
		if(search(id) != null) {
			throw new ExistingClientException(id);
		}
		else if(typeId.isEmpty()) {
			throw new EmptyInfoException("*ID TYPE*");
		}
		else if(id.isEmpty()) {
			throw new EmptyInfoException("*ID*");
		}
		else if(name.isEmpty()) {
			throw new EmptyInfoException("*NAME*");
			
		}
		else if(lastName.isEmpty()) {
			throw new EmptyInfoException("*LASTNAME*");
		}
		else {
			if(phone.isEmpty()) {
				phone = "N/A";
			}
			else if (address.isEmpty()) {
				address = "N/A";
			}
			Client temp = new Client( typeId,  id,  name, lastName, phone, address);
			clients.add(temp);
			
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
	
	public void registerTurn(String id) throws NoExistingClientException, ClientHasTurnException{
		if(search(id) == null) {
			throw new NoExistingClientException(id);
		}
		else if(!search(id).getTurn().isEmpty()) {
			throw new ClientHasTurnException(id);
		}
		else {
			search(id).setTurn(turnToAssign);
			this.turnToAssign = nextTurn(turnToAssign);
		}
	}
	
	public String nextTurn(String turn) {
		char letter = turn.charAt(0);
		String nums = Character.toString(turn.charAt(1)) + Character.toString(turn.charAt(2));
		int num = Integer.parseInt(nums);
		String msg;
		
		if(num == 99) {
			letter++;
			num = 00;
		}
		else {
			num++;
		}
		
		msg = (num < 10) ? (String) Character.toString(letter) + "0" + num : (String) Character.toString(letter) +  Integer.toString(num);
		
		return msg;
		
		
	}
	
	public ArrayList<Client> getClients() {
		return clients;
	}
	
	
	
	
	
	

}
