public class Main {
    public static void main(String[] args) {
        MyLinkedList list = new MyLinkedList(null);
        list.addItem("b");
        list.addItem("f");
        list.addItem("u");
        list.addItem("y");

        list.addItem("a");
        list.addItem("d");
        list.addItem("m");
        list.addItem("w");
        list.addItem("z");

        list.removeItem(new Node("y"));
        list.removeItem(new Node("z"));
        list.removeItem(new Node("a"));

        list.traverse(list.getRoot());
    }
}