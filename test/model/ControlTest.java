package model;

import static org.junit.Assert.*;

import java.time.LocalDateTime;

import org.junit.Test;
import CustomExceptions.*;

public class ControlTest {
	
	Control c;
	
	public void setup1() {
		//there is no existing clients in the program
		c = new Control();
		
		
	}
	
	public void setup2() {
		//the client does not exists but there are some other clients
		c = new Control();
		Client c1 = new Client("CC", "1068895", "Pedro", "Hernandez", "3165577", "cra 23 #20-36");
		Client c2 = new Client("CC","63883786" ,"Sandra", "Gutierrez", "", "");
		Client c3 = new Client("CC", "10097733", "Alfonso", "Bustamante", "", "");
		TurnType tt1 = new TurnType("SERVIR DESAYUNO", 20);
		TurnType tt2 = new TurnType("SERVIR ALMUERZO", 26);
		TurnType tt3 = new TurnType("SERVIR CENA", 30);
		c.getTurnTypes().add(tt1);
		c.getTurnTypes().add(tt2);
		c.getTurnTypes().add(tt3);
		c.getClients().add(c1);
		c.getClients().add(c2);
		c.getClients().add(c3);
		
	}
	
	public void setup3() {
		//the client exists
		c = new Control();
		Client c1 = new Client("CC", "1068895", "Pedro", "Hernandez", "3165577", "cra 23 #20-36");
		Client c2 = new Client("CC","63883786" ,"Sandra", "Gutierrez", "", "");
		Client c3 = new Client("CC", "10097733", "Alfonso", "Bustamante", "", "");
		Client c4 = new Client("CC", "44695237", "Andres", "Lopez", "", "");
		TurnType tt1 = new TurnType("SERVIR DESAYUNO", 20);
		TurnType tt2 = new TurnType("SERVIR ALMUERZO", 26);
		TurnType tt3 = new TurnType("SERVIR CENA", 30);
		c.getTurnTypes().add(tt1);
		c.getTurnTypes().add(tt2);
		c.getTurnTypes().add(tt3);
		c.getClients().add(c1);
		c.getClients().add(c2);
		c.getClients().add(c3);
		c.getClients().add(c4);
		
	}
	
	public void setup4()throws Exception {
		c = new Control();
		Client c1 = new Client("CC", "1068895", "Pedro", "Hernandez", "3165577", "cra 23 #20-36");
		c.getClients().add(c1);
		TurnType tt1 = new TurnType("SERVIR DESAYUNO", 20);
		c.getTurnTypes().add(tt1);
		c.registerTurn("1068895", 0);
		
	}
	
	
	//Register Clients tests

	@Test
	public void test1() throws Exception{
		setup1();
		c.addClient("CC", "1068895", "Pedro", "Hernandez", "3165577", "cra 23 #20-36");
		assertEquals("The size is not correct, this means that the client was not added", 1, c.getClients().size());
		assertEquals("The id does not match, that means that the parameter was not joined correctly", "1068895", c.getClients().get(0).getId());
	}
	
	@Test
	public void test2() throws Exception {
		setup2();
		c.addClient("CC", "44695237", "Andres", "Lopez", "", "");
		assertEquals("The size is not correct, this means that the client was not added", 4, c.getClients().size());
		assertEquals("The id does not match, that means that the parameter was not joined correctly", "44695237", c.getClients().get(3).getId());
		
	}
	
	@Test(expected =ExistingClientException.class  )
	public void test3() throws Exception{
		setup3();
		c.addClient("CC", "44695237", "Andres", "Lopez", "", "");
	}
	
	//searching tests
	
	@Test
	public void test4() throws Exception {
		setup1();
		Client temp = new Client ("CC", "6455443", "Gabriel", "Martinez", "", "" );
		c.getClients().add(temp);
		assertEquals("The objects are not equals", temp, c.search("6455443"));
		
	}
	
	@Test
	public void test5() {
		setup2();
		assertEquals("The searching does not return null", null, c.search("44695237"));
		
	}
	
	@Test
	public void test6() {
		setup1();
		assertEquals("The searching does not return null", null, c.search("1068895"));
	}
	
	@Test
	public void test7() {
		setup3();
		Client copy = c.search("1068895");
		assertEquals("The objects are not equals", copy, c.search("1068895"));
	}
	
	//Assign turns tests
	
	@Test(expected = ClientHasTurnException.class)
	public void test8() throws Exception{
		setup4();
		c.registerTurn("1068895", 0);
	}
	
	@Test
	public void test9() throws Exception {
		setup3();
		c.registerTurn("44695237", 1);
		assertEquals("Error, the turn has not been added", 1, c.search("44695237").getTurns().size());
	}
	
	//Generate turns tests
	
	@Test
	public void test10()throws Exception{
		setup3();
		//checking that the first turn is A00
		c.registerTurn("1068895", 1);
		assertEquals("The turn has not been assigned as it had to be", 1, c.search("1068895").getTurns().size());
		assertEquals("The turn is not A00 as it should be", "A00", c.search("1068895").getTurns().get(0).getName());
		
		//checking that the next turn is consecutive
		c.registerTurn("63883786", 2);
		assertEquals("The turn has not been assigned as it had to be", 1, c.search("63883786").getTurns().size());
		assertEquals("The turn is not A01 as it should be", "A01", c.search("63883786").getTurns().get(0).getName());
	}
	
	
	@Test
	public void test11() throws Exception {
		setup3();
		//setting the turn to D99 and assigning it
		c.setTurnToAssign("D99");
		c.registerTurn("1068895", 1);
		assertEquals("The turn is not D99 as it should be", "D99", c.search("1068895").getTurns().get(0).getName());
		//checking that the next turn is E00
		c.registerTurn("63883786", 0);
		assertEquals("The turn is not E00 as it should be", "E00", c.search("63883786").getTurns().get(0).getName());
	}
	
	@Test
	public void test12()throws Exception{
		setup3();
		//setting turn to Z99 and assingning it
		c.setTurnToAssign("Z99");
		c.registerTurn("1068895", 0);
		assertEquals("The turn is not Z99 as it should be", "Z99", c.search("1068895").getTurns().get(0).getName());
		//checking that the next turn is A00
		c.registerTurn("63883786", 1);
		assertEquals("The turn is not A00 as it should be", "A00", c.search("63883786").getTurns().get(0).getName());
		
	}
	
	@Test
	public void test13()throws Exception{
		setup3();
		//assigning turns
		c.registerTurn("1068895", 0);
		c.registerTurn("63883786", 1);
		LocalDateTime time = c.getSystemTime().getTime2();
		c.getSystemTime().setTime(time.plusMinutes(20));
		//calling turns and telling that they were attended
		c.attendTurn();
		assertEquals("The turn has not been called", Turn.CALLED, c.search("1068895").getTurns().get(0).getStatus());
		//checking that a turn is pending
		assertEquals("The turn is not pending", Turn.PENDING, c.search("63883786").getTurns().get(0).getStatus());
		//consulting what turn is next to attend
		assertEquals("The turn should be A01", "A01", c.getTurnToAttend());
	
	}
	
	@Test(expected = NoExistingClientException.class)
	public void test14()throws Exception{
		setup1();
		//checking that a turn cannot be assigned to a non-existing client
		c.registerTurn("1068895", 0);
	}
	
	@Test
	public void test15()throws Exception{
		setup1();
		//checking that a not assigned client cannot be called
		
		assertEquals("The turn cannot be called", "No turns attended", c.attendTurn());
	}

}
