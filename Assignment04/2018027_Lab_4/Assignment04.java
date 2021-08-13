package Assignment4;

import java.util.HashMap;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
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

//////////////////////////////////////SideKick////////////////////////////////////////
abstract class SideKick implements Cloneable{
	private double XP;
	private double HP;
	private final double MaxHP;
	private final String type;
	private double attackDamage;
	
	SideKick(int XP, String t) {
		this.type = t;
		this.setXP(0.0);
		this.setHP(100.0);
		this.MaxHP = this.getHP();
		if(t == "minion") {
			this.setAttackDamage(1 + 0.5*XP);
		}else if(t == "knight") {
			this.setAttackDamage(2 + 0.5*XP);
		}
	}
	
	protected abstract void clonesAttacked(double dmg);
	protected abstract void initialize(Scanner s);
	protected abstract void attack(Monster m);
	
	@Override
	protected Object clone() throws CloneNotSupportedException{
		return super.clone();
	}

	public double getAttackDamage() {
		return attackDamage;
	}

	public void setAttackDamage(double attackDamage) {
		this.attackDamage = attackDamage;
	}

	public double getHP() {
		return HP;
	}

	public void setHP(double hP) {
		HP = hP;
	}

	public double getXP() {
		return XP;
	}

	public void setXP(double xP) {
		XP = xP;
	}

	public String getType() {
		return type;
	}
}

class minion extends SideKick{
	 boolean cloningUsed = false;
	ArrayList<SideKick> cloned = new ArrayList<>();
	
	public minion(int XP){
		super(XP, "minion");
	}
	
	@Override
	protected void initialize(Scanner s) {
		System.out.println("Press c to use cloning ability. Else press f to move to fight.");
		String in = s.next();
		if(in.equals("c")) {
			this.cloningUsed = true;
			for(int i = 0; i < 3; i++) {
				SideKick a1;
				try {
					a1 = (SideKick)this.clone();
					this.cloned.add(a1);
				} catch (CloneNotSupportedException e) {
				}
			}
			System.out.println("Cloning Done");
		}
	}
	@Override
	protected void attack(Monster m) {
		m.decHP(this.getAttackDamage());
		System.out.println("SideKick attacked and inflicted " + this.getAttackDamage() + " damage to the monster.");
		if(this.cloningUsed) {
			for(int i = 0; i < this.cloned.size(); i++) {
				m.decHP(this.cloned.get(i).getAttackDamage());
				System.out.println("SideKick attacked and inflicted " + this.cloned.get(i).getAttackDamage() + " damage to the monster.");
			}
		}
		System.out.println("SideKick HP:" + this.getHP() + "/100");
		if(this.cloningUsed) {
			for(int i = 0; i < this.cloned.size(); i++) {
				System.out.println("SideKick HP:" + this.cloned.get(i).getHP() + "/100");
			}
		}
	}

	@Override
	protected void clonesAttacked(double dmg) {
		for(int i = 0; i<this.cloned.size(); i++) {
			this.cloned.get(i).setHP(this.cloned.get(i).getHP() - dmg);
			System.out.println("SideKick's HP:" + this.cloned.get(i).getHP() + "/100");
		}
	}
	
	
}

class knight extends SideKick{
	public knight(int XP) {
		super(XP, "knight");
	}
	
	@Override
	protected void initialize(Scanner s) {
	}
	@Override
	protected void attack(Monster m) {
		m.decHP(this.getAttackDamage());
		System.out.println("SideKick attacked and inflicted " + this.getAttackDamage() + " damage to the monster.");
		System.out.println("SideKick HP:" + this.getHP() + "/100");
	}

