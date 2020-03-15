package ui;
import model.*;

import java.time.LocalDateTime;
import java.util.*;
import CustomExceptions.*;
import java.io.*;

public class Main {
	public static final String SAVE_MODEL_PATH = "data/saves/model_saved.sbjt";
	public Scanner sc;
	public Control control;
	

	public static void main(String args[]) {
		Main main = new Main();
		main.menu();
	}

	public Main() {
		sc = new Scanner(System.in);
		control = new Control();
	}

	public void menu() {
		int opc = 10;
		long timeA, timeB, totalDur;
		
		while (opc != 0) {
			long t1 = System.currentTimeMillis();
			try {
				System.out.println("\n***********************************************************************************");
				System.out.println(control.showTime());
				System.out.println("***********************************************************************************");
				System.out.println("\nSelect the option:");
				System.out.println("1) Add user 			2) Assign turn 			3) Add type of turn");
				System.out.println("4) Update time          	5) Attend turns			6) Save all");
				System.out.println("7) Load all			8) Generate client reports 	9) Generate turn reports");
				System.out.println("10) Generate random clients	11)Generate Random turns	12) Print a sorted list of existing clients");
				System.out.println("13) Search a turn to know if exists");
				System.out.println(" 0) Exit");
				System.out.println("***********************************************************************************");
				opc = Integer.parseInt(sc.nextLine());
				switch(opc) {
				case 1:

					System.out.print("\nEnter the type of id: "); String typeId = sc.nextLine();
					System.out.print("Enter the id: "); String id = sc.nextLine();
					System.out.print("Enter the name: "); String name = sc.nextLine();
					System.out.print("Enter the lastname: "); String lastName = sc.nextLine();
					System.out.print("Enter the phone: "); String phone = sc.nextLine();
					System.out.print("Enter the address: "); String address = sc.nextLine();
					timeA = System.currentTimeMillis();
					try {
						control.addClient(typeId, id, name, lastName, phone, address);
						System.out.println("The client has been created successfully");
					}
					catch(ExistingClientException ec) {
						System.out.println("\n" + ec.getMessage());
					}
					catch(EmptyInfoException ei) {
						System.out.println("\n" + ei.getMessage());
					}
					timeB = System.currentTimeMillis();
					totalDur = timeB - timeA;
					System.out.println("Duration of this operation in milliseconds: " + totalDur);
					break;
				case 2:

					System.out.print("\nEnter the id of the client: "); String id1 = sc.nextLine();
					System.out.println("\nSelect the type of turn from this list: ");
					System.out.println(control.printTypeList());
					int type = Integer.parseInt(sc.nextLine().toUpperCase());
					type--;
					timeA = System.currentTimeMillis();
					try {
						control.registerTurn(id1, type);
						int i = control.getClients().indexOf(control.search(id1));
						int j = control.getClients().get(i).getTurns().size()-1;

						System.out.println("\nThe turn " + control.getClients().get(i).getTurns().get(j).getName() + " has been assigned correctly to the client " + control.getClients().get(i).getId());
					}
					catch(NoExistingClientException nec) {
						System.out.println("\n" + nec.getMessage());
					}
					catch(ClientHasTurnException cht) {
						System.out.println("\n" + cht.getMessage());
					}
					catch(EmptyTypeListException etl) {
						System.out.println("\n" + etl.getMessage());
					}
					catch(InvalidTypeException it) {
						System.out.println("\n" + it.getMessage());
					}
					timeB = System.currentTimeMillis();
					totalDur = timeB - timeA;
					System.out.println("Duration of this operation in milliseconds: " + totalDur);
					break;
				case 3:
					System.out.print("\nEnter the name of the type of turn: "); String nType = sc.nextLine().toUpperCase();
					System.out.print("Enter the duration of this type of turn: "); float duration = Float.parseFloat(sc.nextLine());
					timeA = System.currentTimeMillis();
					try {
						control.addTurnType(nType, duration);
						System.out.println("\nType added correctly");
					}
					catch(ExistingTurnTypeException ett) {
						System.out.println("\n" + ett.getMessage());
					}
					catch(EmptyInfoException ein) {
						System.out.println("\n" + ein.getMessage());
					}
					timeB = System.currentTimeMillis();
					totalDur = timeB - timeA;
					System.out.println("Duration of this operation in milliseconds: " + totalDur);
					break;
				case 4:
					System.out.println("How do you want to update the type?");
					System.out.println("1) Manually \n2) Taking the time of the system");
					int opt = Integer.parseInt(sc.nextLine());
					while(opt!= 1 && opt!=2) {
						System.out.println("Please enter a valid option");
						opt = Integer.parseInt(sc.nextLine());
					}
					switch(opt) {
					case 1:
						System.out.println("Enter the year: "); int year = Integer.parseInt(sc.nextLine());
						System.out.println("Enter the month: "); int month = Integer.parseInt(sc.nextLine());
						System.out.println("Enter the day: "); int day = Integer.parseInt(sc.nextLine());
						System.out.println("Enter the hour: "); int hour = Integer.parseInt(sc.nextLine());
						System.out.println("Enter the minutes: "); int minutes = Integer.parseInt(sc.nextLine());
						System.out.println("Enter the seconds: "); int seconds = Integer.parseInt(sc.nextLine());
						timeA = System.currentTimeMillis();
						try {
							control.updateTimeManually(year, month, day, hour, minutes, seconds);
							System.out.println("Date updated correctly");
						}
						catch(DateIsLessException dil) {
							System.out.println(dil.getMessage());
						}
						timeB = System.currentTimeMillis();
						totalDur = timeB - timeA;
						System.out.println("Duration of this operation in milliseconds: " + totalDur);
						break;
					case 2:
						timeA = System.currentTimeMillis();
						try {

							control.updateTimeWithPc(LocalDateTime.now());
							System.out.println("Date updated correctly");
						}
						catch(DateIsLessException dile) {
							System.out.println(dile.getMessage());
						}
						timeB = System.currentTimeMillis();
						totalDur = timeB - timeA;
						System.out.println("Duration of this operation in milliseconds: " + totalDur);
						break;
						
					} 
					

					break;


				case 5:
					System.out.println("Attending the turns until this time...");
					timeA = System.currentTimeMillis();
					try {
						System.out.println(control.attendTurn());
					}
					catch(NoExistingTurnException net) {
						System.out.println(net.getMessage());
					}
					timeB = System.currentTimeMillis();
					totalDur = timeB - timeA;
					System.out.println("Duration of this operation in milliseconds: " + totalDur);
					break;
				case 6:
					timeA = System.currentTimeMillis();
					try {
						ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(SAVE_MODEL_PATH));
						oos.writeObject(control);
						oos.close();
						System.out.println("All was saved correctly");
					}
					catch(FileNotFoundException e) {
						System.out.println(e.getMessage());
					}
					catch(IOException e) {
						System.out.println("Error saving");
						System.out.println(e.getMessage());
					}
					timeB = System.currentTimeMillis();
					totalDur = timeB - timeA;
					System.out.println("Duration of this operation in milliseconds: " + totalDur);
					break;
				case 7:
					timeA = System.currentTimeMillis();
					try {
						ObjectInputStream ois = new ObjectInputStream(new FileInputStream(SAVE_MODEL_PATH));
						control = (Control) ois.readObject();
						ois.close();
						System.out.println("All was loaded correctly");
					}
					catch(FileNotFoundException fnf) {
						System.out.println(fnf.getMessage());
					}
					catch(ClassNotFoundException cnf) {
						System.out.println(cnf.getMessage());
					}
					catch(IOException e) {
						System.out.println(e.getMessage());
					}
					timeB = System.currentTimeMillis();
					totalDur = timeB - timeA;
					System.out.println("Duration of this operation in milliseconds: " + totalDur);
					break;
				case 8:
					System.out.println("Enter the client id: "); String clientId = sc.nextLine();
					System.out.println("How do you want the report?");
					System.out.println("1) File \n2) Console");
					int option = Integer.parseInt(sc.nextLine());
					timeA = System.currentTimeMillis();
					try {
						System.out.println(control.generateClientReport(clientId, option));
					}
					catch(NoExistingClientException nec) {
						System.out.println(nec.getMessage());
					}
					catch(FileNotFoundException fnf) {
						System.out.println("File not found");
					}
					catch(IOException e) {
						System.out.println(e.getMessage());
					}
					timeB = System.currentTimeMillis();
					totalDur = timeB - timeA;
					System.out.println("Duration of this operation in milliseconds: " + totalDur);
					break;
				case 9:
					System.out.println("Enter the turn name: "); String turnName = sc.nextLine().toUpperCase();
					System.out.println("How do you want the report?");
					System.out.println("1) File \n2) Console");
					int op = Integer.parseInt(sc.nextLine());
					timeA = System.currentTimeMillis();
					try {
						System.out.println(control.generateTurnReport(turnName, op));
					}
					catch(FileNotFoundException fnf) {
						System.out.println("File not found");
					}
					catch(IOException e) {
						System.out.println(e.getMessage());
					}
					timeB = System.currentTimeMillis();
					totalDur = timeB - timeA;
					System.out.println("Duration of this operation in milliseconds: " + totalDur);
					break;
				case 10:
					System.out.println("Enter the quantity of clients that you want to generate:"); int quantity = Integer.parseInt(sc.nextLine());
					timeA = System.currentTimeMillis();
					try {
						control.generateRandomClients(quantity);
					}
					catch(IOException e) {
						System.out.println("\nClients not generated");
					}
					timeB = System.currentTimeMillis();
					totalDur = timeB - timeA;
					System.out.println("Duration of this operation in milliseconds: " + totalDur);
					break;
				case 11:
					System.out.println("Enter the number of turns per day:"); int turnsByDay = Integer.parseInt(sc.nextLine());
					System.out.println("Enter the number of days to generate turns:"); int days = Integer.parseInt(sc.nextLine());
					timeA = System.currentTimeMillis();
					try {
						control.generateRandomTurns(turnsByDay, days);
					}
					catch(NoExistingClientException e) {
						System.out.println(e.getMessage());
					}
					catch(ClientHasTurnException e) {
						System.out.println(e.getMessage());
					}
					catch(EmptyTypeListException e) {
						System.out.println(e.getMessage());
					}
					catch(InvalidTypeException e) {
						System.out.println(e.getMessage());
					}
					timeB = System.currentTimeMillis();
					totalDur = timeB - timeA;
					System.out.println("Duration of this operation in milliseconds: " + totalDur);
					break;
				case 12:
					System.out.println(" 1) Sort by ID \n2) Sort by Name and LastName");
					int opti = Integer.parseInt(sc.nextLine());
					while(opti!= 1 && opti!=2) {
						System.out.println("Please enter a valid option");
						opt = Integer.parseInt(sc.nextLine());
					}
					switch (opti){
					case 1:
						timeA = System.currentTimeMillis();
						System.out.println(control.sortClientsById());
						timeB= System.currentTimeMillis();
						totalDur = timeB -timeA;
						System.out.println("Duration of this operation in milliseconds: " + totalDur);
						break;
					case 2: 
						timeA = System.currentTimeMillis();
						System.out.println(control.sortClientsByNameAndLastName());
						timeB= System.currentTimeMillis();
						totalDur = timeB -timeA;
						System.out.println("Duration of this operation in milliseconds: " + totalDur);
						break;
					default:
						System.out.println("invalid option");
						break;
					}
					break;
				case 13:
					System.out.println("Enter the name of the turn:"); String tn = sc.nextLine().toUpperCase();
					timeA = System.currentTimeMillis();
					try {
						System.out.println(control.binarySearchTurn(tn).toString());
					}
					catch(NullPointerException e) {
						System.out.println("The turn does not exists");
					}
					timeB= System.currentTimeMillis();
					totalDur = timeB -timeA;
					System.out.println("Duration of this operation in milliseconds: " + totalDur);
					break;
			

				case 0:
					System.out.println("\nGoodbye!");
					break;
				default:
					System.out.println("\nSelect a correct option");
					break;
				}
			}
			catch(NumberFormatException nfe) {
				System.out.println("\nInput mismatch");
			}
			long t2 = System.currentTimeMillis();
			long time = t2-t1;
			control.upgradeTime(time);
		}

	}




}
