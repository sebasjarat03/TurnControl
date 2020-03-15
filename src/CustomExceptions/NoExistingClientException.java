package CustomExceptions;

@SuppressWarnings("serial")
public class NoExistingClientException extends Exception{
	
	public NoExistingClientException(String info,String id) {
		super("The client with the  " + info + ": " + id + " does not exists");
	}
	public NoExistingClientException(String msg) {
		super(msg);
	}
}
