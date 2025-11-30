package inventorymanagementproj;

import lab3.Date;
import exceptionclasses.EmptyListException;
import lists.ArrayList;
import lists.ArrayOrderedList;

/*
 * Category Class
 * Stores a category name &
 * An ordered list of Items.
 */

public class Category implements Comparable<Category>
{
	
	private String name;
	private ArrayOrderedList<Item> items;
	
	public Category(String name) 
	{
		this.name = name;
		this.items = new ArrayOrderedList<Item>();
	}
	
	public String getName()
	{
		return name;
	}
	
	public ArrayOrderedList<Item> getItems() 
	{
		return items;
	}
	
	/*
	 * addItem(Item item) - void 
	 * @param item, an item with an expiration date
	 *Adds an item to the category
	 *AOL maintains autommatically-
	 *maintaining order by expiration date.
	 * 
	 */

	public void addItem(Item item)
	{
		items.add(item);
	}
	
	/*
	 * 
	 * addOrCombineItem(int quality, Date expDate) - void  
	 * Combines quantity if item with same expiration date exists
	 *@param quantity - amount of food items 
	 *@param expDate - the expirationDate of the food item
	 */
	
	public void addOrCombineItem(int qty , Date expDate) 
	{
		//Must iterate and look through the list 
		//Manually. 
		
		// Looks for existing items with same expiration date.
		
		
		//Create temporary list to hold items while we search
		
		ArrayOrderedList<Item> tempList = new ArrayOrderedList<Item>();
		boolean found = false;
		
		while(!items.isEmpty())
		{
			try
			{
				Item item = items.removeFirst();

				if (!found && item.getExpirationDate().equals(expDate))
				//Found matching expiration date - combine quantities
				{
					item.setQuantity(item.getQuantity() + qty);
					found = true;
				}
			
				tempList.add(item);
			
			} 
			
			catch (EmptyListException e)
			
			{
				break;
			}
			
		}
		
		
		//If not found , add new item to tempList.
		if (!found)
		{
			tempList.add(new Item(qty, expDate));
		}
		
		//Restore items from temp list (will re-sort)
		
		while (!tempList.isEmpty())
		{
			try
			{
				items.add(tempList.removeFirst());
			} 
			
			catch (EmptyListException e)
			
			{
				break;
			}
		
		}
	}
	
	/*
	 * Removes quantity from items (FIFO)
	 * Since items are already sorted by expiration date
	 * - we remove the front.
	 * 
	 * removeQuantity(int quantityToRemove) - int
	 * @param quantityToRemove amount to remove.
	 * @return quantity removed.
	 */
	
	public int removeQuantity(int quantityToRemove)
	{
		int removed = 0;
		
		while (quantityToRemove > 0 && !items.isEmpty())
		{
			try
			{
				Item firstItem = items.first(); // Get earliest expiration.
				int currentQuantity = firstItem.getQuantity();
				
				if (currentQuantity <= quantityToRemove)
				{
					//Remove entire item
					items.removeFirst();
					removed += currentQuantity;
					quantityToRemove -= currentQuantity;
				}	
				
				else
				//Partial removal	
				{
				firstItem.setQuantity(currentQuantity - quantityToRemove);
				removed += quantityToRemove;
				quantityToRemove = 0;
				}
			} 
			
			catch (EmptyListException e) 
		
			{
				break;
			}
		}
		
		return removed;
	}
	
	
	/*
	 * removeExpiredItems(Date currentDate) - ArrayOrderedList<Item> 
	 * Removes all expired items based on given date
	 * @param currentDate the date to check against
	 * @return ArrayOrderedList of expired items were removed 
	 */
	
	public ArrayOrderedList<Item> removeExpiredItems(Date currentDate)
	{
		ArrayOrderedList<Item> expiredItems = new ArrayOrderedList<Item>();
		ArrayOrderedList<Item> itemsToKeep = new ArrayOrderedList<Item>();
		
		//Remove all items, check each , and sort into expired vs keep
		
		while (!items.isEmpty())
		{
			try
			{
				Item item = items.removeFirst();
				
				if (item.getExpirationDate().compareTo(currentDate) < 0)
				{
					expiredItems.add(item);
				} 
				
				else 
				
				{
					itemsToKeep.add(item);
				} 	
				
			}	catch (EmptyListException e)
				
				{
					break;
				}
			}
		
			//Restore non expired items
			items = itemsToKeep;
			return expiredItems;
		}
		
	
	/*
	 * getTotalQuantity() - int 
	 * 
	 * Gets total quantity across all items in this category
	 * Uses temporary removal and re-adding since no get() method.
	 * @return totalQuantity across all items in the same category
	 */
		
	
	public int getTotalQuantity() 
	{
		int total = 0;
		ArrayOrderedList<Item> tempList = new ArrayOrderedList<Item>();
		
		//Remove all, count quantities, and add back
		
		while (!items.isEmpty())
		{
			try
			{
				Item item = items.removeFirst();
				total += item.getQuantity();
				tempList.add(item);
			} catch (EmptyListException e)
			
			{
				break;
			}
		}
		
		// Restore items
		
		while (!tempList.isEmpty())
		{
			try
			{
				items.add(tempList.removeFirst());
			} catch (EmptyListException e) 
			
			{
				break;
			}
		}
		
		
		return total;
		
	}
	
	
	/*
	 * isEmpty() - booleans
	 * Checks if category has no items
	 * @return true if has items- false if no items
	 */
	
	public boolean isEmpty() 
	{
		return items.isEmpty();
	}
	
	/*
	 * @Override?
	 * The type Category must implement the inherited abstract method 
Comparable<Category>.compareTo(Category)
	 * 
	 * 
	 * compareTo(Category other) - int 
	 * @param other , An object of category to be compared. 
	 * @return an integer to determine- 
	 * negative , zero or a positive integer as this category names
	 * is less than or equal to or greater than specified category names.
	 * 
	 * 
	 * Compares this category with specified category for order,
	 */
	
	@Override
	public int compareTo(Category other)
	{
		return this.name.compareToIgnoreCase(other.name);
	}
	
	/*
	 * toString 
	 * @return A string representation of categories and items.
	 * Includes its name and all items.
	 * The items are displayed in sorted order. If the category is empty - a message indicates 
	 * No items will be displayed 
	 */
	@Override
	public String toString()
	{
		String returnMe = "Category: " + name + "\n";
		
		if (items.isEmpty())
		{
			returnMe += " NO Items in inventory\n";
		} else
			
		{
			//Remove and add back the display items.
			
			ArrayOrderedList<Item> tempList = new ArrayOrderedList<Item>();
			
			while (!items.isEmpty())
			{
				try
				{
					Item item = items.removeFirst();
					returnMe += " " + item + "\n";
					tempList.add(item);
				} catch (EmptyListException e)
				
				{
					break;
				}
			}
		
		//Restore items
			while (!tempList.isEmpty())
			{
				try
				{
					items.add(tempList.removeFirst());
				} catch (EmptyListException e)
				
				{
					break;
				}
			}
		}
		return returnMe;
	}
}
