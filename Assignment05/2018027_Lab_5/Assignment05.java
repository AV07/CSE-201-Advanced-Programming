import java.util.ArrayList;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Random;
import java.util.Scanner;

class enterCHECK {
	protected static void enterCont() {
		Scanner s = new Scanner(System.in);
		// pauses the program and waits for the user to press enter
		System.out.println(">>Hit ENTER to start the game");
		s.nextLine();
		s.close();
	}
}

class Player{
	private final String name;
	private int position;
	private int rollNum;
	private int snakeBites;
	private int vultureBites;
	private int cricketBites;
	private int trampolineUsed;
	
	Player(String s){
		this.name = s;
		this.setPosition(0);
		this.setRollNum(1);
		this.snakeBites = 0;
		this.vultureBites = 0;
		this.cricketBites = 0;
		this.trampolineUsed = 0;
	}
	public String getName() {
		return name;
	}
	public int getPosition() {
		return position;
	}
	public void setPosition(int position) {
		this.position = position;
	}
	public int getRollNum() {
		return rollNum;
	}
	public void setRollNum(int rollNum) {
		this.rollNum = rollNum;
	}
	public int getSnakeBites() {
		return snakeBites;
	}
	public void setSnakeBites(int snakeBites) {
		this.snakeBites = snakeBites;
	}
	public int getVultureBites() {
		return vultureBites;
	}
	public void setVultureBites(int vultureBites) {
		this.vultureBites = vultureBites;
	}
	public int getCricketBites() {
		return cricketBites;
	}
	public void setCricketBites(int cricketBites) {
		this.cricketBites = cricketBites;
	}
	public int getTrampolineUsed() {
		return trampolineUsed;
	}
	public void setTrampolineUsed(int trampolineUsed) {
		this.trampolineUsed = trampolineUsed;
	}
}

@SuppressWarnings("serial")
class myException extends Exception{
}

@SuppressWarnings("serial")
abstract class Tiles extends myException{
	private final String type;
	private int steps;
	Tiles(String s) {
		this.type = s;
		this.setSteps(0);
	}
	abstract protected void shakeTile(Player p);
	protected void exception() {
		try {
			throw new myException();
		}catch(myException e){
			System.out.println(this.type + "BiteException");
		}
	}
	public String getType() {
		return type;
	}
	public int getSteps() {
		return steps;
	}
	public void setSteps(int steps) {
		this.steps = steps;
	}
}

@SuppressWarnings("serial")
class Snake extends Tiles{
	protected Snake(){
		super("Snake");
	}
	@Override
	protected void shakeTile(Player p) {
		System.out.print(">>          Hiss...! I am a Snake, you go back " + this.getSteps() + " tiles. ");
		exception();
		p.setSnakeBites(p.getSnakeBites() + 1);
	}
}

@SuppressWarnings("serial")
class Vulture extends Tiles{
	protected Vulture(){
		super("Vulture");
	}
	@Override
	protected void shakeTile(Player p) {
		System.out.print(">>          Yapping...! I am a Vulture, you go back " + this.getSteps() + " tiles. ");
		exception();
		p.setVultureBites(p.getVultureBites() + 1);
	}
}

@SuppressWarnings("serial")
class Cricket extends Tiles{
	protected Cricket(){
		super("Cricket");
	}
	@Override
	protected void shakeTile(Player p) {
		System.out.print(">>          Chirp...! I am a Cricket, you go back " + this.getSteps() + " tiles. ");
		exception();
		p.setCricketBites(p.getCricketBites()+1);
	}
}

@SuppressWarnings("serial")
class White extends Tiles{
	protected White(){
		super("White");
	}
	@Override
	protected void shakeTile(Player p) {
		System.out.println(">>          I am a White Tile!");
		System.out.println(">>          " + p.getName() + " moved to Tile-" + (p.getPosition() + 1));
	}
}

@SuppressWarnings("serial")
class Trampoline extends Tiles{
	protected Trampoline(){
		super("Trampoline");
	}
	@Override
	protected void shakeTile(Player p) {
		System.out.print(">>          Trampoline...! I am a Cricket, you go back " + this.getSteps() + " tiles. ");
		try {
			throw new myException();
		}catch(myException e){
			System.out.println("TrampolineException");
		}
		p.setTrampolineUsed(p.getTrampolineUsed()+1);
	}
}

public class Assignment05 {
	
	protected static final HashMap<Integer, String> tiles = new HashMap<>();
	protected static final HashMap<String, Integer> tiles1 = new HashMap<>();
	protected static final HashMap<Integer, Integer> count = new HashMap<>();
	protected static final HashMap<Integer, Integer> stepsAffected = new HashMap<>();
	protected static final ArrayList<Tiles> track = new ArrayList<>();
	
	protected static void initialize(Random r) {
		tiles.put(1, "Snake");
		tiles.put(2, "Vulture");
		tiles.put(3, "Cricket");
		tiles.put(4, "Trampoline");
		tiles1.put("Snake", 1);
		tiles1.put("Vulture", 2);
		tiles1.put("Cricket", 3);
		tiles1.put("Trampoline", 4);
		count.put(1, 0);
		count.put(2, 0);
		count.put(3, 0);
		count.put(4, 0);
		int a;
		for(int i = 1; i <= 4; i++) {
			a = 0;
			while(a < 1) {
				a = r.nextInt(11);
			}
			stepsAffected.put(i, a);
		}
	}
	
