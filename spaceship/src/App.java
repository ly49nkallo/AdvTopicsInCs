import processing.core.PApplet;
// import processing.core.PVector;

public class App extends PApplet{
    ParticleSystem particles;
	private final int numberOfParticles = 100;
    final public int HEIGHT = 1000;
    final public int WIDTH = 1000;
    final public int BUFFER = 50; // minimum distance from any edge a particle can start
    final public double G = 1.0d;
    public static void main(String[] args) {
        PApplet.main(App.class);
    }
    public static App ctx;
    public void settings() { 
        size(HEIGHT, WIDTH); 
    }
    public void setup() {
        background(255);
		ctx = this;
        particles = new ParticleSystem(ctx, numberOfParticles);
        particles.setup();
        draw();
    }
    public void draw() {
        particles.update();
        background(255);
        particles.draw();
    }
}
