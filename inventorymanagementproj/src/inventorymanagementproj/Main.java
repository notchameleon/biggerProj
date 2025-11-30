package inventorymanagementproj;


public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		InventoryManager manager = new InventoryManager();
		
		
		// load Initial inventory
		manager.loadInventory("inventory.txt");
		
		// process Transactions
		manager.processTransactions("transactions.txt");
		
		manager.close();
		
		System.out.println("\n--- PROCESSING COMPLETE ---");
		System.out.println("Check errors.txt for any invalid entries");
	}

}
