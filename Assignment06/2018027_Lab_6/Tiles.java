
import java.io.Serializable;

public abstract class Tiles implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final String type;
	private int steps;
	Tiles(String s) {
		this.type = s;
		this.setSteps(0);
	}
	abstract protected void shakeTile(Player p) throws VultureBiteException, SnakeBiteException, CricketBiteException, CricketBiteException, TrampolineException;
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