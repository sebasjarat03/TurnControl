package CustomExceptions;

@SuppressWarnings("serial")
public class NoExistingTypeException extends Exception {
	public NoExistingTypeException(String name) {
		super("The type with name " + name + " does not exists");
	}

}
