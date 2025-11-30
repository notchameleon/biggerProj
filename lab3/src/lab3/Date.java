package lab3;

/**
* <p>Title: The Date Class</p>
*
* <p>Description: Objects of this type can store a month, day, and year as integers.
* The class provides accessors and mutators for all instance variables along with
* a toString method.</p>
*
* @author Steffano Orrego (N00963924), Beniamin Ilco (N00985709)
*/


/*
 * The Date Class
 * 
 * Objects of this type can store a month , day N year 
 * as integers with comparision capabilities and string parsing
 */

public class Date implements Comparable<Date> {
	private int dMonth;		//variable to store the month
	private int dDay;		//variable to store the day
	private int dYear;		//variable to store the year
 
	/**
	 * default constructor --
	 * sets month, day, and year to 1, 1, and 1800 respectively
	 */
	public Date()
	{
		dMonth = 1;
		dDay = 1;
		dYear = 1800;
	}
 
	/**
	 * parameterized constructor --
	 * sets month, day, and year to user specified values
	 * @param month value to be stored as this object's month
	 * @param day value to be stored as this object's day
	 * @param year value to be stored as this object's year
	 */
	public Date(int month, int day, int year) throws DateException
	{
		setMonth(month);
		setDay(day);
		setYear(year);
	}
	
	/*
	 * Parameterized constructor that accept a date string
	 * in format M/D/YYYY
	 * @param dateString the date in string format.
	 * @throws DateException if date is invalid.
	 * 
	 */
	
	public Date(String dateString) throws DateException
	{
		
		if (dateString == null || dateString.trim().isEmpty())
		{
			throw new DateException("Date string cannot be null or empty");
		}
		
		//Check for invalid characters (anything other than digits and /)
		
		if (!dateString.matches("[0-9/]+"))
		{
			throw new DateException("Date contains invalid characters: " + dateString);
		}
		
		String[] parts = dateString.split("/");
		
		if (parts.length != 3) 
		{
			throw new DateException("Invalid date format. Expected M/D/YYYY" + dateString);
		}
		
		try 
		{
			int month = Integer.parseInt(parts[0]);
			int day = Integer.parseInt(parts[1]);
			int year = Integer.parseInt(parts[2]);
			
			//Year must be 4 digits
			if(year < 1000 || year > 9999) 
			{
				throw new DateException("Year must be 4 digits: " + year);
			}
			
			setMonth(month);
			setDay(day);
			setYear(year);
			
		} 
		
		catch (NumberFormatException e) 
		
		{
			throw new DateException("Invalid number in date: " + dateString);
		}
	}
	
	/*
	 * The type Date must implement the inherited abstract method 
		Comparable<Date>.compareTo(Date)
	 * 
	 * This happens because The class Date 
	 * implements from Comparable interface  Comparable<E> 
	 * this interfaces takes in whatever object in this case Comparable<Date>
	 * and in the interface it has a abstractmethod of .compareTo()  
	 * compares memory address and its actual object orientated specific fields.
	 * 
	 * @Override
	 * compareTo(Date other) - int
	 * @param other the date to compare to 
	 * @return negative if this date is earlier , 0 if equal ,
	 * positive if later  
	 * 
	 */
	
	@Override
	public int compareTo(Date other) 
	{
		if (this.dYear != other.dYear) 
		{
			return this.dYear - other.dYear;
		}
		
		if (this.dMonth != other.dMonth)
		{
			return this.dMonth - other.dMonth;
		}
		
		return this.dDay - other.dDay;
		
	}
	
	/*
	 * equals(Object ob) - boolean
	 * @param obj the object to see if - specific fields equal eachother.
	 * @return boolean - true if specified fields are true or false if not.
	 */
	
	
	@Override 
	public boolean equals(Object obj)
	{
		if (this == obj)
		{
			return true;
		}
		
		if (!(obj instanceof Date))
		{
			return false;
		}
		
		
		Date other = (Date) obj;
		
		return this.dMonth == other.dMonth &&
				this.dDay == other.dDay &&
				this.dYear == other.dYear;
	}
	
	
	
	/**
	 * setDate --
	 * stores month, day, and year by calling the setMethods defined
	 * @param month value to be stored as this object's month
	 * @param day value to be stored as this object's day
	 * @param year value to be stored as this object's year
	 */
	public void setDate(int month, int day, int year) throws DateException
	{
		setMonth(month);
		setDay(day);
		setYear(year);
	}
 
	/**
	 * setMonth --
	 * stores the user specified value as the month
	 * @param month the value to be stored
	 */
	public void setMonth(int month) throws DateException
	{
		//dMonth = month;
		if (month >= 1 && month <= 12)
			dMonth = month;
		else
			throw new DateException("Invalid Month: month out of range");
	}
 
	/**
	 * setDay --
	 * stores the user specified value as the day
	 * @param day the value to be stored
	 */
	public void setDay(int day)
	{
		dDay = day;
	}
 
	/**
	 * setYear --
	 * stores the user specified value as the year
	 * @param year the value to be stored
	 */
	public void setYear(int year)
	{
		dYear = year;
	}
 
	/**
	 * getMonth --
	 * accessor for the month
	 * @return returns the value stored as the month
	 */
	public int getMonth()
	{
		return dMonth;
	}
 
	/**
	 * getDay --
	 * accessor for the day
	 * @return returns the value stored as the day
	 */
	public int getDay()
	{
		return dDay;
	}
 
	/**
	 * getYear --
	 * accessor for the year
	 * @return returns the value stored as the year
	 */
	public int getYear()
	{
		return dYear;
	}
 
	/**
	 * toString --
	 * returns the month, day, and year in the format: mm-dd-yyyy;
	 * leading zeros are NOT contained within the string
	 * @return a String containing the date in month-day-year format
	 */
	public String toString()
	{
		return (dMonth + "-" + dDay + "-" + dYear);
	}
}

