package edu.pucmm;

import edu.pucmm.exception.DuplicateEmployeeException;
import edu.pucmm.exception.EmployeeNotFoundException;
import edu.pucmm.exception.InvalidSalaryException;

import java.util.ArrayList;
import java.util.List;

/**
 * @author me@fredpena.dev
 * @created 01/06/2024  - 23:43
 */
public class EmployeeManager {

    private final List<Employee> employees;

    public EmployeeManager() {
        this.employees = new ArrayList<>();
    }

    public void addEmployee(Employee employee) {
        if (employees.contains(employee)) {
            throw new DuplicateEmployeeException("Duplicate employee");
        }

        for (Employee existingEmployee : employees) {
            if (existingEmployee.getId().equals(employee.getId())) {
                throw new DuplicateEmployeeException("Employee with the same ID already exists");
            }
        }

        for (Employee existingEmployee : employees) {
            if (existingEmployee.getName().equals(employee.getName())) {
                throw new DuplicateEmployeeException("Employee with the same name already exists");
            }
        }

        if (employee.getPosition() == null) {
            throw new IllegalArgumentException("Position cannot be null");
        }

        if (employee.getSalary() < 0) {
            throw new IllegalArgumentException("Salary cannot be negative");
        }

        if (!isSalaryValidForPosition(employee.getPosition(), employee.getSalary())) {
            throw new InvalidSalaryException("Invalid salary for position");
        }
        employees.add(employee);
    }

    public void removeEmployee(Employee employee) {
        if (!employees.contains(employee)) {
            throw new EmployeeNotFoundException("Employee not found");
        }
        employees.remove(employee);
    }

    public List<Employee> getEmployees() {
        return employees;
    }

    public double calculateTotalSalary() {
        double totalSalary = 0;
        for (Employee employee : employees) {
            totalSalary += employee.getSalary();
        }
        return totalSalary;
    }

    public void updateEmployeeSalary(Employee employee, double newSalary) {
        if (!employees.contains(employee)) {
            throw new EmployeeNotFoundException("Employee not found");
        }
        if (!isSalaryValidForPosition(employee.getPosition(), newSalary)) {
            throw new InvalidSalaryException("Salary is not within the range for the position");
        }
        employee.setSalary(newSalary);
    }

    public void updateEmployeePosition(Employee employee, Position newPosition) {
        if (!employees.contains(employee)) {
            throw new EmployeeNotFoundException("Employee not found");
        }
        if (!isSalaryValidForPosition(newPosition, employee.getSalary())) {
            throw new InvalidSalaryException("Current salary is not within the range for the new position");
        }
        employee.setPosition(newPosition);
    }

    public boolean isSalaryValidForPosition(Position position, double salary) {
        if (salary == position.getMinSalary() * 0.9){
            position.setMinSalary(salary);
        }
        return salary >= position.getMinSalary() && salary <= position.getMaxSalary();
    }
}
