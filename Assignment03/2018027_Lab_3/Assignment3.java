import java.util.HashMap;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.Random;

class User{
	private final String Name;
	private final int HeroAvatar;
	User(String Name, int HeroAvatar){
		this.Name = Name;
		this.HeroAvatar = HeroAvatar;
	}
	protected String getName(){
		return this.Name;
	}
	protected int getAvatar() {
		return this.HeroAvatar;
	}
}

//////////////////////////////////////////HERO//////////////////////////////////////////
abstract class Hero{
	private double HP;
	private double defensiveHP = 0;
	private int maxHP;
	private int XP;
	private int lvl;
	private int moves;
	private final String type;
	private boolean SpecialAvailableasOption;
	
	Hero(String type){
		this.type = type;
		this.maxHP = 100;
		this.HP = 100;
		this.lvl = 1;
		this.XP = 0;
		this.moves = 0;
		this.SpecialAvailableasOption = false;
	}
	protected void upgrade(int l) {
		this.XP += (l*20);
		this.lvl = (this.XP)/20;
		this.maxHP = 50 + (50*this.lvl);
		this.HP = this.maxHP;
	}
	protected abstract void attack(Monster m);
	protected abstract void defend(Monster m);
	protected abstract void SpecialPower(Monster m);
	
	protected void fight(Monster m, Scanner s) {
		if(!this.SpecialAvailableasOption) {
			this.moves += 1;
		}
		if(this.moves == 4) {
			this.SpecialAvailableasOption = true;
			this.moves = 0;
		}
		System.out.println("Choose move:");
		System.out.println("1) Attack");
		System.out.println("2) Defense");
		if(this.SpecialAvailableasOption) {
			System.out.println("3) Special Attack");
		}
		int in = s.nextInt();
		if(in == 1) {
			this.attack(m);
		}else if(in == 2) {
			this.defend(m);
		}else if(in == 3 && this.SpecialAvailableasOption) {
			this.SpecialAvailableasOption = false;
			this.moves = 0;
		}
	}
	String getType() {
		return this.type;
	}
	double getHP(){
		return this.HP;
	}
	void setHP(double a){
		this.HP = a;
	}
	int getmaxHP() {
		return this.maxHP;
	}
	int getXP() {
		return this.XP;
	}
	void addtoXP(int a) {
		this.XP += a;
	}
	int getLvl() {
		return this.lvl;
	}
	int getMoves() {
		return this.moves;
	}
	void incMoves() {
		this.moves += 1;
	}
	void decMoves() {
		this.moves -= 1;
	}
	boolean getSpecialAvailable() {
		return this.SpecialAvailableasOption;
	}
	void setSpecialAvailable(boolean a){
		this.SpecialAvailableasOption = a;
	}
	double getdefenceHP() {
		return this.defensiveHP;
	}
	void setdefenceHP(double a) {
		this.defensiveHP = a;
	}
	int SpecialCount;
	int SpecialCountA;
	int SpecialCountD;
}

class Warrior extends Hero{
	Warrior() {
		super("Warrior");
	}
	@Override
	protected void attack(Monster m) {
		int extraHP = 0;
		if(this.SpecialCount > 0) {
			if(this.SpecialCountA == 1) {
				extraHP = 5;
				this.SpecialCountA -= 1;
			}
			this.SpecialCount -= 1;
		}
		System.out.println("You choose to attack");
		m.decHP(9 + this.getLvl());
		System.out.println("You attacked and inflicted " + (9 + this.getLvl()) + " damage to the monster");
		if(Math.round(this.getHP()) < 0) {
			this.setHP(0); 
		}else if(Math.round(m.getHP()) < 0) {
			m.setHP(0);
		}
		System.out.println("Your HP: " + (this.getHP() + extraHP) + "/" + this.getmaxHP() + " Monsters HP: " + m.getHP() + "/" + m.getMaxHP());
		if(m.getHP() <= 0) {
			if(m.getLVL() == 4) {
				System.out.println("You Killed Liofang!!!!!! ggz Broski");
				return;
			}else {
				System.out.println("Monster Killed!");
				System.out.println("20XP awarded");
				this.upgrade(m.getLVL());
				System.out.println("Level Up: Level:" + this.getLvl());
				System.out.println("Fight won proceed to the next location");
			}
		}else {
			m.attack(this);
		}
	}
	
