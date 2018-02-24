public class Rectangle extends GeometricObject {
    private double side1 = 1.0, side2 = 1.0;

    /** Constructor */
    public Rectangle() {
    }

    /** Constructor */
    public Rectangle(double side1, double side2) {
        this.side1 = side1;
        this.side2 = side2;
    }

    /** Override method getArea in GeometricObject */
    public double getArea() {
        return side1 * side2;
    }

    /** Override method getPerimeter */
    public double getPerimeter() {
        return 2 * (side1 + side2);
    }
}
