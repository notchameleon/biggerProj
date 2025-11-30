package inventorymanagementproj;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.BufferedReader;
import lists.ArrayOrderedList;
import java.io.FileWriter;
import java.io.FileReader;
import exceptionclasses.EmptyListException;
import lab3.DateException;
import lab3.Date;
import lists.ArrayList;





public class InventoryManager {
	private ArrayOrderedList<Category> categories;
	private PrintWriter errorLog;
	private static final String ERROR_LOG_FILE = "errors.txt";
	
	public InventoryManager() 
	{
		categories = new ArrayOrderedList<Category>();
		try
		{
			//
			errorLog = new PrintWriter(new FileWriter(ERROR_LOG_FILE));
		} catch (IOException e) 
		
		{
			System.err.println("Error creating error log file: " + e.getMessage());		}
		
		
	    }
		
	/*
	 * findOrCreateCategory(String categoryName) - Category
	 * Searches for category name using temporary removal and readding!!!!
	 * @param categoryName - searches for existing String category name. 
	 */
	
	private Category findOrCreateCategory(String categoryName) 
	{
		ArrayOrderedList<Category> tempList = new ArrayOrderedList<Category>();
		Category foundGroup = null;
		
		while(!categories.isEmpty())
		{
			try
			{
				Category cat = categories.removeFirst();
				
				if (cat.getName().equalsIgnoreCase(categoryName))
				{
					foundGroup = cat;
				}
				tempList.add(cat);
			} catch (EmptyListException e)
			{
				break;
			}
		}
		
		//IF THIS IS NOT HERE rice shows up. 
		//MISSING: I NEVER RESTORE tempLIST back to categories.
		while (!tempList.isEmpty())
		{
			try
			{
				categories.add(tempList.removeFirst());
			} catch (EmptyListException e)
			
			
			{
				break;
			}
		}
		
		
		
		//If Found Return
		if (foundGroup != null)
		{
			return foundGroup;
		}
		
		//Otherwise create new category and add (ArrayOrderedList will keep it sorted)
		
		Category newCat = new Category(categoryName);
		
		categories.add(newCat);
		return newCat;
		
	}
	
	
	
	
	
	/*
	 * singleInventoryLine(String line, int lineNumber) - void 
	 * 
	 * 
	 * Processes a single inventory line
	 * in the text file inventory.txt
	 * @param line, String Category of food.
	 * @param lineNumber, int A Quantity of the amount of certain food items.
	 * @throws MalformedDataException - if any of the special conditions are met.
	 * @throws DateException - if any of the special conditions are met.
	 */
	
	
	private void singleInventoryLine(String line, int lineNumber) throws MalformedDataException, DateException
	{
		String[] parts = line.split("\\s+");
		
		if (parts.length < 3) 
		{
			throw new MalformedDataException("Missing required fields (EXPECTED: category quantity date)");
		}
		
		
		String category = parts[0];
		String quantityStr = parts[1];
		String dateStr = parts[2];
		
		//Validate quantity.
		
		int qty;
		try 
		{
			qty = Integer.parseInt(quantityStr);
			if (qty < 0) 
			{
				throw new MalformedDataException("Quantity cannot be negative: " + qty);
			}
		} catch (NumberFormatException e)
		
		{
			throw new MalformedDataException("Quantity is not numeric: " + quantityStr);
		}
		
		
		//Validate and parse date.
		
		Date expDate = new Date(dateStr);
		
		//Add to inventory.
		
		Category cat = findOrCreateCategory(category);
		cat.addOrCombineItem(qty, expDate);	
	}
	
	
	
