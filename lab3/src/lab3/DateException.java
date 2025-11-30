package lab3;

/**
 * <p>Title: DateExceptionp</p>
 *
 * @author Steffano Orrego (N00963924), Beniamin Ilco (N00985709)
 */

public class DateException extends Exception {
	
	
	public DateException() 
	{
		super("Invalid value for Date");
	}
	
	public DateException(String message)
	{
		super(message);
	}
	
}
