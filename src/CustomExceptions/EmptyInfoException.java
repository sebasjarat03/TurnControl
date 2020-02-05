package CustomExceptions;

@SuppressWarnings("serial")
public class EmptyInfoException extends Exception{
	
	public EmptyInfoException(String info) {
		super("The field of " + info+ " cannot be empty.");
	}

}
