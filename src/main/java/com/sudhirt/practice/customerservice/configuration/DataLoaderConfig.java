package com.sudhirt.practice.customerservice.configuration;

import com.sudhirt.practice.customerservice.domain.Address;
import com.sudhirt.practice.customerservice.domain.Customer;
import com.sudhirt.practice.customerservice.repository.CustomerRepository;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.util.*;
import java.util.stream.IntStream;

@Configuration
@Profile("!test")
public class DataLoaderConfig {

    @Bean
    ApplicationRunner init(CustomerRepository customerRepository) {
        return args -> customerRepository.saveAll(customers());
    }

    private Collection<Customer> customers() {
        List<Customer> customers = new ArrayList<>();
        Customer customer;
        final Random random = new Random();
        IntStream.range(0, 30).boxed().forEach(i -> {
            customers.add(
                    Customer.builder()
                            .firstName("FirstName_" + i)
                            .lastName("LastName_" + i)
                            .dateOfBirth(new Date())
                            .build());

            IntStream.range(0, random.nextInt(3)).boxed().forEach(r -> {
                customers.get(i).addAddress(
                        Address.builder()
                                .address1("AddressLine1_" + i + "_" + r)
                                .address2("AddressLine2_" + i + "_" + r)
                                .city("City" + i + "_" + r)
                                .state("State" + i + "_" + r)
                                .country("Country" + i + "_" + r)
                                .zip("123456" + i + r)
                                .build());
            });
        });
        return customers;
    }
}
