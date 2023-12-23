package javaPractice.classesAndObjects;

/*
 *  Objects are structures which contain data in the form of fields and methods.
 * */
//This class is made to be a general representation.
public class Rectangle {

    private double length;
    private double width;

    /**
     * the default constructor
     * there's no return type, because they're not a method.
     * The default constructor is known as such because it does not have a parameter list.
     * This means if someone wanted to create a Rectangle object and they did not want to set the length or width just yet,
     * then they can use this constructor without passing anything.
     * And the default constructor is typically used to assign default values to the fields.
     */
    public Rectangle() {
        length = 0;
        width = 0;
    }

    /**
     * The "this" keyword refers to this class.
     * It's essentially saying to take the value of our local variable length and assign it to our global one.
     */

    //pass us a length and pass us a width, and we'll set those values
    public Rectangle(double length, double width) {
        this.length = length; //can be set this way
        setWidth(width); //or can be set this way. these are only different here to demo alternative options
    }

    /*Getter and Setter methods
     *   We can create a method that will allow anyone who wants to use a Rectangle object
     *   to set the length or get it
     *   if they want to read it, as well as the width.
     *
     * get = we're just going to return width
     * set = we'll say this class's of width should be equal to the width that was just passed to us.because of the scope.
     * */
    public void setLength(double length) {
        this.length = length;
    }

    public double getLength() {
        return length;
    }

    public void setWidth(double width) {
        this.width = width;
    }

    public double getWidth() {
        return width;
    }

    // Perimeter of rectangle
    public double calculatePerimeter() {
        return (2 * length) + (2 * width);
    }

    // Area of rectangle
    public double calculateArea() {
        return length * width;
    }
}