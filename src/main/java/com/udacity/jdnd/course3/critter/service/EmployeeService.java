package com.udacity.jdnd.course3.critter.service;

import com.udacity.jdnd.course3.critter.entity.Employee;
import com.udacity.jdnd.course3.critter.repository.EmployeeRepository;
import com.udacity.jdnd.course3.critter.repository.ScheduleRepository;
import com.udacity.jdnd.course3.critter.user.EmployeeSkill;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.DayOfWeek;
import java.util.*;

@Service
@Transactional
public class EmployeeService {

    @Autowired
    private EmployeeRepository repository;
    @Autowired
    private ScheduleRepository scheduleRepository;

    public Long createEmployee(Employee employee){
        return repository.save(employee).getId();
    }

    public Employee addEmployeeSchedule(Set<DayOfWeek> dayOfWeekSet, Long employeeId){
        Optional<Employee> employeeOptional = repository.findById(employeeId);
        if (!employeeOptional.isPresent()){
            return null;
        }
        Employee employee = employeeOptional.get();
        employee.setDaysAvailable(dayOfWeekSet);
        repository.save(employee);
        return employee;
    }


    public List<Employee> checkAvailability(Set<EmployeeSkill> activities, DayOfWeek date){
        List<Employee> employees = repository.getAllByDaysAvailableContains(date);
        List<Employee> employeeAvailables = new ArrayList<>();
        for (Employee employee: employees){
            if (employee.getSkills().containsAll(activities)){
                employeeAvailables.add(employee);
            }
        }
        return employeeAvailables;
    }

    public Employee getById(Long id){
        Optional<Employee> employeeOptional = repository.findById(id);
        if (! employeeOptional.isPresent()){
            return null;
        }
        return employeeOptional.get();
    }


}
