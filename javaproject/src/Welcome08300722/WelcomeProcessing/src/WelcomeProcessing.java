import processing.core.PApplet;

public class WelcomeProcessing extends PApplet {

	public static void main(String[] args) {
		PApplet.main("WelcomeProcessing");		
	}
	
	@Override
	public void settings() {
		size(800,200);
	}
	
	@Override
	public void setup() {
		background(50,50,200);
		textSize(67);
		textAlign(CENTER,CENTER);
		text("Welcome to CS@Geffen", width * 0.5f, height * 0.5f);
		
		noLoop();
	}


}
