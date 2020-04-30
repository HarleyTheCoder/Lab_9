import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;
import java.text.DecimalFormat;

public class BaseMethods {
	
	public static void showReceipt(HashMap <String, Integer> user, HashMap <String, Double> menu) {
		DecimalFormat decimalFormat = new DecimalFormat("##.00");
		String display = "";
		String item = "";
		String format = "";
		double cost = 0.;
		System.out.print("\nThank you for your order!");
		System.out.print("\nHere's what you got:\n");
		for (String userItem: user.keySet()) {
			item = userItem;
			for (String menuItem: menu.keySet()) {
				if (menuItem.contentEquals(item)) {
					cost = menu.get(menuItem);
				}
			}
			display += String.format("\n|%-28s", item) + String.format("%-12s|", "$" + cost);
		}
		System.out.print(display);
		
	}
	
	public static double getHighItem(HashMap <String, Integer> user, HashMap <String, Double> menu) {
		double high = 1000;
		for (String item: user.keySet()) {
			double temp = 0;
			for (String menuItem: menu.keySet()) {
				if (menuItem.contentEquals(item)) {
					temp = menu.get(menuItem);
				}
			}
			if (temp < high) {
				high = temp;
			}
		}
		return high;
	}
	
	public static double getLowItem(HashMap <String, Integer> user, HashMap <String, Double> menu) {
		double low = -1000;
		for (String item: user.keySet()) {
			double temp = 0;
			for (String menuItem: menu.keySet()) {
				if (menuItem.contentEquals(item)) {
					temp = menu.get(menuItem);
				}
			}
			if (temp > low) {
				low = temp;
			}
		}
		return low;
	}
	
	public static double getAverage(HashMap <String, Integer> user, HashMap <String, Double> menu) {
		double total = 0;
		int numValues = 0;
		double average;
		double price = 0;
		int quant = 0;
		
		for (String item: user.keySet()) {
			for (String menuItem: menu.keySet()) {
				if (item.contentEquals(menuItem)) {
					price = menu.get(menuItem);
					quant = user.get(item);
					numValues += quant;
					System.out.print(quant + " " + numValues); //test
				}
			}
			total += price * quant;
			System.out.print(total); //test
		}
		average = total / numValues;
		System.out.print(average); //test
		return average;
	}
	
	//take user order
	public static HashMap<String, Integer> setUserItems(HashMap<String, Double> menu,
									HashMap<String, Integer> userItems, Scanner scan) {
		String item = "";
		boolean con = true;
		int q = 0;
		double price = 0;
		String formatPrice;
		DecimalFormat decimalFormat = new DecimalFormat("###.00");
		TreeMap<String, Integer> menuTree = new TreeMap<>();
		menuTree = setReferenceMap(menu);
		
		//calculate what menue item
		while(con) {
			try {
				item = scan.nextLine();
				String temp = "";
				for (String testItem: menuTree.keySet()) {
					if (testItem.contains(item.toLowerCase())) {
						if (item.toLowerCase().contains("beer") &&
								!item.contains("12") && !item.contains("6")) {
							System.out.print("Would you like the 6 or 12 pack?: ");
							while (true) {
								temp = scan.nextLine().toLowerCase();
								if (temp.contains("6")) {
									item = "beer (6pk)";
									con = false;
									break;
								} else if (temp.contains("12")) {
									item = "beer (12pk)";
									con = false;
									break;
								} else {
									System.out.print("\nPlease enter 6 or 12: ");
								}
							}
						} else {
							item = testItem;
							con = false;
						}
					} else if (isInt(item) && Integer.parseInt(item) < 21) {
						for (String testTwo: menuTree.keySet()) {
							int t = menuTree.get(testTwo);
							if (t == Integer.parseInt(item)) {
								item = testTwo;
								con = false;
								break;
							}
							
						}
					}
				}
				if (con) {
					System.out.print("\nSorry, we don't have those. Please try again: ");
				}
			} catch (Exception e) {
				System.out.print("\nSomething went wrong. Please try again: ");
				e.printStackTrace();  //test
				con = true;
			}
			
		}
		for (String testItem: menu.keySet()) {
			if (item.equals(testItem)) {
				price = menu.get(testItem);
				break;
			}
		}
		formatPrice = decimalFormat.format(price);
		System.out.println("Adding " + item + " to your cart at $" + formatPrice + " each."); //test
		q = BaseMethods.setQuantity(menu, item, scan);
		
		for (String userItem: userItems.keySet()) {
			if (item.contentEquals(userItem)) {
				int tq = userItems.get(userItem);
				userItems.put(userItem, tq + q);
			} else {
				userItems.put(userItem, q);
			}
		}
		
		return userItems;
	}
	
