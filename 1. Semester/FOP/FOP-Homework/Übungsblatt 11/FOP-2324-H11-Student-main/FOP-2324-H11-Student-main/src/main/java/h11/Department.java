package h11;

import org.tudalgo.algoutils.student.annotation.DoNotTouch;
import org.tudalgo.algoutils.student.annotation.StudentImplementationRequired;
import static org.tudalgo.algoutils.student.Student.crash;
import java.util.List;

/**
 * A {@link Department} manages a list of {@link Employee}s and provides methods to query information about them.
 *
 * @param employees The employees of this department.
 */
public record Department(@DoNotTouch List<Employee> employees) {

    /**
     * Returns the employees of this department.
     *
     * @return the employees
     */
    @Override
    public List<Employee> employees() {
        return this.employees;
    }

    /**
     * Gets list of positions in department.
     *
     * @return the list of positions in department
     */
    @StudentImplementationRequired
    public List<Position> getListOfPositionsInDepartment() {
        //H1.1
        var list = this.employees.stream().map(employee -> employee.getPosition()).distinct().toList();
        return list;
    }

    /**
     * Filter employee by position list.
     *
     * @param position the position
     * @return the list
     */
    @StudentImplementationRequired
    public List<Employee> filterEmployeeByPosition(Position position) {
        //H1.2
        var list = this.employees.stream().filter(employee -> employee.getPosition().equals(position)).toList();
        return list;
    }

    /**
     * Gets number of employees by salary.
     *
     * @param salary the salary
     * @return the number of employees by salary
     */
    @StudentImplementationRequired
    public long getNumberOfEmployeesBySalary(double salary) {
        //H1.3
        var count = this.employees.stream().filter(employee -> employee.getSalary() >= salary).count();
        return count;
    }

    /**
     * Adjust salary.
     *
     * @param amount   the amount
     * @param increase the increase
     */
    @StudentImplementationRequired
    public void adjustSalary(double amount, boolean increase) {
        //H1.4
        this.employees.stream().forEach(employee -> employee.setSalary(increase ? employee.getSalary()+amount : employee.getSalary()-amount));
    }
}
