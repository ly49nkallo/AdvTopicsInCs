import processing.core.PApplet;

public class Math extends PApplet{
    public static void main(String[] args) {
        //PApplet.main("Math");
        System.out.println(10-pythagorean(6,3));
    }

    @Override
    public void settings() {
        size(300,300);
    }
    @Override
    public void setup() {
        background(200);
    }
    @Override
    public void draw() {

    }
    static float pythagorean(int a, int b) {
        return sqrt((a^2) + (b^2));
    }
}
