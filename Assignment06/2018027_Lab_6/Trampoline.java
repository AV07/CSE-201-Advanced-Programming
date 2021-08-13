
public class Trampoline extends Tiles{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected Trampoline(){
		super("Trampoline");
	}
	protected void throwE() throws TrampolineException {
		throw new TrampolineException();
	}
	
	@Override
	protected void shakeTile(Player p) throws TrampolineException {
		System.out.println(">>          Trampoline...! I am a Cricket, you go back " + this.getSteps() + " tiles. ");
		System.out.print("            ");
		p.setTrampolineUsed(p.getTrampolineUsed()+1);
		this.throwE();
	}
}