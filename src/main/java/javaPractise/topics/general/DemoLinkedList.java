package javaPractise.topics.general;

import java.util.LinkedList;

public class DemoLinkedList {
    public static void main(String[] args) {

        LinkedList<String> linkedListObject = new LinkedList<String>();

        linkedListObject.add("Item 1");
        linkedListObject.add("Item 2");
        linkedListObject.add("Item 3");
        linkedListObject.add("Item 4");
        linkedListObject.add("Item 5");
        System.out.println("LinkedList content: " + linkedListObject);

        // Out put --> LinkedList content: [Item 1, Item 2, Item 3, Item 4, Item 5]

        linkedListObject.addFirst("first item");
        linkedListObject.addLast("last item");
        System.out.println("LinkedList content after adding new items" + linkedListObject);

        // Out put -->LinkedList content after adding new items[first item, Item 1, Item 2, Item 3, Item 4, Item 5, last item]

        Object firstVar = linkedListObject.get(0);
        System.out.println("First Element :" + firstVar);

        // Out put --> First Element :first item

        linkedListObject.set(0, "changed first item");
        Object secondVar = linkedListObject.get(0);
        System.out.println("First Element after updating by set method is :" + secondVar);
        System.out.println("linked list after set the value of first items" + linkedListObject);
        // Out put --> First Element after updating by set method is :changed first item

        linkedListObject.removeFirst();
        linkedListObject.removeLast();
        System.out.println("linked list after deleting the first and last items" + linkedListObject);

        linkedListObject.add(0, "newly added item");
        linkedListObject.remove(2);
        System.out.println("final contem=nt of linkedlist" + linkedListObject);


    }

}
