import java.util.Scanner;

public class Student {
    private String name;
    private double average;

    public Student(String name, double average) {
        this.name = name;
        this.average = average;
    }

    public Student() {
        Scanner sc = new Scanner(System.in);
        this.name = sc.next();
        this.average = sc.nextDouble();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getAverage() {
        return average;
    }

    public void setAverage(double average) {
        this.average = average;
    }

    public boolean scholarship() {
        return this.average > 5.5;
    }
}
