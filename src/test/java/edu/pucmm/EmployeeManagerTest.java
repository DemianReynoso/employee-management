package edu.pucmm;


import edu.pucmm.exception.DuplicateEmployeeException;
import edu.pucmm.exception.EmployeeNotFoundException;
import edu.pucmm.exception.InvalidSalaryException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author me@fredpena.dev
 * @created 02/06/2024  - 00:47
 */

public class EmployeeManagerTest {

    private EmployeeManager employeeManager;
    private Position juniorDeveloper;
    private Position seniorDeveloper;
    private Employee employee1;
    private Employee employee2;

    @BeforeEach
    public void setUp() {
        employeeManager = new EmployeeManager();
        juniorDeveloper = new Position("1", "Junior Developer", 30000, 50000);
        seniorDeveloper = new Position("2", "Senior Developer", 60000, 90000);
        employee1 = new Employee("1", "John Doe", juniorDeveloper, 40000);
        employee2 = new Employee("2", "Jane Smith", seniorDeveloper, 70000);
        employeeManager.addEmployee(employee1);
    }

    @Test
    public void testAddEmployee() {
        // TODO: Agregar employee2 al employeeManager y verificar que se agregó correctamente.
        employeeManager.addEmployee(employee2);
        // - Verificar que el número total de empleados ahora es 2.
        assert(employeeManager.getEmployees().size() == 2);
        // - Verificar que employee2 está en la lista de empleados.
        assertTrue(employeeManager.getEmployees().contains(employee2));
    }

    @Test
    public void testRemoveEmployee() {
        // TODO: Eliminar employee1 del employeeManager y verificar que se eliminó correctamente.
        // - Agregar employee2 al employeeManager.
        employeeManager.addEmployee(employee2);
        // - Eliminar employee1 del employeeManager.
        employeeManager.removeEmployee(employee1);
        // - Verificar que el número total de empleados ahora es 1.
        assert(employeeManager.getEmployees().size() == 1);
        // - Verificar que employee1 ya no está en la lista de empleados.
        assertFalse(employeeManager.getEmployees().contains(employee1));
    }

    @Test
    public void testCalculateTotalSalary() {
        // TODO: Agregar employee2 al employeeManager y verificar el cálculo del salario total.
        // - Agregar employee2 al employeeManager.
        employeeManager.addEmployee(employee2);
        // - Verificar que el salario total es la suma de los salarios de employee1 y employee2.
        double totalSalary = employeeManager.calculateTotalSalary();
        assert(totalSalary == employee1.getSalary() + employee2.getSalary());
    }

    @Test
    public void testUpdateEmployeeSalaryValid() {
        // TODO: Actualizar el salario de employee1 a una cantidad válida y verificar la actualización.
        // - Actualizar el salario de employee1 a 45000.
        employeeManager.updateEmployeeSalary(employee1, 45000);
        // - Verificar que el salario de employee1 ahora es 45000.
        assert(employee1.getSalary() == 45000);
        assertTrue(true);
    }

    @Test
    public void testUpdateEmployeeSalaryInvalid() {
        // TODO: Intentar actualizar el salario de employee1 a una cantidad inválida y verificar la excepción.
        // - Intentar actualizar el salario de employee1 a 60000 (que está fuera del rango para Junior Developer).
        try {
            employeeManager.updateEmployeeSalary(employee1, 60000);
        } catch (InvalidSalaryException e) {
            assertTrue(true);
            return;
        }

        try {
            employeeManager.updateEmployeeSalary(employee1, 60000);
        } catch (InvalidSalaryException e) {
            // Verificar que se lanza la excepción.
            assertTrue(true);
            return;
        }
        // - Verificar que se lanza una InvalidSalaryException.
        fail("Expected InvalidSalaryException to be thrown");
    }

    @Test
    public void testUpdateEmployeeSalaryEmployeeNotFound() {
        // TODO: Intentar actualizar el salario de employee2 (no agregado al manager) y verificar la excepción.
        // - Intentar actualizar el salario de employee2 a 70000.
        try {
            employeeManager.updateEmployeeSalary(employee2, 70000);
        } catch (EmployeeNotFoundException e) {
            // Verificar que se lanza la excepción.
            assertTrue(true);
            return;
        }
        // - Verificar que se lanza una EmployeeNotFoundException.
        fail("Expected EmployeeNotFoundException to be thrown");
    }

    @Test
    public void testUpdateEmployeePositionValid() {
        // TODO: Actualizar la posición de employee2 a una posición válida y verificar la actualización.
        // - Agregar employee2 al employeeManager.
        employeeManager.addEmployee(employee2);
        // - Actualizar la posición de employee2 a seniorDeveloper.
        employeeManager.updateEmployeePosition(employee2, seniorDeveloper);
        // - Verificar que la posición de employee2 ahora es seniorDeveloper.
        assertEquals(employee2.getPosition(), seniorDeveloper);
    }

    @Test
    public void testUpdateEmployeePositionInvalidDueToSalary() {
        // TODO: Intentar actualizar la posición de employee1 a seniorDeveloper y verificar la excepción.
        // - Intentar actualizar la posición de employee1 a seniorDeveloper.
        try{
            employeeManager.updateEmployeePosition(employee1, seniorDeveloper);
        } catch (InvalidSalaryException e) {
            assertTrue(true);
            return;
        }
        // - Verificar que se lanza una InvalidSalaryException porque el salario de employee1 no está dentro del rango para Senior Developer.
        fail("Expected InvalidSalaryException to be thrown");
    }

