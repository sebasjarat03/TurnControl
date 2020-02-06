package CustomExceptions;

@SuppressWarnings("serial")
public class ClientHasTurnException extends Exception{
	
	public ClientHasTurnException(String id) {
		super("The client with the id " + id + " already has a turn");
	}

}
