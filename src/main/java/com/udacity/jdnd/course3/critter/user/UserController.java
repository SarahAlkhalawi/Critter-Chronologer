package com.udacity.jdnd.course3.critter.user;

import com.udacity.jdnd.course3.critter.entity.Customer;
import com.udacity.jdnd.course3.critter.entity.Employee;
import com.udacity.jdnd.course3.critter.entity.Pet;
import com.udacity.jdnd.course3.critter.service.CustomerService;
import com.udacity.jdnd.course3.critter.service.EmployeeService;
import com.udacity.jdnd.course3.critter.service.PetService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Handles web requests related to Users.
 *
 * Includes requests for both customers and employees. Splitting this into separate user and customer controllers
 * would be fine too, though that is not part of the required scope for this class.
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private CustomerService customerService;

    @Autowired
    private PetService petService;

    @Autowired
    private EmployeeService employeeService;

    @PostMapping("/customer")
    public CustomerDTO saveCustomer(@RequestBody CustomerDTO customerDTO){
        Customer customer = convertCustomerDTOToCustomer(customerDTO);
        customerDTO.setId(customerService.create(customer));
        return customerDTO;
//        throw new UnsupportedOperationException();
    }

    private Customer convertCustomerDTOToCustomer(CustomerDTO customerDTO){
        Customer customer = new Customer();
        BeanUtils.copyProperties(customerDTO,customer);
        List<Long> petsID = customerDTO.getPetIds();
        List<Pet> pets = new ArrayList<>();
        if (petsID != null){
            for (Long id : petsID){
                pets.add(petService.getPetbyId(id));
            }
            customer.setPets(pets);
        }
        return customer;
    }

    @GetMapping("/customer")
    public List<CustomerDTO> getAllCustomers(){
        List<CustomerDTO> customerDTOS = new ArrayList<>();
        List<Customer> customerList= customerService.getAllCustomer();
        for (Customer customer: customerList){
            customerDTOS.add(convertCustomerToCustomerDTO(customer));
        }
        return customerDTOS;
//        throw new UnsupportedOperationException();
    }

    private CustomerDTO convertCustomerToCustomerDTO(Customer customer){
        CustomerDTO customerDTO = new CustomerDTO();
        BeanUtils.copyProperties(customer, customerDTO);
        List<Long> petsID = new ArrayList<>();
        List<Pet> pets = customer.getPets();
        if (pets != null){
            for (Pet p : pets){
                petsID.add(p.getId());
            }
            customerDTO.setPetIds(petsID);
        }
        return customerDTO;
    }

    @GetMapping("/customer/pet/{petId}")
    public CustomerDTO getOwnerByPet(@PathVariable long petId){
        return convertCustomerToCustomerDTO(customerService.GetOwnerByPet(petId));
//        throw new UnsupportedOperationException();
    }

    @PostMapping("/employee")
    public EmployeeDTO saveEmployee(@RequestBody EmployeeDTO employeeDTO) {
        Employee employee = convertEmployeeDTOToEmployee(employeeDTO);
        employeeDTO.setId(employeeService.createEmployee(employee));
        return employeeDTO;
//        throw new UnsupportedOperationException();
    }

    private Employee convertEmployeeDTOToEmployee(EmployeeDTO employeeDTO){
        Employee employee = new Employee();
        BeanUtils.copyProperties(employeeDTO,employee);
        employee.setId(employeeDTO.getId());
        return employee;
    }

    @PostMapping("/employee/{employeeId}")
    public EmployeeDTO getEmployee(@PathVariable long employeeId) {
        return convertEmployeeToEmployeeDTO(employeeService.getById(employeeId));
//        throw new UnsupportedOperationException();
    }

    private EmployeeDTO convertEmployeeToEmployeeDTO(Employee employee){
        EmployeeDTO employeeDTO = new EmployeeDTO();
        BeanUtils.copyProperties(employee, employeeDTO);
        employeeDTO.setId(employee.getId());
        return employeeDTO;
    }

    @PutMapping("/employee/{employeeId}")
    public void setAvailability(@RequestBody Set<DayOfWeek> daysAvailable, @PathVariable long employeeId) {
        employeeService.addEmployeeSchedule(daysAvailable,employeeId);
//        throw new UnsupportedOperationException();
    }

    @GetMapping("/employee/availability")
    public List<EmployeeDTO> findEmployeesForService(@RequestBody EmployeeRequestDTO employeeDTO) {
        List<Employee> employees = employeeService.checkAvailability(employeeDTO.getSkills()
                ,employeeDTO.getDate().getDayOfWeek());
        List<EmployeeDTO> employeeDTOList = new ArrayList<>();
        if (employees!= null){
            for (Employee employee: employees){
                employeeDTOList.add(convertEmployeeToEmployeeDTO(employee));
            }
        }
        return employeeDTOList;

//        throw new UnsupportedOperationException();
    }

}
