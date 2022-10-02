public class Dog extends Animal {
    private int eyes;
    private int teeth;
    private String coat;
    private int tail;
    private int legs;

    public Dog(int size, int weight, String name, int eyes, int teeth, String coat, int tail, int legs) {
        super(1, 1, size, weight, name);
        this.eyes = eyes;
        this.teeth = teeth;
        this.coat = coat;
        this.tail = tail;
        this.legs = legs;
    }
}
