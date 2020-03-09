package CustomExceptions;

@SuppressWarnings("serial")
public class ExistingTurnTypeException extends Exception {
	public ExistingTurnTypeException(String name) {
		super("The turn type with name " + name  + " already exists" );
	}

}