	@Override
	protected void clonesAttacked(double dmg) {
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
	private int currDefeatedMonsterLevel;
	private boolean SpecialAvailableasOption;
	boolean sideKickSelected;
	boolean sideKickusedonce;
	private SideKick sidekick;
	
	Hero(String type){
		this.type = type;
		this.maxHP = 100;
		this.HP = 100;
		this.lvl = 1;
		this.XP = 0;
		this.moves = 0;
		this.SpecialAvailableasOption = false;
		this.sideKickusedonce = false;
		this.sideKickSelected = false;
	}
	protected void upgrade(int l) {
		this.XP += (l*20);
		
		if(!this.sideKickusedonce && this.sideKickSelected) {
			this.getSideKick().setXP(this.getSideKick().getXP() + (l*2));
			this.getSideKick().setHP(100);
			this.getSideKick().setAttackDamage(this.getSideKick().getAttackDamage() + Math.round((l*2)/5));
		}
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
	void setSidekick(SideKick sk){
		this.sidekick = sk;
	}
	SideKick getSideKick() {
		return this.sidekick;
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
	public int getCurrDefeatedMonsterLevel() {
		return currDefeatedMonsterLevel;
	}
	public void setCurrDefeatedMonsterLevel(int currDefeatedMonsterLevel) {
		this.currDefeatedMonsterLevel = currDefeatedMonsterLevel;
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
		if(this.sideKickSelected) {
			this.getSideKick().attack(m);
			if(this.getSideKick().getHP() <= 0) {
				this.sideKickSelected = false;
			}
		}
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
				this.setCurrDefeatedMonsterLevel(m.getLVL());
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
		if(this.sideKickSelected && this.getSideKick().getType().equals("knight") && m.getLVL() == 2) {
			this.setdefenceHP(this.getdefenceHP() + 5);
		}
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
		if(this.sideKickSelected) {
			this.getSideKick().attack(m);
			if(this.getSideKick().getHP() <= 0) {
				this.sideKickSelected = false;
			}
		}
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
				this.setCurrDefeatedMonsterLevel(m.getLVL());
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
		if(this.sideKickSelected && this.getSideKick().getType().equals("knight") && m.getLVL() == 2) {
			this.setdefenceHP(this.getdefenceHP() + 5);
		}
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
		if(this.sideKickSelected) {
			this.getSideKick().attack(m);
			if(this.getSideKick().getHP() <= 0) {
				this.sideKickSelected = false;
			}
		}
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
				this.setCurrDefeatedMonsterLevel(m.getLVL());
				System.out.println("Fight won proceed to the next location");
			}
		}else {
			m.attack(this);
		}
		
	}

	@Override
	protected void defend(Monster m) {
		this.setdefenceHP(4 + this.getLvl());
		if(this.sideKickSelected && this.getSideKick().getType().equals("knight") && m.getLVL() == 2) {
			this.setdefenceHP(this.getdefenceHP() + 5);
		}
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
		m.attack(this);
		
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
		if(this.sideKickSelected) {
			this.getSideKick().attack(m);
			if(this.getSideKick().getHP() <= 0) {
				this.sideKickSelected = false;
			}
		}
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
				this.setCurrDefeatedMonsterLevel(m.getLVL());
				System.out.println("Fight won proceed to the next location");
			}
		}else {
			m.attack(this);
		}
	}

	@Override
	protected void defend(Monster m) {
		this.setdefenceHP(7 + this.getLvl());
		if(this.sideKickSelected && this.getSideKick().getType().equals("knight") && m.getLVL() == 2) {
			this.setdefenceHP(this.getdefenceHP() + 5);
		}
		System.out.println("You choose to defend");
		System.out.println("Monster attacked reduced by " + Math.round(this.getdefenceHP()) + "!");
		System.out.println("Your HP: " + Math.round(this.getHP()) + "/" + this.getmaxHP() + " Monsters HP: " + Math.round(m.getHP()) + "/" + m.getMaxHP());
		m.attack(this);
	}
	
	@Override
	protected void SpecialPower(Monster m) {
		System.out.println("HP increased by 5% for next 3 moves");
		this.setHP(this.getHP()*1.05);
		m.attack(this);
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
//		return dmg;
		return 1;
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
		if(h.sideKickSelected) {
			h.getSideKick().setHP(h.getSideKick().getHP() - dmg*1.5);
			System.out.println("SideKick's HP:" + h.getSideKick().getHP() + "/100");
			if(h.getSideKick().getType().equals("minion")) {
				h.getSideKick().clonesAttacked(dmg*1.5);
			}
			if(Math.round(h.getSideKick().getHP()) <= 0) {
				h.sideKickSelected = false;
				System.out.println("SideKick Killed");
			}
		}
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
		if(h.sideKickSelected) {
			h.getSideKick().setHP(h.getSideKick().getHP() - dmg*1.5);
			System.out.println("SideKick's HP:" + h.getSideKick().getHP() + "/100");
			if(h.getSideKick().getType().equals("minion")) {
				h.getSideKick().clonesAttacked(dmg*1.5);
			}
			if(Math.round(h.getSideKick().getHP()) <= 0) {
				h.sideKickSelected = false;
				System.out.println("SideKick Killed");
			}
		}
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
		if(h.sideKickSelected) {
			h.getSideKick().setHP(h.getSideKick().getHP() - dmg*1.5);
			System.out.println("SideKick's HP:" + h.getSideKick().getHP() + "/100");
			if(h.getSideKick().getType().equals("minion")) {
				h.getSideKick().clonesAttacked(dmg*1.5);
			}
			if(Math.round(h.getSideKick().getHP()) <= 0) {
				h.sideKickSelected = false;
				System.out.println("SideKick Killed");
			}
		}
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
		int cloD = 0;
		if(h.getMoves() % 10 == 0) {
			int dmg = (int)(Math.round((float)h.getHP()/2) - Math.round(h.getdefenceHP()));
			if(dmg < 0) {
				dmg = 0;
			}
			System.out.println("The monster attacked and inflicted " + dmg + " damage to you.");
			h.setHP(h.getHP() - dmg);
			cloD = dmg;
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
			cloD = dmg;
		}
		
		if(Math.round(h.getHP())<0) {
			h.setHP(0); 
		}else if(Math.round(this.getHP()) < 0) {
			this.setHP(0);
		}
		System.out.println("Your HP: " + Math.round(h.getHP()) + "/" + h.getmaxHP() + " Monsters HP: " + Math.round(this.getHP()) + "/" + this.getMaxHP());
		if(h.sideKickSelected && h.sideKickusedonce == false) {
			h.getSideKick().setHP(h.getSideKick().getHP() - cloD);
			System.out.println("SideKick's HP:" + h.getSideKick().getHP() + "/100");
			if(h.getSideKick().getType().equals("minion")) {
				h.getSideKick().clonesAttacked(cloD);
			}
			if(Math.round(h.getSideKick().getHP()) <= 0) {
				h.sideKickSelected = false;
				h.sideKickusedonce = true;
				System.out.println("SideKick Killed");
			}
		}
		if(Math.round(h.getHP()) <= 0) {
			System.out.println("You were killed by the monster!");
			return;
		}
	}
}
class Customcompare implements Comparator<SideKick>{
	public int compare(SideKick a, SideKick b) {
		if(a.getXP() > b.getXP()) {
			return 1;
		}else {
			return -1;
		}
	}
}