	@Override
	protected void defend(Monster m) {
		int extraD = 0;
		if(this.SpecialCount > 0) {
			if(this.SpecialCountD == 1) {
				extraD = 5;
			}
			this.SpecialCount -= 1;
		}
		this.setdefenceHP(2 + this.getLvl() + extraD);
		System.out.println("You choose to defend");
		System.out.println("Monster attack reduced by " + Math.round(this.getdefenceHP()) + "!");
		System.out.println("Your HP: " + Math.round(this.getHP()) + "/" + this.getmaxHP() + " Monsters HP: " + Math.round(m.getHP()) + "/" + m.getMaxHP());
		m.attack(this);
	}
	
	@Override
	protected void SpecialPower(Monster m) {
		this.SpecialCount = 3;
		this.SpecialCountA = 1;
		this.SpecialCountD = 1;
		System.out.println("Attack and Defence attributes are boosted by 5 for next 3 moves");
	}
}

class Thief extends Hero{
	Thief() {
		super("Thief");
	}
	@Override
	protected void attack(Monster m) {
		System.out.println("You choose to attack");
		m.decHP(5 + this.getLvl());
		System.out.println("You attacked and inflicted " + (5 + this.getLvl()) + " damage to the monster");
		if(Math.round(this.getHP()) < 0) {
			this.setHP(0);
		}else if(Math.round(m.getHP()) < 0) {
			m.setHP(0);
		}
		System.out.println("Your HP: " + Math.round(this.getHP()) + "/" + this.getmaxHP() + " Monsters HP: " + Math.round(m.getHP()) + "/" + m.getMaxHP());
		if(m.getHP() <= 0) {
			if(m.getLVL() == 4) {
				System.out.println("You Killed Liofang! ggz Broski");
				return;
			}else {
				System.out.println("Monster Killed!");
				System.out.println("20XP awarded");
				this.upgrade(m.getLVL());
				System.out.println("Level Up: Level:" + this.getLvl());
				System.out.println("Fight won proceed to the next location");
			}
		}else {
			m.attack(this);
		}	
	}

	@Override
	protected void defend(Monster m) {
		this.setdefenceHP(3 + this.getLvl());
		System.out.println("You choose to defend");
		System.out.println("Monster attacked reduced by " + Math.round(this.getdefenceHP()) + "!");
		System.out.println("Your HP: " + Math.round(this.getHP()) + "/" + this.getmaxHP() + "Monsters HP: " + Math.round(m.getHP()) + "/" + m.getMaxHP());
		m.attack(this);
	}
	
	@Override
	protected void SpecialPower(Monster m) {
		System.out.println("You stole 30% of Monster's HP i.e. " + Math.round(m.getHP()*0.3) + " HP");
		m.setHP(m.getHP()*0.7);
		System.out.println("Your HP: " + this.getHP() + "/" + this.getmaxHP() + " Monster's HP: " + m.getHP() + "/" + m.getMaxHP());
		m.attack(this);
	}
	
}

class Mage extends Hero{
	int decInMonstersHP = 0;
	
	Mage() {
		super("Mage");
	}
	@Override
	protected void attack(Monster m) {
		System.out.println("You choose to attack");
		m.decHP(4 + this.getLvl());
		System.out.println("You attacked and inflicted " + (4 + this.getLvl()) + " damage to the monster");
		if(Math.round(this.getHP())<0) {
			this.setHP(0); 
		}else if(Math.round(m.getHP()) < 0) {
			m.setHP(0);
		}
		System.out.println("Your HP: " + Math.round(this.getHP()) + "/" + this.getmaxHP() + " Monsters HP: " + Math.round(m.getHP()) + "/" + m.getMaxHP());
		
		if(m.getHP() <= 0) {
			if(m.getLVL() == 4) {
				System.out.println("You Killed Liofang! ggz Broski");
				return;
			}else {
				System.out.println("Monster Killed!");
				System.out.println("20XP awarded");
				this.upgrade(m.getLVL());
				System.out.println("Level Up: Level:" + this.getLvl());
				System.out.println("Fight won proceed to the next location");
			}
		}else {
			m.attack(this);
		}
		
	}

