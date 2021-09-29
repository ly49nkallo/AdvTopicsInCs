import processing.core.PApplet;
import processing.core.PVector;
import java.util.Random;

public class DemoApp extends PApplet {

	Particle[] particles;
	private final int numberOfParticles = 20;

    public static void main(String[] args) {
        PApplet.main(DemoApp.class);
    }

    public static PApplet ctx;
    public void settings() { size(800,400); }
	
	public void setup() {
		ctx = this;

		particles = new Particle [numberOfParticles];
		for (int i = 0; i < particles.length; i++) {
			particles[i] = new Particle(this);
			particles[i].setup();
		}
	}
	
	public void draw() {
		background(255);
		for (Particle p : particles) {
			p.sm.update();
			p.update();
			p.draw();
		}
	}
}

