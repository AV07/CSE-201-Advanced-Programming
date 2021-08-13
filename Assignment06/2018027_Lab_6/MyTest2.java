
import java.io.File;
import java.io.IOException;
import java.util.Random;
import java.util.Scanner;

import org.junit.Test;

public class MyTest2 {
	
	@Test(expected = SnakeBiteException.class)
	public void testSnakeTile() throws SnakeBiteException {
		Snake t = new Snake();
		t.throwE();
	}
	
	@Test(expected = VultureBiteException.class)
	public void testVultureTile() throws VultureBiteException {
		Vulture t = new Vulture();
		t.throwE();
	}
	
	@Test(expected = TrampolineException.class)
	public void testTrampolineTile() throws TrampolineException {
		Trampoline t = new Trampoline();
		t.throwE();
	}
	@Test(expected = CricketBiteException.class)
	public void testCricketTile() throws CricketBiteException{
		Cricket t = new Cricket();
		t.throwE();
	}
	
	@Test(expected = GameWonException.class)
	public void testGameWonException() throws GameWonException, IOException{
		Assignment06 a = new Assignment06();
		String name = "atul";
		int n = 100;
		File f = new  File(name + ".txt");
		if(f.exists()) {
			f.delete();
		}
		a.p = new Player(name);
		a.p.setN(n);
		Random r = new Random();
		a.initialize(r);
		a.initializeTrack(r, n);
		Scanner s = new Scanner(System.in);
		a.myTest2Called = true;
		a.gameBegins(a.p, r, n, s);
	}
	
}
