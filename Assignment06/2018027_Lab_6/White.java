
public class White extends Tiles{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected White(){
		super("White");
	}
	@Override
	protected void shakeTile(Player p) {
		System.out.println(">>          I am a White Tile!");
		System.out.println(">>          " + p.getName() + " moved to Tile-" + (p.getPosition() + 1));
	}
}