	/*
	 * 
	 * displayInventory() - void 
	 * 
	 * Displays current inventory.	 * 
	 * 
	 */
	
	
	public void displayInventory()
	{
		System.out.println("CURRENT INVENTORY:");
		if (categories.isEmpty())
		{
			System.out.println("  Inventory is empty\n");
		} else 
		{
			//Display all categories using temporary removal and re-adding.
			ArrayOrderedList<Category> tempList = new ArrayOrderedList<Category>();
			
			while (!categories.isEmpty())
			{
				try
				{
					Category cat = categories.removeFirst();
					System.out.print(cat);
					tempList.add(cat);
				} catch (EmptyListException e)
				
				{
					break;
				}
			}
		
			//Restore categories 
			
			while(!tempList.isEmpty())
			{
				try
				{
					categories.add(tempList.removeFirst());
				} catch (EmptyListException e)
				
				{
					break;
				}
			}		
		}	
	}
	
	
	
	/*
	 * logError(int, String , String) - void
	 * 
	 * Logs an error to the error log file
	 * @param lineNumber, which line in inventory that had an error 
	 * @param line, original Input 
	 * @param errorMessage, error message?
	 */
	
	private void logError(int lineNumber, String line, String errorMessage)
	{
		String logEntry = "Line " + lineNumber + ": " + errorMessage + 
				" --- ORIGINAL INPUT: " + line;
		
		if (errorLog != null)
		{
			errorLog.println(logEntry);
			errorLog.flush();
		}
		
		System.out.println("ERROR - " + logEntry);
	}
	
	/*
	 * 
	 * close() - void 
	 * Closes the error log
	 *
	 */
	
	public void close()
	{
		if (errorLog != null) 
		{
			errorLog.close();
		}
	}
	
	
	/*
	 * processDonation(String[] parts, String line) - void
	 * 
	 * Processes an 'a' (donation) transaction
	 * - (HAVE SAME EXPIRATION DATE?)
	 * 
	 */
	
	private void processDonation(String[] parts, String line) throws MalformedDataException, DateException
	{
		
		if (parts.length < 4) 
		{
			throw new MalformedDataException("Donation missing required fields (EXPECTED: a category quantity date)");
		}
		
		
		String category = parts[1];
		String quantityStr = parts[2];
		String dateStr = parts[3];
		
		
		//Validate quantity
		
		int qty;
		
		try
		{
			qty = Integer.parseInt(quantityStr);
			
			if (qty < 0)
			{
				throw new MalformedDataException("Quantity cannot be negative: " + qty);
			}
		} catch (NumberFormatException e) 
		
		
		{
			throw new MalformedDataException("Quantity is not numeric: " + quantityStr);
		}
		
		//Validate and parse date
		
		Date expDate = new Date(dateStr);
		
		// Add donation 
		
		Category cat = findOrCreateCategory(category);
		
		cat.addOrCombineItem(qty, expDate);
		
		System.out.println("DONATION ADDED: " + qty + " " + category + " (EXPIRES " + expDate + ")");
		
	}
	
	/*
	 * 	processDistrubition(String[] parts, String line) - void
	 * *Guest visited the nest and took category of item and quantity.
	 * 
	 * Processes a 'd' (distribution) transaction
	 */
	
