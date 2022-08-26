import processing.core.PApplet;
// import processing.core.PVector;

public class App extends PApplet{
    public static void main(String[] args) {
        PApplet.main(App.class);
    }
    public static App ctx;
    public void settings() { 
        size(1000,800); 
    }
    public void setup() {
		ctx = this;
        stroke(255);
        background(192, 64, 0);
    }
    public void draw() {
        line(150, 25, mouseX, mouseY);
    }
}