	@Override
	protected void defend(Monster m) {
		this.setdefenceHP(4 + this.getLvl());
		System.out.println("You choose to defend");
		System.out.println("Monster attacked reduced by " + Math.round(this.getdefenceHP()) + "!");
		System.out.println("Your HP: " + Math.round(this.getHP()) + "/" + this.getmaxHP() + "Monsters HP: " + Math.round(m.getHP()) + "/" + m.getMaxHP());
		m.attack(this);
	}
	
	@Override
	protected void SpecialPower(Monster m) {
		m.decHP(m.getHP()*0.95);
		decInMonstersHP = (int)Math.round(m.getHP()*0.05);
		System.out.println("Monster's HP reduced by 5% for next 3 moves");
		System.out.println("Your HP: " + Math.round(this.getHP()) + "/" + this.getmaxHP() + "Monsters HP: " + Math.round(m.getHP()) + "/" + m.getMaxHP());
	}
}

class Healer extends Hero{
	Healer() {
		super("Healer");
	}
	@Override
	protected void attack(Monster m) {
		System.out.println("You choose to attack");
		m.decHP(3 + this.getLvl());
		System.out.println("You attacked and inflicted " + (3 + this.getLvl()) + " damage to the monster");
		if(Math.round(this.getHP())<0) {
			this.setHP(0); 
		}else if(Math.round(m.getHP()) < 0) {
			m.setHP(0);
		}
		System.out.println("Your HP: " + Math.round(this.getHP()) + "/" + this.getmaxHP() + " Monsters HP: " + Math.round(m.getHP()) + "/" + m.getMaxHP());
		if(m.getHP() <= 0) {
			if(m.getLVL() == 4) {
				System.out.println("You Killed Liofang! ggz Broski");
				return;
			}else {
				System.out.println("Monster Killed!");
				System.out.println("20XP awarded");
				this.upgrade(m.getLVL());
				System.out.println("Level Up: Level:" + this.getLvl());
				System.out.println("Fight won proceed to the next location");
			}
		}else {
			m.attack(this);
		}
	}

	@Override
	protected void defend(Monster m) {
		this.setdefenceHP(7 + this.getLvl());
		System.out.println("You choose to defend");
		System.out.println("Monster attacked reduced by " + Math.round(this.getdefenceHP()) + "!");
		System.out.println("Your HP: " + Math.round(this.getHP()) + "/" + this.getmaxHP() + " Monsters HP: " + Math.round(m.getHP()) + "/" + m.getMaxHP());
		m.attack(this);
	}
	
	@Override
	protected void SpecialPower(Monster m) {
		System.out.println("HP increased by 5% for next 3 moves");
		this.setHP(this.getHP()*1.05);
	}
}



//////////////////////////////////MONSTER///////////////////////////////////
abstract class Monster{
	private double HP;
	private final int maxHP;
	private final int lvl;
	
	Monster(int lvl){
		this.lvl = lvl;
		this.HP = 50 + (50*lvl);
		this.maxHP = (int)this.HP;
	}
	protected void reset() {
		this.HP = this.maxHP;
	}
	
	protected int value(double HP) {
		Random r = new Random();
		int dmg = (int)Math.round(HP);
		while(!(dmg<=(this.HP/4)) && dmg >= 0) {
			dmg = (int)Math.round(r.nextGaussian() + HP/8);
		}
		return dmg;
//		return 2;
	}
	
	protected abstract void attack(Hero h);
	
