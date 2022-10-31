import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;

public class Lambdas {
    public static void main(String[] args) {
        Employee john = new Employee("John Wick", 23);
        Employee jack = new Employee("Jack Daniels", 34);
        Employee snow = new Employee("Snow White", 56);
        Employee tyler = new Employee("Tyler the Creator", 83);

        List<Employee> employees = new ArrayList<>(List.of(new Employee[]{snow, jack, john, tyler}));

        Collections.sort(employees, (e1, e2) -> e1.getName().compareTo(e2.getName()));

//        for(Employee e: employees) {
//            System.out.println(e.getName());
//        }
        Function<Employee, String> getLastName = (Employee employee) -> employee.getName().substring(employee.getName().indexOf(' ') + 1);

//        employees.forEach(employee -> {
//            String lastName = getLastName.apply(employee);
//            System.out.println(lastName);
//        });

        Department hr = new Department("Human Resources");
        hr.addEmployee(jack);
        hr.addEmployee(john);
        hr.addEmployee(snow);
        Department accounting = new Department("Accounting");
        accounting.addEmployee(john);

        List<Department> departments = new ArrayList<>();
        departments.add(hr);
        departments.add(accounting);

        departments.stream()
                .flatMap(department -> department.getEmployees().stream())
                .forEach(System.out::println);
    }
}
class Employee {
    private String name;
    private int age;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Employee(String name, int age) {
        this.name = name;
        this.age = age;
    }

    @Override
    public String toString() {
        return name;
    }
}

class Department {
    private String name;
    private List<Employee> employees;

    public Department(String name) {
        this.name = name;
        this.employees = new ArrayList<>();
    }

    public void addEmployee(Employee employee) {
        employees.add(employee);
    }

    public List<Employee> getEmployees() {
        return employees;
    }
}

