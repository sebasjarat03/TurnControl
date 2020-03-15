package model;
import java.io.*;
import java.time.*;
import java.util.ArrayList;
import java.util.Arrays;


import CustomExceptions.*;

public class Control implements Serializable{
	
	public final static String CLIENT_REPORT_PATH = "data/client_reports/client";
	public final static String TURN_REPORT_PATH = "data/turn_reports/turn";
	public final static String RANDOM_NAMES_PATH = "data/randoms/names.txt";
	public final static String RANDOM_LASTNAMES_PATH = "data/randoms/lastnames.txt";
	
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
	
	public void registerTurn(String id, int turnType) throws NoExistingClientException, ClientHasTurnException, EmptyTypeListException, InvalidTypeException{
		if(search(id) == null) {
			throw new NoExistingClientException( "id", id);
		}
		else if(clientHasPendingTurn(id)) {
			throw new ClientHasTurnException(id);
		}
		else if(turnTypes.isEmpty()) {
			throw new EmptyTypeListException();
		}
		else if(turnType<0 | turnType>=turnTypes.size()) {
			throw new InvalidTypeException();
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
			
			TurnType aux = turnTypes.get(turnType);
			Turn turnTemp = new Turn(turnToAssign, Turn.PENDING, search(id), aux, startingTime);
			turns.add(turnTemp);
			search(id).setTurn(turnTemp);
			this.turnToAssign = nextTurn(turnToAssign);
			
		}
		
		
	}
	
