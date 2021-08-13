
import java.util.Random;
import java.util.Scanner;
import java.io.*;

public class Assignment06 {
	
	public static Player p;
	public static boolean myTest1Called = false;
	public static boolean myTest2Called = false;
	
	public static void initialize(Random r) {
		p.tiles.put(1, "Snake");
		p.tiles.put(2, "Vulture");
		p.tiles.put(3, "Cricket");
		p.tiles.put(4, "Trampoline");
		p.tiles1.put("Snake", 1);
		p.tiles1.put("Vulture", 2);
		p.tiles1.put("Cricket", 3);
		p.tiles1.put("Trampoline", 4);
		for(int i = 1; i<5; i++) {
			p.count.put(i, 0);
		}
		int a;
		for(int i = 1; i <= 4; i++) {
			a = 0;
			while(a < 1) {
				a = r.nextInt(11);
			}
			p.stepsAffected.put(i, a);
		}
	}
	
	public static void initializeTrack(Random r, int n) {
		for(int i = 0; i < n; i++) {
			Tiles w = new White();
			p.track.add(w);
		}
		int index;
		int typeInt;
		Tiles t;
		for(int i = 1; i <= n/4; i++) {
			typeInt = 1 + r.nextInt(4);
			p.count.put(typeInt, p.count.get(typeInt) + 1);
			index = 0;
			boolean a = true;
			while(a) {
				index = r.nextInt(n);
				if(index > 10 && p.track.get(index).getType().equals("White")) {
					a = false;
				}
			}
			p.track.remove(index);
			if(typeInt == 1) {
				t = new Snake();
				t.setSteps(p.stepsAffected.get(typeInt));
				p.track.add(index, t);
			}else if(typeInt == 2) {
				t = new Vulture();
				t.setSteps(p.stepsAffected.get(typeInt));
				p.track.add(index, t);
			}else if(typeInt == 3) {
				t = new Cricket();
				t.setSteps(p.stepsAffected.get(typeInt));
				p.track.add(index, t);
			}else if(typeInt == 4) {
				t = new Trampoline();
				t.setSteps(p.stepsAffected.get(typeInt));
				p.track.add(index, t);
			}
		}
	}
	
