package model;
import java.io.Serializable;
import java.time.*;
import java.util.ArrayList;
import java.util.Arrays;

import CustomExceptions.*;

public class Control implements Serializable{
	
	
	private String turnToAssign;
	private String turnToAttend;
	private ArrayList<Client> clients;
	private ArrayList<Turn> turns;
	private ArrayList<TurnType> turnTypes;
	private SystemTime systemTime;
	
	public Control() {
		this.turnToAssign = "A00";
		this.turnToAttend = "A00";
		clients = new ArrayList<Client>();
		turns = new ArrayList<Turn>();
		turnTypes = new ArrayList<TurnType>();
		systemTime = new SystemTime();
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
	
	public void registerTurn(String id, String turnType) throws NoExistingClientException, ClientHasTurnException, EmptyTypeListException, NoExistingTypeException{
		if(search(id) == null) {
			throw new NoExistingClientException( "id", id);
		}
		else if(clientHasPendingTurn(id)) {
			throw new ClientHasTurnException(id);
		}
		else if(turnTypes.isEmpty()) {
			throw new EmptyTypeListException();
		}
		else if(searchTurnType(turnType)==null) {
			throw new NoExistingTypeException(turnType);
		}
		else {
			LocalDateTime startingTime;
			
			if(turns.size()<1) {
				startingTime = systemTime.getTime2();
			}
			else {
				LocalDateTime auxTime = turns.get(turns.size()-1).getTimeOfFinish();
				startingTime = (systemTime.getTime2().isAfter(auxTime)) ? systemTime.getTime2() : auxTime;
			}
			
			TurnType aux = searchTurnType(turnType);
			Turn turnTemp = new Turn(turnToAssign, Turn.PENDING, search(id), aux, startingTime);
			System.out.println(startingTime);
			turns.add(turnTemp);
			search(id).setTurn(turnTemp);
			this.turnToAssign = nextTurn(turnToAssign);
			
		}
		
		
	}
	
	public String nextTurn(String turn) {
		char letter = turn.charAt(0);
		String nums = Character.toString(turn.charAt(1)) + Character.toString(turn.charAt(2));
		int num = Integer.parseInt(nums);
		String msg;
		
		if(num == 99) {
			
			if(letter == 'Z') {
				letter = 'A';
				num = 00;
			}
			else {
				letter++;
				num = 00;
			}
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
	
	
	
	public String getTurnToAttend() {
		return this.turnToAttend;
	}
	
	public boolean clientHasPendingTurn(String id) {
		boolean has = false;
		ArrayList <Turn> temp = search(id).getTurns();
		if(temp.isEmpty()) {
			has = false;
		}
		else {
		for(int i = 0; i< temp.size() && !has; i++) {
			if (temp.get(i).getStatus().equals(Turn.PENDING)) {
				has = true;
			}
		}
		}
		return has;
	}
	
	public String getTurnToAssign() {
		return this.turnToAssign;
	}
	
	public void setTurnToAttend(String tta) {
		this.turnToAttend = tta;
	}
	
	public void setTurnToAssign(String ttass) {
		this.turnToAssign = ttass;
	}
	
	public void addTurnType(String name, float duration) throws  ExistingTurnTypeException, EmptyInfoException  {
		
		if(searchTurnType(name)!=null) {
			throw new ExistingTurnTypeException(name);
		}
		else if(name.isEmpty()) {
			throw new EmptyInfoException("*NAME*");
		}
		else if(duration == 0) {
			throw new EmptyInfoException("*DURATION*");
		}
		else {
			TurnType temp = new TurnType(name, duration);
			turnTypes.add(temp);
		}
		
		
	}
	
	public TurnType searchTurnType(String name) {
		TurnType temp = null;
		boolean found = false;
		for(int i = 0; i<turnTypes.size() && !found; i++) {
			if(turnTypes.get(i).getName().equalsIgnoreCase(name)) {
				temp = turnTypes.get(i);
				found = true;
			}
		}
		return temp;
	}
	
	public String printTypeList() {
		String msg = "";
		for (int i = 0; i < turnTypes.size(); i++) {
			msg += "\n" + turnTypes.get(i).toString(); 
		}
		return msg;
	}
	
	public String showTime() {
		String msg = "" + systemTime.getTime();
		
		return msg;
	}
	
	public void upgradeTime(long time) {
		LocalDateTime actual = systemTime.getTime2();
		long nanoSeconds = time*1000000;
		systemTime.setTime(actual.plusNanos(nanoSeconds)); 
	}
	
	public void updateTimeManually(int y, int mo, int d, int h, int mi, int s) throws DateIsLessException {
		LocalDateTime newTime = LocalDateTime.of(y, mo, d, h, mi, s);
		if(systemTime.getTime2().isAfter(newTime)) {
			throw new DateIsLessException();
		}
		else {
			this.systemTime.setTime(newTime);
		}
	}
	
	public void updateTimeWithPc(LocalDateTime newTime) throws DateIsLessException {
		if(systemTime.getTime2().isAfter(newTime)) {
			throw new DateIsLessException();
		}
		else {
			this.systemTime.setTime(newTime);
		}
	}
	
	
	
	public Turn binarySearchTurn(String n) {
		int low = 0;
		int high = turns.size()-1;
		boolean found = false;
		Turn temp = null;
		while(low<=high && !found) {
			int mid = (low+high) / 2;
			
			if(n.compareTo(turns.get(mid).getName())>0) {
				low = mid +1;
			}
			else if(n.compareTo(turns.get(mid).getName())<0) {
				high = mid - 1;
			}
			else {
				temp = turns.get(mid);
				found=true;
			}
		}
		return temp;
	}
	
	public String attendTurn() throws NoExistingTurnException {
		String msg  = "";
		boolean stop = false;
		for (int i = 0; i < turns.size() && !stop ; i++) {
			if(turns.get(i)==null) {
				throw new NoExistingTurnException(turnToAttend);
			}
			else {
				if(turns.get(i).getTimeToStart().isBefore(systemTime.getTime2()) ) {
					if(turns.get(i).getStatus().equals(Turn.PENDING)) {
					int clientStatus = (int)Math.random()*(1-2)+2;
					turns.get(i).setStatus(Turn.CALLED);
					if(clientStatus == 1) {
						turns.get(i).setClientStatus(Turn.ATTENDED);
					}
					else if(clientStatus == 2) {
						turns.get(i).setClientStatus(Turn.LEFT);
					}
					msg += "\nTurn " + turns.get(i).getName() + " type(" + turns.get(i).getTurnType().getName() + ") was attended";
					turnToAttend = nextTurn(turnToAttend);
					}
					
				}
				else {
					stop = true;
				}
				
				
				
			}
		}
		if(msg.isEmpty()) {
			msg += "No turns attended";
		}
		return msg;
		
	}
	
	
	
	
	
	
	
	
	

}
