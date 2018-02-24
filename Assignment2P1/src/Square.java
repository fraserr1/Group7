public class Square extends Rectangle {
    private double side;

    /** Constructor */
    public Square(double side) {
        super(side, side);
    }

    /** Override method getArea in Rectangle */
    public double getArea() {
        return Math.pow(side, 2);
    }

    /** Override method getPerimeter */
    public double getPerimeter() {
        return 4 * side;
    }
}
