import processing.core.PApplet;
import processing.core.PVector;

public class Math extends PApplet{
    public static void main(String[] args) {
        PApplet.main("Math");
        //System.out.println(10-pythagorean(6,3));
    }

    @Override
    public void settings() {
        size(300,300);
    }
    @Override
    public void setup() {
        PVector ptT = new PVector(2,1);
        PVector ptA = new PVector(8,4);

        PVector d = PVector.sub(ptT, ptA);
        println(d.mag());
    }
    @Override
    public void draw() {

    }
    static float pythagorean(int a, int b) {
        return sqrt((a^2) + (b^2));
    }
}
