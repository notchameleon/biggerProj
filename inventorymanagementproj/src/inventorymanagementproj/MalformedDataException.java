package inventorymanagementproj;

public class MalformedDataException extends Exception {
	
	public MalformedDataException() 
	{
		super("Malformed data in input");
	}
	
	public MalformedDataException(String message)
	{
		super(message);
	}
	
}
