package inventorymanagementproj;


import lab3.Date;
//Must import have lab 3 date?


/*
 * Item Class 
 * Stores quantity and expiration dates
 * for a food item.
 * 
 */

//WHY DOES THiS CLASS NEED THE INTERFACE COMPARABLE

public class Item implements Comparable<Item> {
	
	private int quantity;
	private Date expirationDate; 
	
	public Item(int quantity, Date expirationDate)
	{
		this.quantity = quantity;
		this.expirationDate = expirationDate;
	}
	
	/*
	 * getQuantity() - int
	 * @return an Integer of how many food items 
	 */
	
	
	public int getQuantity() 
	{
		return quantity;
	}
	
	/*
	 * setQuantity(int quantity) - void
	 * changes the amount of food items in quantity 
	 * can change how many food items
	 * @param quantity - to change the amount of food items.
	 */
	
	public void setQuantity(int quantity) 
	{
		this.quantity = quantity;
	}
	
	/*
	 * getExpirationDate() - Date 
	 * @return Date - returns a type of Date 
	 * which is a ExpirationDate.
	 * 
	 */
	
	public Date getExpirationDate() 
	{
		return expirationDate;
	}
	
	/*
	 * setExpirationDate(Date expirationDate) - void
	 * @param expirationDate
	 * 
	 * changes the expirationDate  
	 * 
	 * 
	 */
	public void setExpirationDate(Date expirationDate) 
	{
		this.expirationDate = expirationDate;
	}
	
	
	/*
	 * Compares item by expiration date (for FIFO/FEFO) \
	 * @param other , The Item object in this case
	 * with specific fields. 
	 *DOES compareTo and toString need @Override.
	 */
	
	@Override
	public int compareTo(Item other) 
	{
		return this.expirationDate.compareTo(other.expirationDate);
	}
	
	
	@Override
	public String toString()
	{
		return "Quantity: " + quantity + ", Expires: " + expirationDate; 
	}
	

}
