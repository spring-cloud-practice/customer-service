package com.sudhirt.practice.customerservice.controller;

import com.sudhirt.practice.customerservice.domain.Address;
import com.sudhirt.practice.customerservice.domain.Customer;
import com.sudhirt.practice.customerservice.exception.NotFoundException;
import com.sudhirt.practice.customerservice.repository.AddressRepository;
import com.sudhirt.practice.customerservice.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
public class CustomerController {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private AddressRepository addressRepository;

    @GetMapping("/customers")
    public List<Customer> getAll() {
        return customerRepository.findAll();
    }

    @GetMapping("/customers/{id}")
    public Customer get(@PathVariable(name = "id", required = true) String id) {
        Optional<Customer> customer = customerRepository.findOne(Example.of(Customer.builder().id(id).build()));
        return customer.map(c -> {
            return c;
        }).orElseThrow(NotFoundException::new);
    }

    @GetMapping("/customers/{customerId}/addresses")
    public List<Address> getCustomerAddresses(@PathVariable(name = "customerId", required = true) String customerId) {
        List<Address> addresses = addressRepository.findAll(
                Example.of(
                        Address.builder()
                                .customer(Customer.builder()
                                        .id(customerId)
                                        .build())
                                .build()));
        return addresses;
    }

    @GetMapping("/customers/{customerId}/addresses/{addressId}")
    public Address getCustomerAddress(@PathVariable(name = "customerId", required = true) String customerId,
                                      @PathVariable(name = "addressId", required = true) String addressId) {
        //Optional<Address> addressHolder = addressRepository.findByIdAndCustomer(addressId, Example.of(Customer.builder().id(customerId).build()));
        Optional<Address> addressHolder = addressRepository.findByIdAndCustomer(addressId, Customer.builder().id(customerId).build());
        return addressHolder.map(address -> {
            return address;
        }).orElseThrow(NotFoundException::new);
    }
}