	private void processDistribution(String[] parts, String line) throws MalformedDataException
	{
		if (parts.length < 3) 
		{
			throw new MalformedDataException("Distribution missing required fields (EXPECTED: d category quantity)");
		}
		
		
		String categoryName = parts[1];
		String quantityStr = parts[2];
		
		//Validate quantity.
		
		int qty;
		try
		{
			qty = Integer.parseInt(quantityStr);
			if (qty < 0) 
			{
				throw new MalformedDataException("Quantity cannot be negative: " + qty);
			}
		} catch (NumberFormatException e)
		
		
		{
			throw new MalformedDataException("Quantity is not numeric: " + quantityStr);
		}
		
		
		//Find category using temporary removal and re-adding
		
		ArrayOrderedList<Category> tempList = new ArrayOrderedList<Category>();
		
		Category foundGroup = null;
		
		while (!categories.isEmpty())
		{
			try
			{
				Category cat = categories.removeFirst();
				if (cat.getName().equalsIgnoreCase(categoryName))
				{
					foundGroup = cat;
				}
				tempList.add(cat);
			} catch (EmptyListException e)
			
			{
				break;
			}
		}
		
		
		//Restore categories.
		
		while (!tempList.isEmpty())
		{
			try
			{
				categories.add(tempList.removeFirst());
			} catch (EmptyListException e)
			
			{
				break;
			}
		}
		
		
		
		
		if (foundGroup == null)
		{
			throw new MalformedDataException("Category not found in inventory: " + categoryName);
		}
		
		int removed = foundGroup.removeQuantity(qty);		
		System.out.println("DISTRIBUTED: " + removed + " " + categoryName + " (REQUESTED: " + qty + ")");
		
		//Remove category if empty
		
		if (foundGroup.isEmpty())
		{
			categories.remove(foundGroup);
			System.out.println("Category '" + categoryName + "' is now empty and removed from inventory");
		}
	}
	
	/*
	 *  processCleanup(String[] parts, String line) - void
	 * *NEST wants to check  the inventory  for this expiration date
	 *  remove all expired items
	 * 
	 * Processes a 'c' (cleanup) transaction
	 * @param parts, An array holding certain positions of strings 
	 * @param line, which line the specific scenario happens.
	 * @throws MalformedDataException - if any of the special conditions are met.
	 * @throws DateException - if any of the special conditions are met.
	 */
	
	
	private void processCleanup(String[] parts, String line) throws MalformedDataException, DateException
	{
		if (parts.length < 2)
		{
			throw new MalformedDataException("Cleanup missing date (EXPECTED: c date)");
		}
		
		String dateStr = parts[1];
		Date checkDate = new Date(dateStr);
		
		System.out.println("CLEAING UP items EXPIRED before " + checkDate);
		System.out.println("\nEXPIRED ITEMS REMOVED: ");
		
		boolean foundExpired = false;
		
		ArrayOrderedList<Category> categoriesToRemove = new ArrayOrderedList<Category>();
		ArrayOrderedList<Category> tempList = new ArrayOrderedList<Category>();
		
		//Process each category
		
		while (!categories.isEmpty())
		{
			try
			{
				Category cat = categories.removeFirst();
				ArrayOrderedList<Item> expiredItems = cat.removeExpiredItems(checkDate);
				
				if (!expiredItems.isEmpty())
				{
					foundExpired = true;
					System.out.println(" " + cat.getName() + ": ");
					
					//Display expired Items.
					
					while (!expiredItems.isEmpty())
					{
						try
						{
							Item expItem = expiredItems.removeFirst();
							System.out.println("    - " + expItem);
						} catch (EmptyListException e)
						
						{
							break;
						}				
					}		
				}
				
				if (cat.isEmpty())
				{
					categoriesToRemove.add(cat);
				} else 
				
				{
					tempList.add(cat);
				}
			} catch (EmptyListException e)
			
			{
			break;
			}
		}	
				
				
					//Restore non empty categories 
		while (!tempList.isEmpty()) {
			try {
				categories.add(tempList.removeFirst());
			} catch (EmptyListException e) {
				break;
			}
		}
		
		
		if (!foundExpired)
		{
			System.out.println(" No expired items found");
		}
	
	}
		
	
	
	
	/*
	 *loadInventory(String fileName) - void
	 * 
	 * Loads inventory from inventory.txt
	 * 
	 * Each entry represents a category of food
	 * a quantity , and expiration date.
	 * 
	 * @param filename - the text file in the package.
	 */
	
	
	public void loadInventory(String filename)
	{
		System.out.println("--- LOADING INVENTORY FROM " + filename + " ---\n");
		
		//ArrayOrderedList<String> errors = new ArrayOrderedList<String>();
		int lineNumber = 0;
		String line = "";

		
		try (BufferedReader br = new BufferedReader(new FileReader(filename)))
		{
			
			while((line = br.readLine()) != null)
			{
				lineNumber++;
				line = line.trim();
				
				if (line.isEmpty())
					{
					continue;
					}
				
				try 
				{
					singleInventoryLine(line, lineNumber);
				} catch (Exception e)
				
				{
					String errorMsg = "Line " + lineNumber + ": " + e.getMessage() +
							" --- ORIGINAL INPUT: " + line;
				//	errors.add(errorMsg);
					if (errorLog != null)
					{
						errorLog.println(errorMsg);
						errorLog.flush();
					}
				}
			}
		} catch (IOException e)
		
		{
			String errorMsg = "Error reading inventory file: "  + e.getMessage();
			
		//	errors.add(errorMsg);
		
			if (errorLog != null)
			{
				errorLog.println(errorMsg);
				errorLog.flush();
			}
		}
	System.out.println("Inventory loaded succesfully!\n");
	displayInventory();
	
	//Print errors after inventory display
	//Since you cant iterate normalyl use temporary removal
/*	
	ArrayOrderedList<String> tempList = new ArrayOrderedList<String>();
	
	while(!errors.isEmpty())
	{
		try 
		{
			String error = errors.removeFirst();
			System.out.println("ERROR - " + error);
			tempList.add(error);
		} catch (EmptyListException e)
		
		{
			break;
		}
	}
	
	//Restore errors if needed
	
	while (!tempList.isEmpty())
	{
		try
		{
			errors.add(tempList.removeFirst());
		} catch (EmptyListException e)
		
		{
			break;
		}
	}
	*/
	 
	}
	
	
	/*
	 * 
	 * singleTransactionLine(String line, int lineNumber)
	 * 
	 * 
	 * Processes a single inventory line
	 * @param line, String Category of food.
	 * @param lineNumber, int A Quantity of the amount of certain food items.
	 * @throws MalformedDataException - if any of the special conditions are met.
	 * @throws DateException - if any of the special conditions are met.
	 * 
	 */
	
