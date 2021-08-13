
import org.junit.Test;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.Random;
import java.util.Scanner;

import org.junit.*;

public class MyTest1 {

	Player serialized;
	Player deserialized;

	@Before
	public void objectGeneration() throws GameWonException {	
		Assignment06 a = new Assignment06();
		String name = "xyz";
		int n = 100;
		a.p = new Player(name);
		a.p.setN(n);
		Random r = new Random();
		a.initialize(r);
		a.initializeTrack(r, n);
		Scanner s = new Scanner(System.in);
		a.myTest1Called = true;
		serialized = a.gameBegins(a.p, r, n, s);
		deserialized = new Player(name);
		deserialized = deserialized.deserialize();
	}
	
	@Test
	public void JUnitTest2() {
		assertTrue(serialized.equals(deserialized));
	}
}
