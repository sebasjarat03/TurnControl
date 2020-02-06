package CustomExceptions;


@SuppressWarnings("serial")
public class ExistingClientException extends Exception{
	
	public ExistingClientException(String id) {
		super("The client with the id " + id + " is already registered");
	}
	
}
