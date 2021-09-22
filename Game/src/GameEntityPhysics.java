import processing.core.PApplet;
import processing.core.PVector;

public class GameEntityPhysics extends PApplet {
	public static final double FRICTION = -0.005;
	public static PApplet ctx;
	
	MoveableGameEntity vehicle;

	public static void main(String[] args) {
		PApplet.main(GameEntityPhysics.class);
		
	}
	
	public void settings() { size(800,400); }
	
	public void setup() {
		ctx = this;
		vehicle = new MoveableGameEntity();
	}
	
	public void draw() {
		background(255);
		
		vehicle.update();	
		vehicle.draw();
	}
	
	public void mouseClicked() {
		PVector f = new PVector(5,5);
		vehicle.applyForce(f);
	}
}