public class Assignment04 {

	public static HashMap<String, User> users = new HashMap<>();
	public static HashMap<String, Hero> heroes = new HashMap<>();
	public static HashMap<Integer, String> heroType = new HashMap<>();
	public static HashMap<Integer, String> monsterType = new HashMap<>();
	public static ArrayList<Integer> locationsMonsters = new ArrayList<>();
	public static ArrayList<SideKick> sideKicks = new ArrayList<>();
	
	public static ArrayList<Integer> Path = new ArrayList<>();
	public static int round = 0;
	public static int numberSideKicks = 0;
	
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
	
	
	
	public static SideKick findBestSideKick() {
		Collections.sort(sideKicks, new Customcompare());
		return sideKicks.get(0);
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
					if(heroes.get(name).getHP() == 0) {
						return;
					}
					round = checkRound(round - 2);
					goToLocationAndFight(name, s);
				}
			}else {
				System.out.println("Moving to location " + in);
				System.out.println("Fight Started. You are fighting level " + locationsMonsters.get(in) + " Monster");
				int index = in + (3*(round-1));
				if(index > 3) {
					System.out.println("Type YES if you wish to use a sidekick, else type NO");
					String YN = s.next();
					
					if(YN.equals("YES") && sideKicks.size()>0) {
						heroes.get(name).sideKickSelected = true;
						SideKick side = findBestSideKick();
						heroes.get(name).setSidekick(side);
						System.out.println("You have a sidekick " + side.getType() + " with you");
						side.initialize(s);
					}
				}
				
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
				heroes.get(name).sideKickusedonce = false;
				heroes.get(name).sideKickSelected = false;
				if(heroes.get(name).getHP() == 0) {
					return;
				}
				int siZe = sideKicks.size();
				for(int l = 0; l < siZe; l++) {
					if(sideKicks.get(l).getHP() <=0) {
						sideKicks.remove(l);
					}
				}
				
				System.out.println("If you would like to buy a sidekick, type YES. Else type no toupgrade level");
				String yn = s.next();
				if(yn.equals("YES")) {
					heroes.get(name).addtoXP(20);
					System.out.println("Your current XP is " + heroes.get(name).getXP());
					System.out.println("If you want to buy a minion(5 XP needed), press 1");
					System.out.println("If you want to buy a knight(8 XP needed), press 2");
					int SidekickNumber = s.nextInt();
					System.out.println("XP to spend:");
					int XPspend = s.nextInt();
					
					System.out.print("You bought a sidekick: ");
					SideKick sk;
					if(SidekickNumber == 1) {
						System.out.println("minion");
						sk = new minion(XPspend - 5);
						heroes.get(name).addtoXP(-XPspend);
						System.out.println("XP of sidekick is 0.0");
						System.out.println("Attack of sidekick is " + sk.getAttackDamage());
						sideKicks.add(sk);
					}else if(SidekickNumber == 2) {
						System.out.println("knight");
						sk = new knight(XPspend - 8);
						heroes.get(name).addtoXP(-XPspend);
						System.out.println("XP of sidekick is 0.0");
						System.out.println("Attack of sidekick is " + sk.getAttackDamage());
						sideKicks.add(sk);
					}
					numberSideKicks += 1;
				}else if(yn.equals("NO")) {
					heroes.get(name).upgrade(heroes.get(name).getCurrDefeatedMonsterLevel());
					System.out.println("Level Up: Level:" + heroes.get(name).getLvl());
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
