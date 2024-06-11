package javaPractice.topics.oop.interfaceClass.interface1;

public class CD implements RetailItem, Displayable {
    double price;
    String title;
    String artist;

    public CD(double price, String title, String artist) {
        this.price = price;
        this.title = title;
        this.artist = artist;
    }

    public CD() {

    }

    @Override
    public double getItemPrice() {
        return price;
    }

    public void setTitle(String title) {
        this.title = title;

    }

    @Override
    public void display() {
        System.out.println("Store = " + storeName + "Title = " + title + " Price =" + price + "Artist =" + artist);
    }
}