	public void registerTurn(String id, int turnType, LocalDateTime time) throws NoExistingClientException, ClientHasTurnException, EmptyTypeListException, InvalidTypeException{
		if(search(id) == null) {
			throw new NoExistingClientException( "id", id);
		}
		else if(clientHasPendingTurn(id)) {
			throw new ClientHasTurnException(id);
		}
		else if(turnTypes.isEmpty()) {
			throw new EmptyTypeListException();
		}
		else if(turnType<0 || turnType>0) {
			throw new InvalidTypeException();
		}
		else {
			LocalDateTime startingTime;
			
			if(turns.size()<1) {
				startingTime = time;
			}
			else {
				LocalDateTime auxTime = turns.get(turns.size()-1).getTimeOfFinish();
				startingTime = (time.isAfter(auxTime)) ? time : auxTime;
			}
			
			TurnType aux = turnTypes.get(turnType);
			Turn turnTemp = new Turn(turnToAssign, Turn.PENDING, search(id), aux, startingTime);
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
	
	public void sortTurnTypesByName() {
		for(int i = 1; i < turnTypes.size(); i++) {
			TurnType temp = turnTypes.get(i);
			int j = i - 1;
			while(j >= 0 && turnTypes.get(j).getName().compareTo(temp.getName()) > 0) {
				turnTypes.set(j + 1, turnTypes.get(j));
				j--;
			}
			turnTypes.set(j + 1, temp);
		}
	}
	
	public String printTypeList() {
		sortTurnTypesByName();
		String msg = "";
		for (int i = 0; i < turnTypes.size(); i++) {
			msg += "\n"+ (i+1) + ") " + turnTypes.get(i).toString(); 
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
					msg += "\nTurn " + turns.get(i).getName() + " type(" + turns.get(i).getTurnType().getName() + ") was attended at " + turns.get(i).getTimeToStart().toString();
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
	
	public String generateClientReport(String clientId, int option) throws NoExistingClientException, IOException {
		String msg = "";
		Client temp = search(clientId);
		if(temp == null) {
			throw new NoExistingClientException("ID", clientId);
		}
		else {
			msg += "\nThe client with id " + clientId + " has this turns:";
			ArrayList<Turn> aux = temp.getTurns();
			for (int i = 0; i < aux.size(); i++) {
				msg+= "\n" +  aux.get(i).toString();
			}
		}
		if(msg.isEmpty()) {
			msg += "This client does not has any turn";
		}
		else if(option == 1) {
			BufferedWriter bw = new BufferedWriter(new FileWriter((CLIENT_REPORT_PATH + clientId + ".txt")));
			bw.write("" + msg);
			bw.close();
			msg = "The report was saved at this path: " + CLIENT_REPORT_PATH + clientId + ".txt";
		}
		else if (option==2){
			
		}
		else {
			msg = option + " is not a correct option";
		}
		return msg;
	}
	
	public String generateTurnReport(String turnName, int option) throws IOException {
		String msg = "";
		
		for (int i = 0; i < clients.size(); i++) {
			ArrayList<Turn> tempList = clients.get(i).getTurns();
			for (int j = 0; j < tempList.size(); j++) {
				if(tempList.get(j).getName().equals(turnName)) {
					msg += clients.get(i).toString();
				}
			}
		}
		
		if(msg.isEmpty()) {
			msg = "Any client has this turn";
		}
		else if (option==1) {
			BufferedWriter bw = new BufferedWriter(new FileWriter((TURN_REPORT_PATH + turnName + ".txt")));
			bw.write("" + msg);
			bw.close();
			msg = "The report was saved at this path: " + TURN_REPORT_PATH + turnName + ".txt";
		}
		else if (option==2){
			
		}
		else {
			msg = option + " is not a correct option";
		}
		return msg;
		
	}
	
	public void generateRandomClients(int quantity) throws IOException {
		ArrayList<String> names = new ArrayList<String>();
		ArrayList<String> lastNames = new ArrayList<String>();
		String[] idTypes = new String[] {"TI","CC", "CE", "PP"};
		
		BufferedReader br1 = new BufferedReader(new FileReader(RANDOM_NAMES_PATH));
		String line = br1.readLine();
		
		
		while(line!=null) {
			names.add(line);
			line = br1.readLine();
			
		}
		br1.close();
		
		BufferedReader br2 = new BufferedReader(new FileReader(RANDOM_LASTNAMES_PATH));
		String line2 = br2.readLine();
		
		while(line2!=null) {
			lastNames.add(line2);
			line2 = br2.readLine();
			
		}
		br2.close();
		
		
		String[][] temps = new String[3][quantity];

		for(int i = 0; i < temps[0].length; i++) {
			int index =(int)(Math.random()*100);
			String na = names.get(index);
			temps[0][i] = na;
		}
		for(int i = 0; i < temps[1].length; i++) {
			int index =(int)(Math.random()*100);
			String su = lastNames.get(index);
			temps[1][i] = su;
		}
		for(int i = 0; i < temps[2].length; i++) {
			int index =(int)(Math.random()*4);
			String id = idTypes[index];
			temps[2][i] = id;
		}
		for (int i = 0; i < quantity; i++) {
			String idType = temps[2][i];
			String id = "00" + (i+1);
			String name = temps[0][i];
			String lastName = temps[1][i];
			String phone = "N/A";
			String address = "N/A";
			clients.add(new Client(idType, id, name, lastName, phone, address));
		}
	}
	
	public void generateRandomTurns(int turnsByDay, int days) throws NoExistingClientException, ClientHasTurnException, EmptyTypeListException, InvalidTypeException {
		ArrayList<Client> clientsWithoutTurns = getClientsWithoutTurn();
		int totTurns = turnsByDay*days;
		if(clientsWithoutTurns.isEmpty()) {
			throw new NoExistingClientException("There are no clients without turn");
		}
		else if(clientsWithoutTurns.size()<totTurns) {
			throw new NoExistingClientException("There are no enough clients ("+ clientsWithoutTurns.size() +") for this number of turns (" + totTurns + ")");
		}
		
		int idx = 0;
		LocalDateTime auxTime = systemTime.getTime2();
		for (int i = 0; i < days; i++) {
			for (int j = 0; j < turnsByDay; j++) {
				Client temp = clientsWithoutTurns.get(idx);
				int turnTypeIdx = (int) Math.random()*turnTypes.size();
				registerTurn(temp.getId(), turnTypeIdx, auxTime);
				idx++;
			}
			auxTime = auxTime.plusDays(1);
		}
		
	}
	
	public ArrayList<Client> getClientsWithoutTurn(){
		ArrayList<Client> temp = new ArrayList<Client>();
		for (int i = 0; i < clients.size(); i++) {
			ArrayList<Turn> tempTurns = clients.get(i).getTurns();
			if(clients.get(i).getTurns().isEmpty()) {
				
				temp.add(clients.get(i));
			}
			else if(clients.get(i).getTurns().get(tempTurns.size()-1).getStatus().equals(Turn.CALLED)) {
				temp.add(clients.get(i));
			}
			
			
		}
		return temp;
	}
	
	public ArrayList<TurnType> getTurnTypes(){
		return turnTypes;
	}
	
	public SystemTime getSystemTime() {
		return systemTime;
	}
	
	public String sortClientsById() {
		if(!clients.isEmpty()) {
			for(int i = 0; i < clients.size() - 1; i++) {
				int min = i;
				for(int j = i + 1; j < clients.size(); j++) {
					if(clients.get(j).getId().compareTo(clients.get(min).getId()) < 0) {
						min = j;
					}
				}
				Client temp = clients.get(min);
				clients.set(min, clients.get(i));
				clients.set(i, temp);
			}
			String msg = "";
			for(int i = 0; i < clients.size(); i++) {
				msg += clients.get(i).toString() + "\n";
			}
			return msg;
		}
		else {
			return "There are not users created";
		}
	}
	
	public String sortClientsByNameAndLastName() {
		if(!clients.isEmpty()) {
			for(int i = 0; i < clients.size() - 1; i++) {
				for(int j = 0; j < clients.size() - i - 1; j++) {
					if(clients.get(j).compareTo(clients.get(j + 1)) > 0) {
						Client temp = clients.get(j);
						clients.set(j, clients.get(j + 1));
						clients.set(j + 1, temp);
					}
				}
			}
			String msg = "";
			for(int i = 0; i < clients.size(); i++) {
				msg += clients.get(i).toString() + "\n";
			}
			return msg;
		}
		else {
			return "There are not users created";
		}
	}
	
	
	
	
	

}