	double getHP() {
		return this.HP;
	}
	void setHP(double d) {
		this.HP = d;
	}
	void decHP(double a) {
		this.HP -= a;
	}
	int getMaxHP() {
		return this.maxHP;
	}
	int getLVL() {
		return this.lvl;
	}
}

class Goblin extends Monster{
	Goblin() {
		super(1);
	}
	@Override
	protected void attack(Hero h) {
		System.out.println("Monster Attack!");
		int dmg = value(this.getHP());
		if(h.getdefenceHP() > dmg) {
			dmg = 0;
		}else {
			dmg -= h.getdefenceHP();
		}
		h.setdefenceHP(0);
		h.setHP(h.getHP() - dmg);
		System.out.println("The monster attacked and inflicted " + dmg + " damage to you");
		if(Math.round(h.getHP())<0) {
			h.setHP(0); 
		}else if(Math.round(this.getHP()) < 0) {
			this.setHP(0);
		}
		System.out.println("Your HP: " + Math.round(h.getHP()) + "/" + h.getmaxHP() + " Monsters HP: " + Math.round(this.getHP()) + "/" + this.getMaxHP());
		if(Math.round(h.getHP()) == 0) {
			System.out.println("You were killed by the monster!");
			return;
		}
	}
}

class Zombie extends Monster{
	Zombie() {
		super(2);
	}
	@Override
	protected void attack(Hero h) {
		System.out.println("Monster Attack!");
		int dmg = value(this.getHP());
		if(h.getdefenceHP() > dmg) {
			dmg = 0;
		}else {
			dmg -= h.getdefenceHP();
		}
		h.setdefenceHP(0);
		h.setHP(h.getHP() - dmg);
		System.out.println("The monster attacked and inflicted " + dmg + " damage to you");
		if(Math.round(h.getHP())<0) {
			h.setHP(0); 
		}else if(Math.round(this.getHP()) < 0) {
			this.setHP(0);
		}
		System.out.println("Your HP: " + Math.round(h.getHP()) + "/" + h.getmaxHP() + " Monsters HP: " + Math.round(this.getHP()) + "/" + this.getMaxHP());
		if(Math.round(h.getHP()) == 0) {
			System.out.println("You were killed by the monster!");
			return;
		}
	}
}

class Fiend extends Monster{
	Fiend() {
		super(3);
	}
	@Override
	protected void attack(Hero h) {
		System.out.println("Monster Attack!");
		int dmg = value(this.getHP());
		if(h.getdefenceHP() > dmg) {
			dmg = 0;
		}else {
			dmg -= h.getdefenceHP();
		}
		h.setdefenceHP(0);
		h.setHP(h.getHP() - dmg);
		System.out.println("The monster attacked and inflicted " + dmg + " damage to you");
		if(Math.round(h.getHP()) < 0) {
			h.setHP(0); 
		}else if(Math.round(this.getHP()) < 0) {
			this.setHP(0);
		}
		System.out.println("Your HP: " + Math.round(h.getHP()) + "/" + h.getmaxHP() + " Monsters HP: " + Math.round(this.getHP()) + "/" + this.getMaxHP());
		if(Math.round(h.getHP()) == 0) {
			System.out.println("You were killed by the monster!");
			return;
		}
	}
}

class Lionfang extends Monster{
	Lionfang() {
		super(4);
	}
	@Override
	protected void attack(Hero h) {
		System.out.println("Monster Attack!");
		if(h.getMoves() % 10 == 0) {
			int dmg = (int)(Math.round((float)h.getHP()/2) - Math.round(h.getdefenceHP()));
			if(dmg < 0) {
				dmg = 0;
			}
			System.out.println("The monster attacked and inflicted " + dmg + " damage to you.");
			h.setHP(h.getHP() - dmg);
		}else {
			int dmg = value(this.getHP());
			if(h.getdefenceHP() > dmg) {
				dmg = 0;
			}else {
				dmg -= h.getdefenceHP();
			}
			h.setdefenceHP(0);
			System.out.println("The monster attacked and inflicted " + dmg + " damage to you");
			h.setHP(h.getHP() - dmg);
		}
		if(Math.round(h.getHP())<0) {
			h.setHP(0); 
		}else if(Math.round(this.getHP()) < 0) {
			this.setHP(0);
		}
		System.out.println("Your HP: " + Math.round(h.getHP()) + "/" + h.getmaxHP() + " Monsters HP: " + Math.round(this.getHP()) + "/" + this.getMaxHP());
		if(Math.round(h.getHP()) == 0) {
			System.out.println("You were killed by the monster!");
			return;
		}
	}
}