	public static void startGame(Random r, Player p) {
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
	
	public static void stepBack(int num, Player p) {
		p.setPosition(p.getPosition() - p.stepsAffected.get(num));
		if(p.getPosition() == 0) {
			p.setPosition(0);
			System.out.println(">>          " + p.getName() + " moved to Tile-1");
		}else if(p.getPosition() < 0) {
			p.setPosition(0);
			System.out.println(">>          " + p.getName() + " moved to Tile-1 as it can't go back further");
		}
		else {
			System.out.println(">>          " + p.getName() + " moved to Tile-" + (p.getPosition() + 1));
		}
	}
	
	public static Player gameBegins(Player p, Random r, int n, Scanner s) throws GameWonException {
		System.out.println(">>Starting the game with " + p.getName() + " at Tile-" + (p.getPosition()+1));
		System.out.println(">>Control transferred to Computer for rolling the Dice for " + p.getName());
		int a25 = (int)(n*0.25);
		int a50 = (int)(n*0.5);
		int a75 = (int)(n*0.75);
		
//		try {
//			Thread.sleep(2000);
//		}
//		catch(Exception e) {
//			
//		}
		if(!myTest2Called && !myTest1Called) {
			enterCHECK.enterCont();
		}
		
		System.out.println();
		System.out.println("|--------------------------------|");
		System.out.println("|          GAME STARTED          |");
		System.out.println("|--------------------------------|");
		System.out.println();
		if(p.getPosition() == 0) {
			startGame(r, p);
		}
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
				System.out.println();
				System.out.println("|----------------------------|");
				System.out.println("|          GAME WON          |");
				System.out.println("|----------------------------|");
				System.out.println();
				System.out.println(">>          " + p.getName() + " wins the race in " + p.getRollNum() + " rolls. ");
				System.out.println(">>          Total Snake Bites = " + p.getSnakeBites());
				System.out.println(">>          Total Vulture Bites = " + p.getVultureBites());
				System.out.println(">>          Total Cricket Bites = " + p.getCricketBites());
				System.out.println(">>          Total Trampolines Used = " + p.getTrampolineUsed());
				
				if(myTest2Called) {
					throw new GameWonException();
				}else {
					try {
						throw new GameWonException();
					}catch(GameWonException e) {
						
					}
				}
				File f = new File(p.getName() + ".txt");
				if(f.exists()) {
					f.delete();
				}
				cont = false;
			}else {
				System.out.println(">>[Roll-" + p.getRollNum() + "]: " + p.getName() + " rolled " + dice + " at Tile-" + (p.getPosition() + 1) + ", landed on Tile-" + (p.getPosition() + 1 + dice));
				p.setPosition(p.getPosition() + dice);
				System.out.println(">>          Trying to shake Tile-" + (p.getPosition()+1));
				
				try{
					p.track.get(p.getPosition()).shakeTile(p);
				}
				catch(VultureBiteException e) {				
				}
				catch(SnakeBiteException e) {
				}
				catch(CricketBiteException e) {
				}
				catch(TrampolineException e) {
				}
				if(!p.track.get(p.getPosition()).getType().equals("White")) {
					stepBack(p.tiles1.get(p.track.get(p.getPosition()).getType()), p);
				}
				if(p.getPosition() == 0) {
					startGame(r, p);
				}
			}
			p.setRollNum(p.getRollNum() + 1);
			int pos = p.getPosition() + 1;
			if((pos >= a25 && p.getcheckPointsReached() < 1) || (pos >= a50 && p.getcheckPointsReached() < 2) || (pos >= a75 && p.getcheckPointsReached() < 3)) {
				System.out.println();
				System.out.println("|--------------------------------|");
				System.out.println("|           CHECKPOINT           |");
				System.out.println("|--------------------------------|");
				System.out.println();
				p.inccheckPointsReached();
				boolean x = false;
				if(!myTest2Called && !myTest1Called) {
					x = isS.isSaved(s);					
				}
				if(myTest1Called) {
					x = true;
				}
				if(x) {
					p.serialize();
					return p;
				}
				else {
					System.out.println();
					System.out.println("|--------------------------------|");
					System.out.println("|            CONTINUE            |");
					System.out.println("|--------------------------------|");
					System.out.println();
				}
				
			}
		}
		return p;
	}
	
	public static void main(String args[]) throws GameWonException {
		Scanner s = new Scanner(System.in);
		String name;
		System.out.println(">>Enter the Player Name");
		name = s.next();
		Random r = new Random();
		int n;
		
		File f = new File(name + ".txt");
		
		if(f.exists()) {
			System.out.println("Existing User");
			p = new Player(name);
			p = p.deserialize();
			n = p.getN();
		}else {
			System.out.println("New User");
			p = new Player(name);
			System.out.println(">>Enter total number of tiles on the race track (length)");
			n = s.nextInt();
			while(n<25) {
				System.out.println("Enter more number of tiles");
				n = s.nextInt();
			}
			p.setN(n);
			initialize(r);
			System.out.println(">>Setting up the race track...");
			System.out.println();
			initializeTrack(r, n);
			
			System.out.println(">>Danger: There are " + p.count.get(1) + ", " + p.count.get(3) + ", " + p.count.get(2) + " numbers of Snakes, Cricket, Vultures respectively on the track!");
			System.out.println(">>Danger: Each Snake, Cricket and Vultures can throw you back by " + p.stepsAffected.get(1) + ", " + p.stepsAffected.get(3) + ", " + p.stepsAffected.get(2) + " number of Tiles respectively!");
			System.out.println(">>Good News: There are " + p.count.get(4) + " number of Trampolines on your track!");
			System.out.println(">>Good News: Each Trampoline can help you advance by " + p.stepsAffected.get(4) + " number of Tiles");
			System.out.println();
		}
		p = gameBegins(p, r, n, s);
//		s.close();
	}
}
