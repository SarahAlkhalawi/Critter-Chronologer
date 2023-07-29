package com.udacity.jdnd.course3.critter.service;

import com.udacity.jdnd.course3.critter.entity.Customer;
import com.udacity.jdnd.course3.critter.entity.Pet;
import com.udacity.jdnd.course3.critter.entity.Schedule;
import com.udacity.jdnd.course3.critter.repository.CustomerRepository;
import com.udacity.jdnd.course3.critter.repository.ScheduleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
@Transactional
public class ScheduleService {
    @Autowired
    private ScheduleRepository repository;

    @Autowired
    private CustomerRepository customerRepository;

    public Long createSchedule(Schedule schedule){
        return repository.save(schedule).getId();
    }

    public List<Schedule> FindScheduleByPet(Long petId){
        return repository.findByPets_Id(petId);
    }

    public List<Schedule> FindScheduleByEmployee(Long id){
        return repository.findByEmployees_Id(id);
    }

//    public List<Schedule> FindScheduleByCustomer(Long cId){
//        List<Schedule> scheduleList = new ArrayList<>();
//        Optional<Customer> customerOptional = customerRepository.findById(cId);
//        if (! customerOptional.isPresent()){
//            return null;
//        }
//        Customer customer= customerOptional.get();
//        List<Pet> pets = customer.getPets();
//        if (pets!=null){
//            for (Pet p: pets){
//                scheduleList.addAll(repository.findByPets_Id(p.getId()));
//            }
//        }
//        return scheduleList;
//    }

    public List<Schedule> findScheduleByCustomer(Long cId){
        Optional<Customer> customerOptional = customerRepository.findById(cId);
        if (! customerOptional.isPresent()){
            return null;
        }
        Customer customer= customerOptional.get();
        List<Schedule> scheduleList = repository.findByPetsIn(customer.getPets());
        return scheduleList;
    }

    public List<Schedule> getAll(){
        return repository.findAll();
    }

}