    @Test
    public void testUpdateEmployeePositionEmployeeNotFound() {
        // TODO: Intentar actualizar la posición de employee2 (no agregado al manager) y verificar la excepción.
        // - Intentar actualizar la posición de employee2 a juniorDeveloper.
        try {
            employeeManager.updateEmployeePosition(employee2, juniorDeveloper);
        } catch (EmployeeNotFoundException e) {
            assertTrue(true);
            return;
        }
        // - Verificar que se lanza una EmployeeNotFoundException.
        fail("Expected EmployeeNotFoundException to be thrown");;
    }

    @Test
    public void testIsSalaryValidForPosition() {
        // TODO: Verificar la lógica de validación de salario para diferentes posiciones.
        // - Verificar que un salario de 40000 es válido para juniorDeveloper.
        assertTrue(employeeManager.isSalaryValidForPosition(juniorDeveloper, 40000));
        // - Verificar que un salario de 60000 no es válido para juniorDeveloper.
        assertFalse(employeeManager.isSalaryValidForPosition(juniorDeveloper, 60000));
        // - Verificar que un salario de 70000 es válido para seniorDeveloper.
        assertTrue(employeeManager.isSalaryValidForPosition(seniorDeveloper, 70000));
        // - Verificar que un salario de 50000 no es válido para seniorDeveloper.
        assertFalse(employeeManager.isSalaryValidForPosition(seniorDeveloper, 50000));
    }

    @Test
    public void testAddEmployeeWithInvalidSalary() {
        // TODO: Intentar agregar empleados con salarios inválidos y verificar las excepciones.
        // - Crear un empleado con un salario de 60000 para juniorDeveloper.
        try {
            Employee invalidJunior = new Employee("3", "Invalid Junior", juniorDeveloper, 60000);
            employeeManager.addEmployee(invalidJunior);
        } catch (InvalidSalaryException e) {
            assertTrue(true);
            return;
        }
        // - Verificar que se lanza una InvalidSalaryException al agregar este empleado.
        fail("Expected InvalidSalaryException to be thrown");
        // - Crear otro empleado con un salario de 40000 para seniorDeveloper.
        try {
            Employee invalidSenior = new Employee("4", "Invalid Senior", seniorDeveloper, 40000);
            employeeManager.addEmployee(invalidSenior);
        } catch (InvalidSalaryException e) {
            assertTrue(true);
            return;
        }
        // - Verificar que se lanza una InvalidSalaryException al agregar este empleado.
        fail("Expected InvalidSalaryException to be thrown");
    }

    @Test
    public void testRemoveExistentEmployee() {
        // TODO: Eliminar un empleado existente y verificar que no se lanza una excepción.
        // - Eliminar employee1 del employeeManager.
        try{
            employeeManager.removeEmployee(employee1);
        } catch (EmployeeNotFoundException e) {
            fail("Expected no exception to be thrown");
            return;
        }
        // - Verificar que no se lanza ninguna excepción.
        assertTrue(true, "Expected no exception to be thrown");
    }

    @Test
    public void testRemoveNonExistentEmployee() {
        // TODO: Intentar eliminar un empleado no existente y verificar la excepción.
        // - Intentar eliminar employee2 (no agregado al manager).
        try {
            employeeManager.removeEmployee(employee2);
        } catch (EmployeeNotFoundException e) {
            assertTrue(true);
            return;
        }
        // - Verificar que se lanza una EmployeeNotFoundException.
        fail("Expected EmployeeNotFoundException to be thrown");
    }

    @Test
    public void testAddDuplicateEmployee() {
        // TODO: Intentar agregar un empleado duplicado y verificar la excepción.
        // - Intentar agregar employee1 nuevamente al employeeManager.
        try {
            employeeManager.addEmployee(employee1);
        } catch (DuplicateEmployeeException e) {
            assertTrue(true);
            return;
        }
        // - Verificar que se lanza una DuplicateEmployeeException.
        fail("Expected DuplicateEmployeeException to be thrown");
    }
    @Test
     public void testAddEmployeesWithSameNameFail(){
        // TODO: Intentar agregar empleados con el mismo nombre y verificar la excepción.
        // - Crear un nuevo empleado con el mismo nombre que employee1 pero diferente ID.
        Employee duplicateNameEmployee = new Employee("3", "John Doe", juniorDeveloper, 45000);
        try {
            employeeManager.addEmployee(duplicateNameEmployee);
        } catch (DuplicateEmployeeException e) {
            assertTrue(true);
            return;
        }
        // - Verificar que se lanza una DuplicateEmployeeException.
        fail("Expected DuplicateEmployeeException to be thrown");
    }

    @Test
    public void testAddNullPositionEmployeeFail(){
        // TODO: Intentar agregar un empleado con posición nula y verificar la excepción.
        // - Crear un nuevo empleado con posición nula.
        try{
            Employee nullPositionEmployee = new Employee("4", "Null Position", null, 40000);
            employeeManager.addEmployee(nullPositionEmployee);
        } catch (IllegalArgumentException e) {
            assertTrue(true);
            return;
        }
        // - Verificar que se lanza una IllegalArgumentException.
        fail("Expected IllegalArgumentException to be thrown");
    }

    @Test
    public void testAddNegativeSalaryEmployeeFail(){
        // TODO: Intentar agregar un empleado con salario negativo y verificar la excepción.
        // - Crear un nuevo empleado con un salario negativo.
        try {
            Employee negativeSalaryEmployee = new Employee("5", "Negative Salary", juniorDeveloper, -1000);
            employeeManager.addEmployee(negativeSalaryEmployee);
        } catch (IllegalArgumentException e) {
            assertTrue(true);
            return;
        }
        // - Verificar que se lanza una IllegalArgumentException.
        fail("Expected IllegalArgumentException to be thrown");
    }
}