	private void singleTransactionLine(String line, int lineNumber) throws MalformedDataException , DateException
	{
		String[] parts = line.split("\\s+");
		
		if (parts.length == 0)
		{
			throw new MalformedDataException("Empty transaction");
		}
		
		String transactionCode = parts[0];
		
		
		//#FUNSWITCHSTATEMENTS ty python.
		switch (transactionCode)
		{
		case "a": // Add donation
			processDonation(parts,line);
			break;
		case "d":
			processDistribution(parts, line);
			break;
		case "c":
			processCleanup(parts, line);
			break;
		default:
			throw new MalformedDataException("Invalid transaction code: " + transactionCode);
		}
		
		
		
	}
	
	
	
	/*
	 * loadTransactions() - void
	 * Processes transactions from file 
	 * @param fileName - transactions.txt
	 */
	
	public void processTransactions(String fileName)
	{
		System.out.println("\n--- PROCESSING TRANSACTIONS FROM " + fileName + " ---\n");
		int lineNumber = 0;
		String line = "";

		try (BufferedReader br = new BufferedReader(new FileReader(fileName)))
		{
			
			while((line = br.readLine()) != null)
			{
				lineNumber++;
				line = line.trim();
				
				if (line.isEmpty()) {
					continue;
				}
				
				
				System.out.println("Transaction: " + line);
				
				
				try 
				{
					singleTransactionLine(line, lineNumber);
				} catch (Exception e)
				
				{
					logError(lineNumber, line, e.getMessage());
					System.out.println("ERROR: " + e.getMessage());
				}
				
				
				System.out.println();
				displayInventory();
				System.out.println("--------------------\n");
				
			}
		} catch (IOException e)
		
		{
			logError(lineNumber, line , e.getMessage());
			System.out.println("ERROR: " + e.getMessage());
		}
		
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	

	}	