public class Assignment03 {

	public static HashMap<String, User> users = new HashMap<>();
	public static HashMap<String, Hero> heroes = new HashMap<>();
	public static HashMap<Integer, String> heroType = new HashMap<>();
	public static HashMap<Integer, String> monsterType = new HashMap<>();
	public static ArrayList<Integer> locationsMonsters = new ArrayList<>();
	
	public static ArrayList<Integer> Path = new ArrayList<>();
	public static int round = 0;
	
	public static void round0() {
		System.out.println("You are at the starting location. Choose path:");
		System.out.println("1) Go to Location 1");
		System.out.println("2) Go to Location 2");
		System.out.println("3) Go to Location 3");
		System.out.println("Enter -1 to exit");
	}
	
	public static void round1() {
		System.out.println("You are at location " + Path.get(Path.size()-1) + "Choose path:");
		System.out.println("1) Go to Location 4");
		System.out.println("2) Go to Location 5");
		System.out.println("3) Go to Location 6");
		System.out.println("4) Go back");
		System.out.println("Enter -1 to exit");
	}
	
	public static void round2() {
		System.out.println("You are at location " + Path.get(Path.size()-1) + "Choose path:");
		System.out.println("1) Go to Location 7");
		System.out.println("2) Go to Location 8");
		System.out.println("3) Go to Location 9");
		System.out.println("4) Go back");
		System.out.println("Enter -1 to exit");
	}
	
	public static void round3() {
		System.out.println("You are at location " + Path.get(Path.size()-1) + "Choose path:");
		System.out.println("1) Go to Location 10");
		System.out.println("2) Go to Location 11");
		System.out.println("3) Go to Location 12");
		System.out.println("4) Go back");
		System.out.println("Enter -1 to exit");
	}
	
	public static int checkRound(int round) {
		if(round == 0) {
			round0();
		}else if(round == 1) {
			round1();
		}else if(round == 2) {
			round2();
		}else if(round == 3){
			round3();
		}else {
			System.out.println("Wrong input");
		}
		return round+1;
	}
	
