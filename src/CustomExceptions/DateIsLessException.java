package CustomExceptions;

@SuppressWarnings("serial")
public class DateIsLessException extends Exception{
	public DateIsLessException () {
		super("This date is after the actual date, the date can only advance");
	}

}
