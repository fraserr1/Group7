public class Circle extends GeometricObject {
    private double x = 0.0, y = 0.0, radius = 1.0;

    /** Constructor */
    public Circle() {
    }

    /** Constructor */
    public Circle(double x, double y, double radius) {
        this.x = x;
        this.y = y;
        this.radius = radius;
    }
    /** Get x */
    public double getX() {
        return x;
    }
    /** Get y */
    public double getY() {
        return y;
    }
    /** Get radius */
    public double getRadius() {
        return radius;
    }

    /** Override method getArea in GeometricObject */
    public double getArea() {
        return Math.PI * Math.pow(radius, 2);
    }

    /** Override method getPerimeter */
    public double getPerimeter() {
        return 2 * Math.PI * radius;
    }
}
