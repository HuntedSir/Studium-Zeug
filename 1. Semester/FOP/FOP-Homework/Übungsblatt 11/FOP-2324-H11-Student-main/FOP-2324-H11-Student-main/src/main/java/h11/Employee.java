package h11;

import org.tudalgo.algoutils.student.annotation.DoNotTouch;

/**
 * A {@link Employee} represents an Employee which has a Name, a salary and a position.
 */
@DoNotTouch
public class Employee {
    private final String NAME;
    private double salary;
    private Position position;

    public Employee(String name, Position position, double salary) {
        this.NAME = name;
        this.salary = salary;
        this.position = position;
    }

    public Position getPosition() {
        return this.position;
    }

    public double getSalary() {
        return this.salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public String getName() {
        return this.NAME;
    }
}
