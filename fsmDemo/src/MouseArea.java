import processing.core.PApplet;
import processing.core.PConstants;
import static processing.core.PApplet.lerpColor;

public class MouseArea {
	private static PApplet app;
	private static int SPEED = 100; // total frames to change colors over
	
	private Region region;
	private int id;
	private int color1, color2; 
	private int currentColor;
	
	private int colorChangeDirection = 1;
	private double colorSpeed = 1.0/SPEED;
	private float colorT = 0.0f;
	
	public MouseArea(double top, double bottom, double left, double right, int color1, int color2, int id) {
		//initialize the PApplet context once for the class
		if(app == null) {
			app = DemoApp.ctx;
		}

		this.id = id;

		this.region = new Region(top,bottom,left,right,id);
		this.color1 = color1;
		this.color2 = color2;
		currentColor = color1;
		
	}
	
	public boolean contains(int x, int y) {
		return region.inside(x,y);
	}
	
	public void update() {
		currentColor = app.lerpColor(color1,color2,colorT);
		colorT += colorSpeed * colorChangeDirection;
		
		//at the bounds reverse the color direction
		if(colorT >= 1.0 || colorT <= 0.0) {
			colorChangeDirection *= -1;
			colorT = app.constrain(colorT, 0.0f, 1.0f);
		}
		
	}
	
	public void draw() {
		PApplet pa = DemoApp.ctx;
		pa.noStroke();
		pa.fill(currentColor);
		pa.rectMode(PConstants.CORNER);
		pa.rect((float)region.left(), (float)region.top(), (float)region.width(), (float)region.height());
	}
	
	public void resetColor() {
		currentColor = color1;
	}
	
	public int id() {
		return id;
	}
}
