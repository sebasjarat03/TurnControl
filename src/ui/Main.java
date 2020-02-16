package ui;
import model.*;
import java.util.*;
import CustomExceptions.*;

public class Main {
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
		while (opc != 0) {
			try {
			System.out.println("\nSelect the option:");
			System.out.println("1) Add user 		2) Assign turn 		3) Attend turn");
			System.out.println(" 0) Exit");
			opc = Integer.parseInt(sc.nextLine());
			switch(opc) {
			case 1:
				try {
					System.out.print("\nEnter the type of id: "); String typeId = sc.nextLine();
					System.out.print("Enter the id: "); String id = sc.nextLine();
					System.out.print("Enter the name: "); String name = sc.nextLine();
					System.out.print("Enter the lastname: "); String lastName = sc.nextLine();
					System.out.print("Enter the phone: "); String phone = sc.nextLine();
					System.out.print("Enter the address: "); String address = sc.nextLine();
					control.addClient(typeId, id, name, lastName, phone, address);
					System.out.println("The client has been created successfully");
				}
				catch(ExistingClientException ec) {
					System.out.println("\n" + ec.getMessage());
				}
				catch(EmptyInfoException ei) {
					System.out.println("\n" + ei.getMessage());
				}
				break;
			case 2:
				try {
					System.out.print("\nEnter the id of the client: "); String id = sc.nextLine();
					control.registerTurn(id);
					int i = control.getClients().indexOf(control.search(id));
					int j = control.getClients().get(i).getTurns().size()-1;
					
					System.out.println("\nThe turn " + control.getClients().get(i).getTurns().get(j).getName() + " has been assigned correctly to the client " + control.getClients().get(i).getId());
				}
				catch(NoExistingClientException nec) {
					System.out.println("\n" + nec.getMessage());
				}
				catch(ClientHasTurnException cht) {
					System.out.println("\n" + cht.getMessage());
				}
				break;
			case 3:
				try {
					String turn = control.getTurnToAttend();
					System.out.println("\nThe client was attended or he left? \n1) Attended \n2) Left");
					
					int att = Integer.parseInt(sc.nextLine());
					while(att!=1 && att!=2) {
						System.out.println("Please enter a valid option: ");
						att = Integer.parseInt(sc.nextLine());
					}
					control.attendTurn(att);
					System.out.println("\nThe turn " + turn + " was called successfully" );
				}
				catch(NoExistingTurnException net) {
					System.out.println("\n" + net.getMessage());
				}
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
		}
		
	}
	
	
	

}
