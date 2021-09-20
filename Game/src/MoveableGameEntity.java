import processing.core.PApplet;
import processing.core.PVector;

public class MoveableGameEntity {
	private static final PApplet ctx = GameEntityPhysics.ctx;
	private int size = 15;
	private double mass = ctx.random(5,5);
	
	PVector pos = new PVector();
	PVector vel = new PVector();
	PVector acc = new PVector();
	PVector heading = new PVector();
	
	MoveableGameEntity() {
	}
	
	public void update() {
		//integrate forces
		vel.add(acc);
		
		//update heading
		vel.normalize(heading);
		
		// if we can add friction without reversing the velocity then add it
		if(vel.magSq() > GameEntityPhysics.FRICTION * GameEntityPhysics.FRICTION ) {
			PVector friction = PVector.mult(heading, (float)GameEntityPhysics.FRICTION);
			vel.add(friction);
		}
		pos.add(vel);
		
		// reset the acceleration to 0 every time step
		acc.x = acc.y=0;
	}
	
	public void draw() {
		ctx.pushStyle();
		ctx.fill(0);
		ctx.stroke(0);
		ctx.circle(pos.x, pos.y, size);
		ctx.popStyle();
	}
	
	public void applyForce(PVector f) {
		acc = f.copy();
		acc.mult((float)(1.0/mass));
	}
}
