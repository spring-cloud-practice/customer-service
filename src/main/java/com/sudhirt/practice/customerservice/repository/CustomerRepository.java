package com.sudhirt.practice.customerservice.repository;

import com.sudhirt.practice.customerservice.domain.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, String> {

}
