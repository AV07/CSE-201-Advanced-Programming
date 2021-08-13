
import java.util.Scanner;
import java.util.Set;
import javafx.util.Pair;
import java.util.ArrayList;
import java.util.HashMap;

class company{
	int accBalance;
	company(){
		this.accBalance = 0;
	}
	company(int n){
		this.accBalance = n;
	}
}

interface printables{
	void menu1();
	int rewardsWon();
}

class Item{
	private final String code;
	private final int numCode;
	private final String Name;
	private int price;
	int quantity;
	private final String category;
	private String offer;
	Item(String Name, int price, int quantity, String category, String code, int numCode){
		this.Name = Name;
		this.category = category;
		this.price = price;
		this.quantity = quantity;
		this.offer = "None";
		this.code = code;
		this.numCode = numCode;
	}
	String getCode() {
		return this.code;
	}
	int getnumCode() {
		return this.numCode;
	}
	String getName() {
		return this.Name;
	}
	int getPrice() {
		return this.price;
	}
	void setPrice(int n) {
		this.price = n;
	}
	int getQuantity() {
		return this.quantity;
	}
	String getCateg() {
		return this.category;
	}
	String getOffer() {
		return this.offer;
	}
	void setOffer(String a) {
		this.offer = a;
	}
}

class Merchant implements printables {
	private final String Name;
	private final int userID;
	int totalContribution;
	int slots;
	HashMap<Integer, Item> itemsCode = new HashMap<>();
	HashMap<String, ArrayList<Item>> categories = new HashMap<>();
	public void menu1() {
		System.out.println("Merchant Menu");
		System.out.println("1) Add item");
		System.out.println("2) Edit item");
		System.out.println("3) Search by category");
		System.out.println("4) Add offer");
		System.out.println("5) Rewards won");
		System.out.println("6) Exit");
	}
	public int rewardsWon() {
		return 0;
	}
	Merchant(String Name, int userID){
		this.Name = Name;
		this.userID = userID;
		this.totalContribution = 0;
	}
	String getName() {
		return this.Name;
	}
	int getID() {
		return this.userID;
	}
}

class Customer implements printables {
	private final String Name;
	private final int userID;
	int countOrders;
	int mainAcc;
	int rewardsAcc;
	ArrayList<Pair<Integer, Item>> cart = new ArrayList<>();
	ArrayList<Pair<Merchant, Item>> latestOrder = new ArrayList<>();
	ArrayList<Integer> quantity = new ArrayList<>();
	public void menu1() {
		System.out.println("Customer Menu");
		System.out.println("1) Search item");
		System.out.println("2) checkout cart");
		System.out.println("3) Reward won");
		System.out.println("4) print latest orders");
		System.out.println("5) Exit");
	}
	public int rewardsWon() {
		return 0;
	}
	Customer(String Name, int userID){
		this.Name = Name;
		this.userID = userID;
		this.countOrders = 0;
		this.mainAcc = 100;
		this.rewardsAcc = 0;
	}
	String getName() {
		return this.Name;
	}
	int getID() {
		return this.userID;
	}
}

public class Assignment02 {

