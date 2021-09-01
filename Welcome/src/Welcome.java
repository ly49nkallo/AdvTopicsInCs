
public class Welcome {

	public static void main(String[] args) {
		String person = null;
		
		if(args.length > 0) {
			person = args[0];
		}
		
		if(person != null) {
			System.out.println("Welcome to another year " + person + "!");
		} else {
			System.out.println("Welcome to Adv. Intro to CS!");
		}
	}

}
