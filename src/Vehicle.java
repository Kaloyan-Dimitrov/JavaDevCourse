public class Vehicle {
    private int currentVelocity;
    private int currentDir;

    private String name;
    private String size;

    public Vehicle(String name, String size) {
        this.name = name;
        this.size = size;
        this.currentVelocity = 0;
        this.currentDir = 0;
    }

    public void steer(int direction) {
        this.currentDir += direction;
        System.out.println("Vehicle.steer(): Steering at " + currentDir + " degrees.");
    }

    public void move(int velocity, int direction) {
        this.currentVelocity = velocity;
        this.currentDir = direction;
        System.out.println("Vehicle.move(): Movign at " + currentVelocity + " in direction " + currentDir);
    }

    public String getName() {
        return name;
    }

    public String getSize() {
        return size;
    }

    public int getCurrentVelocity() {
        return currentVelocity;
    }

    public int getCurrentDir() {
        return currentDir;
    }

    public void stop() {
        this.currentVelocity = 0;
    }
}