	protected static void initializeTrack(Random r, int n) {
		for(int i = 0; i < n; i++) {
			Tiles w = new White();
			track.add(w);
		}
		int index;
		int typeInt;
		Tiles t;
		for(int i = 1; i <= n/2; i++) {
			typeInt = 1 + r.nextInt(4);
			count.put(typeInt, count.get(typeInt) + 1); 
			index = 0;
			while(index < 1) {
				index = r.nextInt(n);
			}
			
			while(!track.get(index).getType().equals("White")) {
				index = r.nextInt(n);
			}
			track.remove(index);
			if(typeInt == 1) {
				t = new Snake();
				t.setSteps(stepsAffected.get(typeInt));
				track.add(index, t);
			}else if(typeInt == 2) {
				t = new Vulture();
				t.setSteps(stepsAffected.get(typeInt));
				track.add(index, t);
			}else if(typeInt == 3) {
				t = new Cricket();
				t.setSteps(stepsAffected.get(typeInt));
				track.add(index, t);
			}else if(typeInt == 4) {
				t = new Trampoline();
				t.setSteps(stepsAffected.get(typeInt));
				track.add(index, t);
			}
		}
	}
	
	protected static void startGame(Random r, Player p) {
		while(p.getPosition() == 0) {
			int dice = 0;
			while(dice < 1) {
				dice = r.nextInt(7); 
			}
			System.out.print(">>[Roll-" + p.getRollNum() + "]: " + p.getName() + " rolled " + dice + " at Tile-" + (p.getPosition() + 1) + ", ");
			if(dice == 6) {
				System.out.println("You are out of the cage! You get a free roll");
				p.setPosition(p.getPosition() + 1);
			}else {
				System.out.println("OOPs you need 6 to start");
			}
			p.setRollNum(p.getRollNum() + 1);
		}
		p.setPosition(0);
	}
	
	protected static void stepBack(int num, Player p) {
		p.setPosition(p.getPosition() - stepsAffected.get(num));
		if(p.getPosition() <= 0) {
			p.setPosition(0);
			System.out.println(">>          " + p.getName() + " moved to Tile-1 as it can't go back further");
		}else {
			System.out.println(">>          " + p.getName() + " moved to Tile-" + (p.getPosition() + 1));
		}
	}
	
	public static void main(String args[]) {
		Scanner s = new Scanner(System.in);
		
		System.out.println(">>Enter total number of tiles on the race track (length)");
		int n = 0;
		while(n == 0) {
			try {
				n = s.nextInt(); // number of tiles
			}catch(InputMismatchException e){
				s.next();
				System.out.println("InputMisMatchException");
			}
		}
		Random r = new Random();
		initialize(r);
		System.out.println(">>Setting up the race track...");
		initializeTrack(r, n);
		
//		for(int i = 0; i<n; i++) {
//			System.out.println(track.get(i).getType());
//		}
		
		System.out.println(">>Danger: There are " + count.get(1) + ", " + count.get(3) + ", " + count.get(2) + " numbers of Snakes, Cricket, Vultures respectively on the track!");
		System.out.println(">>Danger: Each Snake, Cricket and Vultures can throw you back by " + stepsAffected.get(1) + ", " + stepsAffected.get(3) + ", " + stepsAffected.get(2) + " number of Tiles respectively!");
		System.out.println(">>Good News: There are " + count.get(4) + " number of Trampolines on your track!");
		System.out.println(">>Good News: Each Trampoline can help you advance by " + stepsAffected.get(4) + " number of Tiles");
		
		System.out.println(">>Enter the Player Name");
		
		String name = s.next();
		Player p = new Player(name);
		System.out.println(">>Starting the game with " + p.getName() + " at Tile-1");
		System.out.println(">>Control transferred to Computer for rolling the Dice for " + p.getName());
		
		enterCHECK.enterCont();
		s.close();
		System.out.println(">>Game Started ==================>");
		startGame(r, p);
		boolean cont = true;
		int dice;
		while(cont) {
			dice = 0;
			while(dice < 1) {
				dice = r.nextInt(7); 
			}
			if(p.getPosition() + dice > (n-1)) {
				System.out.println(">>[Roll-" + p.getRollNum() + "]: " + p.getName() + " rolled " + dice + " at Tile-" + (p.getPosition()+1) + ", landed on Tile-" + (p.getPosition()+1));
			}else if(p.getPosition() + dice == (n-1)){
				System.out.println(">>[Roll-" + p.getRollNum() + "]: " + p.getName() + " rolled " + dice + " at Tile-" + (p.getPosition()+1) + ", landed on Tile-" + (p.getPosition()+1 + dice));
				System.out.print(">>          " + p.getName() + " wins the race in " + p.getRollNum() + " rolls. ");
				try {
					throw new myException();
				}catch(myException ex) {
					System.out.println("GameWonException");
				}
				System.out.println(">>          Total Snake Bites = " + p.getSnakeBites());
				System.out.println(">>          Total Vulture Bites = " + p.getVultureBites());
				System.out.println(">>          Total Cricket Bites = " + p.getCricketBites());
				System.out.println(">>          Total Trampolines Used = " + p.getTrampolineUsed());
				cont = false;
			}else {
				System.out.println(">>[Roll-" + p.getRollNum() + "]: " + name + " rolled " + dice + " at Tile-" + (p.getPosition() + 1) + ", landed on Tile-" + (p.getPosition() + 1 + dice));
				p.setPosition(p.getPosition() + dice);
				System.out.println(">>          Trying to shake Tile-" + (p.getPosition()+1));
				
				track.get(p.getPosition()).shakeTile(p);
				if(!track.get(p.getPosition()).getType().equals("White")) {
					stepBack(tiles1.get(track.get(p.getPosition()).getType()), p);
				}
				
				if(p.getPosition() == 0) {
					startGame(r, p);
				}
			}
			p.setRollNum(p.getRollNum() + 1);
		}
	}
}