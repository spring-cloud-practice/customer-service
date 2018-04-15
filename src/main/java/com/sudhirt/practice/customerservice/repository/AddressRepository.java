package com.sudhirt.practice.customerservice.repository;

import com.sudhirt.practice.customerservice.domain.Address;
import com.sudhirt.practice.customerservice.domain.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AddressRepository extends JpaRepository<Address, String> {

    Optional<Address> findByIdAndCustomer(String id, Customer customer);
}
