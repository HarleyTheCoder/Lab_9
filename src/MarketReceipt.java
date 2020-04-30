import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;
import java.util.TreeMap;

public class MarketReceipt {
	
	public static void main(String[] args) {
		
		//store array into list or hashmap.
		//use a method to fill that array with user input and return it
		
		Scanner scan = new Scanner(System.in);
		
		boolean yesOrNo = true;
		//TreeMap <String, Integer> helpRefer = new TreeMap<>();
		List<String> menuItems = new ArrayList<String>();
		menuItems.add("banana"); menuItems.add("eggs (12 cnt)"); menuItems.add("iceberg lettuce");
		menuItems.add("cucumber"); menuItems.add("roma tomato"); menuItems.add("strawberries (1 lb)");
		menuItems.add("avacado"); menuItems.add("milk (1g)"); menuItems.add("pineapple");
		menuItems.add("shredded cheese"); menuItems.add("beer (6pk)"); menuItems.add("beer (12pk)");
		menuItems.add("celery"); menuItems.add("onion"); menuItems.add("broccoli");
		menuItems.add("baby carrots (1 lb)"); menuItems.add("ground beef (2.25 lb)"); menuItems.add("ham (1 lb)");
		menuItems.add("chicken (2.4 lb)"); menuItems.add("grapes (1 lb)");
		
		List<Double> menuPrices = new ArrayList<Double>();
		menuPrices.add(0.21); menuPrices.add(1.09); menuPrices.add(1.00);
		menuPrices.add(0.40); menuPrices.add(0.30); menuPrices.add(2.12);
		menuPrices.add(0.25); menuPrices.add(2.09); menuPrices.add(1.00);
		menuPrices.add(1.98); menuPrices.add(5.79); menuPrices.add(10.49);
		menuPrices.add(1.28); menuPrices.add(0.70); menuPrices.add(1.11);
		menuPrices.add(0.98); menuPrices.add(9.77); menuPrices.add(6.74);
		menuPrices.add(6.30); menuPrices.add(1.98);
		
		//Instead of using an ArrayList for quantities, I find it easier to use another
		//HashMap to store all the final info together. Creating an extra list would be
		//pointless in the way I have it planned.
		HashMap<String, Integer> userItemQ = new HashMap<>();
		//Ok, it's not working exactly how I thought but I'm running out of time to change code
		//so it's staying like this.
		
		//establish list of groceries and prices
		HashMap<String, Double> itemsAndPrices = new HashMap<>();
		for (int i = 0; i < menuItems.size(); i++) {
			itemsAndPrices.put(menuItems.get(i), menuPrices.get(i));
		}
		
		System.out.println("Welcome to Harley's Market!");
		
		BaseMethods.showMenu(itemsAndPrices);
		
		
		while (yesOrNo) {
			System.out.print("\nWhat item would you like to order? " +
					"(Enter the item number or name.): ");
			userItemQ = BaseMethods.setUserItems(itemsAndPrices, userItemQ, scan);
			System.out.print("\nWould you like to order anything else? (y/n) " );
			yesOrNo = BaseMethods.isYesNo(scan.nextLine(), scan);
			if (yesOrNo) {
				BaseMethods.showMenu(itemsAndPrices);
			} else {
				BaseMethods.showReceipt(userItemQ, itemsAndPrices);
				System.out.print("\nAverage price per item in order was " +
				BaseMethods.getAverage(userItemQ, itemsAndPrices));
			}
			
		}
		
		
		
		
		
		scan.close();
	}
	
}
