package javaPractice.oop.animals;

public class Zoo {
    public static void main(String[] args) {

        Dog rocky = new Dog();

        rocky.fetch();
        rocky.makeSound();
        feed(rocky);

        Animal sasha = new Dog();

        sasha.makeSound();
        feed(sasha);

        sasha = new Cat();

        sasha.makeSound();
        // Casting is the act of converting an object’s type into a different type.
        ((Cat) sasha).scratch();
        feed(sasha);
    }

    public static void feed(Animal animal) {

        // instanceof will do a check to see if whatever's on the left side is actually an instance of whatever you specify on the right side.
        if (animal instanceof Dog) {
            System.out.println("here's your dog food");
        } else if (animal instanceof Cat) {
            System.out.println("here's your cat food");
        }
    }
}