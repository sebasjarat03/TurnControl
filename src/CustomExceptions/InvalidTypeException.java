package CustomExceptions;

@SuppressWarnings("serial")
public class InvalidTypeException extends Exception{
	public InvalidTypeException() {
		super("Incorrect turn type");
	}

}
