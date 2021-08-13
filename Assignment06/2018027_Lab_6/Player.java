
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;


public class Player implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected final HashMap<Integer, String> tiles = new HashMap<>();
	protected final HashMap<String, Integer> tiles1 = new HashMap<>();
	protected final HashMap<Integer, Integer> count = new HashMap<>();
	protected final HashMap<Integer, Integer> stepsAffected = new HashMap<>();
	protected final ArrayList<Tiles> track = new ArrayList<>();
	private String name;
	private int position;
	private int rollNum;
	private int snakeBites;
	private int vultureBites;
	private int cricketBites;
	private int trampolineUsed;
	private int checkPointsReached;
	private int n;
	
	@Override
	public boolean equals(Object o) {
		if(o == this) {
			return true;
		}
		if (!(o instanceof Player)) { 
            return false; 
        }
		Player c = (Player) o;
		if(this.name.equals(c.name)) {
			if(this.position == c.position) {
				if(this.rollNum == c.rollNum) {
					if(this.snakeBites == c.snakeBites) {
						if(this.vultureBites == c.vultureBites) {
							if(this.cricketBites == c.cricketBites) {
								if(this.trampolineUsed == c.trampolineUsed) {
									if(this.checkPointsReached == c.checkPointsReached) {
										if(this.n == c.n) {
											if(this.tiles.equals(c.tiles)) {
												if(this.tiles1.equals(c.tiles1)) {
													if(this.count.equals(c.count)) {
														if(this.stepsAffected.equals(c.stepsAffected)) {
															return true;
														}
													}
												}
											}
										}
									}
								}
							}
						}
					}
				}
			}
		}
		return false;
	}
	
	protected void serialize() {
		try {
			ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(this.name + ".txt"));
			out.writeObject(this);
			out.close();
		}
		catch(IOException e) {
		}
		File f = new File(name + ".txt");
		if(f.exists()) {
			System.out.println();
			System.out.println("|--------------------------------|");
			System.out.println("|           GAME SAVED           |");
			System.out.println("|--------------------------------|");
			System.out.println();
			System.out.println(f.getAbsolutePath());
		}
	}
	
	protected Player deserialize() {
		File f = new File(this.name + ".txt");
		if(f.exists()) {
			System.out.println("File Found");
		}
		Player obj = new Player(this.name);
		ObjectInputStream in = null;
		try {
			in = new ObjectInputStream(new FileInputStream(f));
			obj = (Player)in.readObject();
			System.out.println("Data of player loaded");
			in.close();
		} catch (IOException e) {
			System.out.println("IOException caught");
		} catch (ClassNotFoundException e) {
			System.out.println("ClassNotFoundException caught");
		}
		f.delete();
		return obj;
	}
	
	Player(String s){
		this.name = s;
		this.setPosition(0);
		this.setRollNum(1);
		this.snakeBites = 0;
		this.vultureBites = 0;
		this.cricketBites = 0;
		this.trampolineUsed = 0;
		this.checkPointsReached = 0;
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

	public int getcheckPointsReached() {
		return checkPointsReached;
	}

	public void inccheckPointsReached() {
		this.checkPointsReached += 1;
	}

	public int getN() {
		return n;
	}

	public void setN(int n) {
		this.n = n;
	}
}