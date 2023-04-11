import processing.core.PApplet;
import processing.core.PVector;

public class Particle {
    public final float size = 15; //in px

    public PVector pos = new PVector();
    public PVector vel = new PVector();
    public PVector acc = new PVector();
    public PVector heading = new PVector();

    public double mass; 
    public double charge;

    private App ctx;

    public Particle(App app, double mass, double charge, PVector pos, PVector vel, PVector heading){
        this.ctx = app;
        this.mass = mass;
        this.charge = charge;
        this.pos = pos;
        this.vel = vel;
        this.heading = heading;
    }
    public Particle(App app){
        this(app, 1d, 0d, new PVector(), new PVector(), new PVector());
    }
    public void setup() {
        System.out.println("Particle setup");
    }
    public void draw() {
        ctx.fill(0,150);
		ctx.stroke(0);
		ctx.circle(pos.x, pos.y, size);
    }
    public void update() {
        vel.add(acc);
        vel.normalize(heading);
        pos.add(vel);
        //reset acceleration every timestep
        acc.x = acc.y = 0;
    }
    public void applyForce(PVector f) {
        acc = f.copy();
        acc.mult((float)(1.0/mass));
	}
}