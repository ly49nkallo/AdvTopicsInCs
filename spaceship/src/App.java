import processing.core.PApplet;
import processing.core.PVector;

public class App extends PApplet{
    ParticleSystem particles;
	private final int numberOfParticles = 2;
    final public int HEIGHT = 700;
    final public int WIDTH = 1000;
    final public int BUFFER = 50; // minimum distance from any edge a particle can start
    final public double G = 1000.0d;
    public static void main(String[] args) {
        PApplet.main(App.class);
    }
    public static App ctx;
    public void settings() { 
        size(WIDTH, HEIGHT); 
    }
    public void setup() {
        // noLoop();
        background(255);
		ctx = this;
        particles = new ParticleSystem(ctx, numberOfParticles);
        particles.setup();

        PVector a = new PVector(2,2);
        PVector b = new PVector(-2,-2);
        PVector c = a.sub(b);
        System.out.println(String.format("Vector a (%f, %f) minus vector b (%f, %f) is (%f, %f)", a.x, a.y, b.x, b.y, c.x, c.y));
    }
    public void draw() {
        particles.update();
        background(255);
        particles.draw();
    }
}
