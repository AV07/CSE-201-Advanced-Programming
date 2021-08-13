
public class Vulture extends Tiles{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected Vulture(){
		super("Vulture");
	}
	protected void throwE() throws VultureBiteException {
		throw new VultureBiteException();
	}
	@Override
	protected void shakeTile(Player p) throws VultureBiteException {
		System.out.println(">>          Yapping...! I am a Vulture, you go back " + this.getSteps() + " tiles. ");
		System.out.print("            ");
		p.setVultureBites(p.getVultureBites() + 1);
		this.throwE();
	}
}