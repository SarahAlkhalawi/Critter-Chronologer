package com.udacity.jdnd.course3.critter.service;

import com.udacity.jdnd.course3.critter.entity.Customer;
import com.udacity.jdnd.course3.critter.entity.Pet;
import com.udacity.jdnd.course3.critter.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class CustomerService {

    @Autowired
    private CustomerRepository repository;

    public Long create(Customer customer){
        return repository.save(customer).getId();
    }

    public List<Customer> getAllCustomer(){
        return repository.findAll();
    }

    public Customer GetOwnerByPet(Long petID){
        return repository.findByPets_Id(petID);
    }

    public Customer findCustomerById(Long cId){
        Optional<Customer> customerOptional = repository.findById(cId);
        if (! customerOptional.isPresent()){
            return null;
        }
        return customerOptional.get();
    }


}