	public static void goToLocationAndFight(String name, Scanner s) {
		int in = s.nextInt();
		if(in == -1) {
			return;
		}else {
			if(in == 4) {
				if(Path.size() == 1) {
					System.out.println("Not Possible you are at the start");
				}else {
					Path.remove(Path.size()-1);
					System.out.println("Moving to location " + Path.get(Path.size()-1));
					System.out.println("Fight Started. You are fighting level " + locationsMonsters.get(Path.get(Path.size()-1)) + " Monster");
					int monsterNum = locationsMonsters.get(Path.get(Path.size()-1));
					if(monsterNum == 1) {
						Goblin g = new Goblin();
						while(heroes.get(name).getHP() > 0 && g.getHP() > 0) {
							heroes.get(name).fight(g,s);
						}
						g.reset();
					}else if(monsterNum == 2) {
						Zombie z = new Zombie();
						while(heroes.get(name).getHP() > 0 && z.getHP() > 0) {
							heroes.get(name).fight(z,s);
						}
						z.reset();
					}else if(monsterNum == 3) {
						Fiend f = new Fiend();
						while(heroes.get(name).getHP() > 0 && f.getHP() > 0) {
							heroes.get(name).fight(f,s);
						}
						f.reset();
					}else if(monsterNum == 4) {
						Lionfang l = new Lionfang();
						while(heroes.get(name).getHP() > 0 && l.getHP() > 0) {
							heroes.get(name).fight(l,s);
						}
					}
					round = checkRound(round - 2);
					goToLocationAndFight(name, s);
				}
			}else {
				System.out.println("Moving to location " + in);
				System.out.println("Fight Started. You are fighting level " + locationsMonsters.get(in) + " Monster");
				int index = in + (3*(round-1));
				Path.add(index);
				int monsterNum = locationsMonsters.get(index);
				if(monsterNum == 1) {
					Goblin g = new Goblin();
					while(heroes.get(name).getHP() > 0 && g.getHP() > 0) {
						heroes.get(name).fight(g,s);
					}
					g.reset();
				}else if(monsterNum == 2) {
					Zombie z = new Zombie();
					while(heroes.get(name).getHP() > 0 && z.getHP() > 0) {
						heroes.get(name).fight(z,s);
					}
					z.reset();
				}else if(monsterNum == 3) {
					Fiend f = new Fiend();
					while(heroes.get(name).getHP() > 0 && f.getHP() > 0) {
						heroes.get(name).fight(f,s);
					}
					f.reset();
				}else if(monsterNum == 4) {
					Lionfang l = new Lionfang();
					while(heroes.get(name).getHP() > 0 && l.getHP() > 0) {
						heroes.get(name).fight(l,s);
					}
					l.reset();
				}
				round = checkRound(round);
				goToLocationAndFight(name, s);
			}
		}
	}
	
	public static void print(Scanner s) {
		
		System.out.println("Welcome to ArchLegends");
		System.out.println("Choose your option");
		System.out.println("1) New User");
		System.out.println("2) Existing User");
		System.out.println("3) Exit");
		
		int n = s.nextInt();
		if(n == 1) {
			System.out.println("Enter Username");
			String name = s.next();
			
			System.out.println("Choose a Hero");
			System.out.println("1) Warrior");
			System.out.println("2) Thief");
			System.out.println("3) Mage");
			System.out.println("4) Healer");
			int heroNum = s.nextInt();
			
			User u = new User(name, heroNum);
			users.put(name, u);
			
			if(heroNum == 1) {
				Warrior h = new Warrior();
				heroes.put(name, h);
			}else if(heroNum == 2) {
				Thief h = new Thief();
				heroes.put(name, h);
			}else if(heroNum == 3) {
				Mage h = new Mage();
				heroes.put(name, h);
			}else if(heroNum == 4) {
				Healer h = new Healer();
				heroes.put(name, h);
			}else {
				System.out.println("Invalid Option");
			}
			
			System.out.println("User Creation done. Username:" + u.getName() + ". Hero type: " + heroType.get(heroNum) + ". Log in to play the game. Exiting");
			print(s);
		}else if(n == 2) {
			System.out.println("Enter Username");
			String name = s.next();
			if(users.containsKey(name)) {
				System.out.println("User Found... logging in");
				System.out.println("Welcome " + name);
				round = checkRound(round);
				goToLocationAndFight(name, s);
			}else {
				System.out.println("No such user found");
			}
		}else if(n == 3) {
			return;
		}
	}
	
	public static void main(String args[]) {
		Scanner s = new Scanner(System.in);
		
		heroType.put(1, "Warrior");
		heroType.put(2, "Thief");
		heroType.put(3, "Mage");
		heroType.put(4, "Healer");
		monsterType.put(1, "Goblins");
		monsterType.put(2, "Zombies");
		monsterType.put(3, "Fiends");
		monsterType.put(4, "Lionfang");
		
		Random rand = new Random();
		locationsMonsters.add(0);
		for(int i = 1; i < 10; i++) {
			int a = rand.nextInt(4);
			while(a<1) {
				a = rand.nextInt(4);
			}
			locationsMonsters.add(a);
		}
		for(int i = 10; i<13; i++) {
			locationsMonsters.add(4);
		}
		Path.add(0);
		
		print(s);
		
	}
}
