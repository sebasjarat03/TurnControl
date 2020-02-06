package CustomExceptions;

@SuppressWarnings("serial")
public class NoExistingClientException extends Exception{
	
	public NoExistingClientException(String id) {
		super("The client with the id " + id + " does not exists");
	}
}
