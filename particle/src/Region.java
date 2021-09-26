import java.awt.geom.Rectangle2D;
import java.lang.Math;

import processing.core.PApplet;
import processing.core.PConstants;
//import processing.core.PGraphics;
import processing.core.PVector;

//import static processing.core.PApplet.


public class Region {
	protected final Rectangle2D rect;
	protected PVector center;
	protected final int id;
	
	public Region(double top, double bottom, double left, double right) {
		this(top,bottom,left,right,-1);		
	}
	
	public Region(double top, double bottom, double left, double right, int id) {
		rect = new Rectangle2D.Double(left,top,(right-left),(bottom-top)); 
		this.id = id;
		center = center();
	}
	
	public boolean inside(PVector pos) {
		return rect.contains(pos.x, pos.y);
	}
	
	public boolean inside(float x, float y) {
		return rect.contains(x,y);
	}
	
	public double top() { return rect.getMinY(); }
	
	public double bottom() {return rect.getMaxY(); }
	
	public double left() { return rect.getMinX(); }
	
	public double right() { return rect.getMaxX(); }
	
	public double width() { return rect.getWidth(); }
	
	public double height() { return rect.getHeight(); }
	
	private PVector center() { 
		return new PVector((float)rect.getCenterX(), (float)rect.getCenterY());
	};
	
	public PVector getRandomPosition() {
		double x = Math.random() * width() + left();
		double y = Math.random() * height() + top();
		PVector pt = new PVector((float)x,(float)y);
		
		assert inside(pt) : "random point is not inside the region?!?";
		
		return pt;
	}
	
	public void draw(boolean showId) {
		PApplet pa = DemoApp.ctx;
		pa.pushStyle();
		pa.stroke(50,200,50); // dark green
		pa.noFill();
		pa.rectMode(PConstants.CORNER);
		pa.rect((float)left(), (float)top(), (float)width(), (float)height());
		
		if(showId) {
			pa.textAlign(PConstants.CENTER, PConstants.CENTER);
			pa.text(String.format("%d", id), (float)center.x, (float)center.y);
		}
	}
	
	public void draw() {
		draw(false);
	}
	
}
