import processing.core.PApplet;
import processing.core.PVector;
import java.util.Random;

public class ParticleSystem {
    public int num;
    public Particle[] particles;
    App ctx;
    Random rand = new Random();

    public ParticleSystem(App app, int num){
        this.ctx = app;
        this.num = num;
        this.particles = new Particle[num];
        for (int i = 0; i < this.particles.length; i++) {
            this.particles[i] = new Particle(this.ctx);
        }
        for (Particle p : this.particles) {
            p.pos = new PVector(rand.nextInt(app.WIDTH - (2 * app.BUFFER)) + app.BUFFER,
                                rand.nextInt(app.HEIGHT - (2 * app.BUFFER)) + app.BUFFER);
            PVector.mult(PVector.random2D(), 2, p.vel);
        }
    }
    public ParticleSystem(App app, Particle[] particles){
        this.ctx = app;
        this.num = particles.length;
        this.particles = particles;
    }
    public void setup() {
        for (Particle p : this.particles) {
            p.setup();
        }
    }
    public void draw() {
        for (Particle p : this.particles) {
            System.out.println(String.format("%d, %d", (int)p.pos.x, (int)p.pos.y));
            p.draw();
        }
    }
    public void update() {
        // apply forces to particles in system
        for (int i = 0; i < this.particles.length; i++) {
            for (int j = 0; j < this.particles.length; j++){
                if (i == j) continue;
                PVector difference = new PVector();
                PVector.sub(this.particles[i].pos, this.particles[j].pos, difference);
                PVector direction = new PVector();
                difference.normalize(direction);
                float distance = difference.mag();
                PVector force = new PVector();
                PVector.mult(direction, -(float)(this.ctx.G * this.particles[i].mass * this.particles[j].mass / 
                                        (distance * distance) / 2), force);
                System.out.println(String.format("Force %f %f", force.x, force.y));
                particles[i].applyForce(force);
            }
        }
        for (Particle p : this.particles) {
            p.update();
        }
    }
}
