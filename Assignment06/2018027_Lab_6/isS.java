
import java.util.Scanner;

public class isS {
	public static boolean isSaved(Scanner s) {
		System.out.println("Enter 1 to Continue");
		System.out.println("Enter 2 to Save the Game");
		int x;
		x = s.nextInt();
		if(x == 2) {
			return true;
		}
		return false;
	}
}