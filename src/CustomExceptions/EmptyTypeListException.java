package CustomExceptions;

@SuppressWarnings("serial")
public class EmptyTypeListException extends Exception {
	public EmptyTypeListException() {
		super("The list of turn types is empty");
	}

}
