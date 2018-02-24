public class Test {
    public static void main(String[]args){
        GeometricObject gObjectArray [] = new GeometricObject [5];

        /** Set the elements of the array */
        gObjectArray[0] = new Circle(5,5,5);
        gObjectArray[1] = new EquilateralTriangle(5);
        gObjectArray[2] = new Triangle(5,5,5);
        gObjectArray[3] = new Rectangle(5,5);
        gObjectArray[4] = new Square(5);
        /** Pass each element in the array to printAreaAndPerimeter */
        for (int i = 0; i < gObjectArray.length; i++)
            printAreaAndPerimeter(gObjectArray[i]);

    }

    /** Print the area and perimeter of the GeometricObject */
    private static void printAreaAndPerimeter(GeometricObject gObject){
        System.out.println("Area = " + gObject.getArea() + " Perimeter = " + gObject.getPerimeter());
    }
}