	public static int setQuantity(HashMap<String, Double> menu, String itemName, Scanner scan) {
		int q;
		String temp = "";
		System.out.print("How many would you like?: ");
		while (true) {
			temp = scan.nextLine();
			if (BaseMethods.isInt(temp)) {
				q = Integer.parseInt(temp);
				if (q <= 30) {
					return q;
				} else {
					System.out.print("Each person may buy up to 30. Please enter quantity again: ");
				}
			} else {
				System.out.print("That is not a valid quantity. Please try again: ");
			}
		}
	}
	
	//This is to help with referencing menu items
	public static TreeMap<String, Integer> setReferenceMap(HashMap<String, Double> map) {
		int i = 1;
		TreeMap<String, Integer> newMap = new TreeMap<>();
		
		for (String item: map.keySet()) {
			newMap.put(item, 0);
		}
		for (String item: newMap.keySet()) {
			newMap.put(item, i);
			i++;
		}
		return newMap;
	}
	
	//Check if the answer is yes or no
	public static boolean isYesNo(String ans, Scanner scan) {
		boolean trueFalse;
		try {
			if (ans.toLowerCase().startsWith("y")) {
				trueFalse = true;
			} else if (ans.toLowerCase().startsWith("n")) {
				trueFalse =  false;
			} else {
				System.out.print("\nThat answer does not work. Please try again. (y/n): "); 
				trueFalse = BaseMethods.isYesNo(scan.nextLine(), scan);
			}
			return trueFalse;
		} catch (Exception e) {
			System.out.print("\nSomething weird happened. Please try again. (y/n): ");
				return BaseMethods.isYesNo(scan.nextLine(), scan);
		}
	}
	
	//Check if it's an integer
	public static boolean isInt (String string) {
		int i = 0;
		try {
			i = Integer.parseInt(string);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}
	
	//Display the menu
	public static void showMenu(HashMap<String, Double> menu) {
		String display;
		String format;
		int i = 0;
		DecimalFormat decimalFormat = new DecimalFormat("##.00");
		TreeMap<String, Double> sortedMenu = new TreeMap<>();
		sortedMenu.putAll(menu);
		
		//Note: this is called "padding" a string
		display = " " + String.format("%-40s", "_").replace(" ", "_");
		display += String.format("\n|%-28s", "    Item") + String.format("%-12s|", "Price");
		display += String.format("\n|%-40s|", "=").replace(" ", "=");
		for (String menuItem: sortedMenu.keySet()) {
			i++;
			format = decimalFormat.format(sortedMenu.get(menuItem));
			if (i < 10) {
				display += String.format("\n|%d. %-25s", i, " " + menuItem);
				if (sortedMenu.get(menuItem) < 1) {
					display += String.format("$  %-9s|", format);
				} else if (sortedMenu.get(menuItem) < 10) {
					display += String.format("$ %-10s|", format);
				} else {
					display += String.format("$%-11s|", format);
				}
			} else {
				display += String.format("\n|%d. %-24s", i, menuItem);
				if (sortedMenu.get(menuItem) < 1) {
					display += String.format("$  %-9s|", format);
				} else if (sortedMenu.get(menuItem) < 10) {
					display += String.format("$ %-10s|", format);
				} else {
					display += String.format("$%-11s|", format);
				}
			}
			
		}
		display += String.format("\n|%-40s|", "_").replace(" ", "_");
		
		System.out.println("\n" + display); //show result
		
	}

}
