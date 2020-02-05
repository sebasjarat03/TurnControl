package ui;
import model.*;
import java.util.*;
import CustomExceptions.*;

public class Main {
	public Scanner sc;
	public Control control;
	
	public static void main(String args[]) {
		Main main = new Main();
		
	}
	
	public Main() {
		sc = new Scanner(System.in);
		control = new Control();
	}
	
	public void menu() {
		int opc = 10;
		while (opc != 0) {
			System.out.println("Select the option:");
			System.out.println("1) Add user 		2) Register turn 		3) Attend turn");
			System.out.println(" 0) Exit");
			opc = Integer.parseInt(sc.nextLine());
			switch(opc) {
			case 1:
				
			}
		}
		
	}
	

}
