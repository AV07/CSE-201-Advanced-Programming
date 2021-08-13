
public class Snake extends Tiles{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected Snake(){
		super("Snake");
	}
	protected void throwE() throws SnakeBiteException {
		throw new SnakeBiteException();
	}
	@Override
	protected void shakeTile(Player p) throws SnakeBiteException {
		System.out.println(">>          Hiss...! I am a Snake, you go back " + this.getSteps() + " tiles. ");
		System.out.print("            ");
		p.setSnakeBites(p.getSnakeBites() + 1);
		this.throwE();
	}
}