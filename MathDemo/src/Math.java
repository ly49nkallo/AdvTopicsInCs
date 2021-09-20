import processing.core.PApplet;
import processing.core.PVector;

//import static processing.core.PApplet.*;
public class Math extends PApplet{
    public static void main(String[] args) {
        PApplet.main(Math.class);
        //System.out.println(10-pythagorean(6,3));
    }

    @Override
    public void settings() {
        size(300,300);
    }
    @Override
    public void setup() {
        a = new PVector(10,-2);
        b = new PVector(10,2);
        a.normalize(); b.normalize();
        float c = a.dot(b);
        println(c);

    }
    @Override
    public void draw() {
        background(255);
    }
    PVector a, b;
}