	public static void main(String[] args) {
		
		company Mercury = new company();
		
		ArrayList<Merchant> merchants = new ArrayList<>();
		merchants.add(new Merchant("jack", 1));
		merchants.add(new Merchant("john", 2));
		merchants.add(new Merchant("james", 3));
		merchants.add(new Merchant("jeff", 4));
		merchants.add(new Merchant("joseph", 5));
		
		ArrayList<Customer> customers = new ArrayList<>();
		customers.add(new Customer("ali", 1));
		customers.add(new Customer("nobby", 2));
		customers.add(new Customer("bruno", 3));
		customers.add(new Customer("borat", 4));
		customers.add(new Customer("aladeen", 5));
		
		Scanner s = new Scanner(System.in);
		boolean letsgo = true;
		boolean merchant = false;
		Merchant currM = new Merchant("", 0);
		boolean customer = false;
		Customer currC = new Customer("", 0);
		while(letsgo) {
			System.out.println("Welcome to Mercury");
			System.out.println("1) Enter as Merchant");
			System.out.println("2) Enter as Customer");
			System.out.println("3) See user details");
			System.out.println("4) Company account balance");
			System.out.println("5) Exit");
			int a = s.nextInt();
			int count = 0;
			if(a == 1) {
				merchant = true;
				customer = false;
				System.out.println("choose merchant");
				for(int i = 0; i < merchants.size(); i++) {
					System.out.println(merchants.get(i).getID() + " " + merchants.get(i).getName());
				}
				int b = s.nextInt();
				String cName = merchants.get(b-1).getName();
				currM = merchants.get(b-1);
				boolean letsgo1 = true;
				while(letsgo1) {
					System.out.println("Welcome " + cName);
					currM.menu1();
					
					int c = s.nextInt();
					if(c == 1) {
						System.out.println("Enter item details");
						System.out.println("item name:");
						String Name = s.next();
						System.out.println("item price:");
						int price = s.nextInt();
						System.out.println("item quantity:");
						int quantity = s.nextInt();
						System.out.println("item category:");
						String category = s.next();
						count += 1;
						String codeName = Integer.toString(count) + merchants.get(b-1).getName();
						Item item = new Item(Name, price, quantity, category, codeName, count);
						
						if(!merchants.get(b-1).itemsCode.containsKey(count)) {
							merchants.get(b-1).itemsCode.put(count, item);
						}
						if(merchants.get(b-1).categories.containsKey(category)) {
							merchants.get(b-1).categories.get(category).add(item);
						}else {
							ArrayList<Item> al = new ArrayList<>();
							al.add(item);
							merchants.get(b-1).categories.put(category, al);
						}
						System.out.println(count + " " + Name + " " + price + " " + quantity + " " + "None" + " " + category);
						
					}else if(c == 2) {
						System.out.println("choose item by code");
						int z = 1;
						Item temp;
						while(merchants.get(b-1).itemsCode.containsKey(z)) {
							temp = merchants.get(b-1).itemsCode.get(z);
							System.out.println(z + " " + temp.getName() + " " + temp.getPrice() + " " + temp.quantity + " " + temp.getOffer() + " " + temp.getCateg());
							z += 1;
						}
						int itemCode = s.nextInt();
						if(merchants.get(b-1).itemsCode.containsKey(itemCode)) {
							System.out.println("Enter edit details");
							System.out.println("item price");
							int price = s.nextInt();
							System.out.println("item quantity");
							int quantity = s.nextInt();
						
							merchants.get(b-1).itemsCode.get(itemCode).setPrice(price);
							merchants.get(b-1).itemsCode.get(itemCode).quantity = quantity;
							String categ = merchants.get(b-1).itemsCode.get(itemCode).getCateg();
						
							ArrayList<Item> temp2 = merchants.get(b-1).categories.get(categ);
							int i = 0;
							for(i = 0; i < temp2.size(); i++) {
								if(temp2.get(i).getnumCode() == itemCode) {
									break;
								}
							}
							merchants.get(b-1).categories.get(categ).get(i).setPrice(price);
							merchants.get(b-1).categories.get(categ).get(i).quantity = quantity;
							temp = merchants.get(b-1).categories.get(categ).get(i);
							System.out.println(temp.getnumCode() + " " + temp.getName() + " " + temp.getPrice() + " " + temp.quantity + " " + temp.getOffer() + " " + temp.getCateg());
						}
						
					}else if(c == 3) {
						System.out.println("Choose a category");
						ArrayList<Integer> iNt = new ArrayList<>();
						ArrayList<String> stRing = new ArrayList<>();
						Set<String> set = merchants.get(b-1).categories.keySet();
						int f = 1;
						for (String i : set) {
				            System.out.println(f + ") " + i);
				            iNt.add(f);
				            stRing.add(i);
				            f += 1;
						}
						
						int input = s.nextInt();
						for(int i = 0; i<iNt.size(); i++) {
							if(iNt.get(i) == input) {
								f = i;
								break;
							}
						}
						String categ = stRing.get(f);
						for(int i = 0; i < merchants.size(); i++) {
							if(merchants.get(i).categories.containsKey(categ)) {
								ArrayList<Item> temp1 = merchants.get(i).categories.get(categ);
								System.out.println(merchants.get(i).getName() + "'s items in category " + categ);
								for(int j = 0; j<temp1.size(); j++) {
									System.out.println(temp1.get(j).getnumCode() + " " + temp1.get(j).getName() + " " + temp1.get(j).getPrice() + " " + temp1.get(j).quantity + " " + temp1.get(j).getOffer() + " " + temp1.get(j).getCateg());
								}
								System.out.println();
							}
						}
					}else if(c == 4) {
						System.out.println("choose item by code");
						
						for(int i = 0; i < merchants.get(b-1).itemsCode.size(); i++) {
							Item temp = merchants.get(b-1).itemsCode.get(i+1);
							System.out.println(temp.getnumCode() + " " + temp.getName() + " " + temp.getPrice() + " " + temp.quantity + " " + temp.getOffer() + " " + temp.getCateg());
						}
						
						int itemCode = s.nextInt();
						String categ = merchants.get(b-1).itemsCode.get(itemCode).getCateg();
						System.out.println("choose offer");
						System.out.println("1) buy one get one free");
						System.out.println("2) 25% off");
						int offer = s.nextInt();
						if(offer == 1) {
							merchants.get(b-1).itemsCode.get(itemCode).getOffer().equals("buy one get one free");
							for(int i = 0; i < merchants.get(b-1).categories.get(categ).size(); i++) {
								if(merchants.get(b-1).categories.get(categ).get(i).getnumCode() == itemCode) {
									merchants.get(b-1).categories.get(categ).get(i).setOffer("buy one get one free");
								}
							}
						}else if(offer == 2) {
							merchants.get(b-1).itemsCode.get(itemCode).setOffer("25% off");
							for(int i = 0; i < merchants.get(b-1).categories.get(categ).size(); i++) {
								if(merchants.get(b-1).categories.get(categ).get(i).getnumCode() == itemCode) {
									merchants.get(b-1).categories.get(categ).get(i).setOffer("25% off");
								}
							}
						}
						Item temp = merchants.get(b-1).itemsCode.get(itemCode);
						System.out.println(temp.getnumCode() + " " + temp.getName() + " " + temp.getPrice() + " " + temp.quantity + " " + temp.getOffer() + " " + temp.getCateg());
					}else if(c == 5) {
						
					}else if(c == 6) {
						letsgo1 = false;
					}
				}
			}else if(a == 2) {
				customer = true;
				merchant = false;
				System.out.println("choose customer");
				for(int i = 0; i < customers.size(); i++) {
					System.out.println(customers.get(i).getID() + " " + customers.get(i).getName());
				}
				int b = s.nextInt();
				String cName = customers.get(b-1).getName();
				currC = customers.get(b-1);
				boolean letsgo2 = true;
				while(letsgo2) {
					System.out.println("Welcome " + cName);
					currC.menu1();
					int p = s.nextInt();
					
					if(p == 1) {
						System.out.println("Choose a category");
						ArrayList<Integer> iNt = new ArrayList<>();
						ArrayList<String> stRing = new ArrayList<>();
						Set<String> set = merchants.get(b-1).categories.keySet();
						int f = 1;
						for (String i : set) {
				            System.out.println(f + ") " + i);
				            iNt.add(f);
				            stRing.add(i);
				            f += 1;
						}
						
						int input = s.nextInt();
						for(int i = 0; i<iNt.size(); i++) {
							if(iNt.get(i) == input) {
								f = i;
								break;
							}
						}
						String categ = stRing.get(f);
						System.out.println("choose item by code");
						for(int i = 0; i < merchants.size(); i++) {
							if(merchants.get(i).categories.containsKey(categ)) {
								ArrayList<Item> temp1 = merchants.get(i).categories.get(categ);
								for(int j = 0; j < temp1.size(); j++) {
									System.out.println(temp1.get(j).getCode() + " " + temp1.get(j).getName() + " " + temp1.get(j).getPrice() + " " + temp1.get(j).quantity + " " + temp1.get(j).getOffer() + " " + temp1.get(j).getCateg());
								}
							}
						}
						System.out.println();
						System.out.println("Enter item code");
						String code = s.next();
						System.out.println("Enter item quantity");
						int quantity = s.nextInt();
						String merch = code.substring(1);
						int numC = Integer.parseInt(code.substring(0,1));
						int merchIndex = 0;
						for(int i = 0; i<merchants.size(); i++) {
							if(merchants.get(i).getName().equals(merch)) {
								merchIndex = i;
							}
						}
						Item curr = new Item("", 0, 0, "", "", 0);
						for(int i = 0; i < merchants.get(merchIndex).categories.get(categ).size(); i++) {
							if(merchants.get(merchIndex).categories.get(categ).get(i).getCode().equals(numC)) {
								curr = merchants.get(merchIndex).categories.get(categ).get(i);
								break;
							}
						}
						System.out.println("Choose method of transacion");
						System.out.println("1) Buy item");
						System.out.println("2) Add item to cart");
						System.out.println("3) Exit");
						
						int in = s.nextInt();
						
						if(in == 1) {
							// Buy
							if(curr.quantity>0) {
								currC.countOrders += 1;
//								String offer = currC.cart.get(i).getValue().getOffer();
//								int o = 1;
//								if(offer.equals("None") || currC.cart.get(i).getValue().quantity == 1) {
//									o = 1;
//								}else if(offer.equals("25% off")) {
//									o = 4;
//								}else if(offer.equals("buy one get one free")) {
//									o = 2;
//								}
								if(curr.getOffer().equals("buy one get one free") && quantity*curr.getPrice() <= (currC.mainAcc+currC.rewardsAcc)) {
									curr.quantity -= (quantity*2);
									currC.mainAcc -= quantity*curr.getPrice();
									System.out.println("Item successfully bought");
									currC.quantity.add(quantity);
									Pair<Merchant, Item> newP = new Pair<>(merchants.get(merchIndex), curr);
									currC.latestOrder.add(newP);
								}else {
									
									if(quantity*curr.getPrice() <= (currC.mainAcc + currC.rewardsAcc)) {
										curr.quantity -= quantity;
										if(curr.getOffer().equals("25% off")) {
											currC.mainAcc -= quantity*curr.getPrice()/4;
										}
										if(curr.getOffer().equals("None")) {
											currC.mainAcc -= quantity*curr.getPrice();
										}
										System.out.println("Item successfully bought");
										currC.quantity.add(quantity);
										Pair<Merchant, Item> newP = new Pair<>(merchants.get(merchIndex), curr);
										currC.latestOrder.add(newP);
									}else {
										System.out.println("Out of money");
									}
								}
							}else {
								System.out.println("Out of stock");
							}
						}else if(in == 2) {
							// Add to cart
							Pair<Integer, Item> newP = new Pair<Integer, Item>(quantity, curr);
							currC.cart.add(newP);
							System.out.println("Item added to cart");
						}else if(in == 3) {
							break;
						}
					}else if(p == 2) {
						// checkout cart
						int size = currC.cart.size()-1;
						for(int i = size; i>=0; i--) {
							String offer = currC.cart.get(i).getValue().getOffer();
							int o = 1;
							if(offer.equals("None") || currC.cart.get(i).getValue().quantity == 1) {
								o = 1;
							}else if(offer.equals("25% off")) {
								o = 4;
							}else if(offer.equals("buy one get one free")) {
								o = 2;
							}
							if((currC.mainAcc + currC.rewardsAcc)/o >= currC.cart.get(i).getKey()*currC.cart.get(i).getValue().getPrice()) {
								currC.cart.remove(i);
								currC.mainAcc -= currC.cart.get(i).getKey()*currC.cart.get(i).getValue().getPrice()/o;
							}
						}
					}else if(p == 3) {
					}else if(p == 4) {
						for(int i = 0; i<currC.latestOrder.size(); i++) {
							Pair<Merchant, Item> pair = currC.latestOrder.get(i);
							System.out.println("Bought item " + pair.getValue().getName() + " quantity: " + currC.quantity.get(i) + " for Rs " + pair.getValue().getPrice() + " from Merchant " + pair.getKey().getName());
						}
					}else if(p == 5) {
						letsgo2 = false;
					}
				}
			}else if(a == 3) {
				// See user details
				if(merchant) {
					System.out.println(currM.getName() + " " + currM.getID() + " " + currM.totalContribution);
				}
				if(customer) {
					System.out.println(currC.getName() + " " + currC.getID() + " " + currC.countOrders);
				}
			}else if(a == 4) {
				// Company account balance
				System.out.println(Mercury.accBalance);
			}else if(a == 5) {
				System.out.println("_______END_______");
				letsgo = false;
			}
		}
	}
}
