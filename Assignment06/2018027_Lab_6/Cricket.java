
public class Cricket extends Tiles{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected Cricket(){
		super("Cricket");
	}
	protected void throwE() throws CricketBiteException {
		throw new CricketBiteException();
	}
	@Override
	protected void shakeTile(Player p) throws CricketBiteException {
		System.out.println(">>          Chirp...! I am a Cricket, you go back " + this.getSteps() + " tiles. ");
		System.out.print("            ");
		p.setCricketBites(p.getCricketBites()+1);
		this.throwE();
	}
}