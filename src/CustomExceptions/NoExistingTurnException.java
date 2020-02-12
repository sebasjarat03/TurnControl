package CustomExceptions;

@SuppressWarnings("serial")
public class NoExistingTurnException extends Exception{
	public NoExistingTurnException(String turn) {
		super("The turn " + turn + " has not been assigned");
	}

}
