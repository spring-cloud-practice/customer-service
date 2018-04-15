package com.sudhirt.practice.customerservice;

import com.sudhirt.practice.customerservice.repository.CustomerRepository;
import com.sudhirt.practice.customerservice.domain.Customer;
import com.sudhirt.practice.customerservice.repository.AddressRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import javax.transaction.Transactional;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class CustomerServiceApplicationTests {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private AddressRepository addressRepository;

    private List<Customer> customers = null;

    @Before
    @Transactional
    public void load() throws Exception {
        if (customers == null) {
            customers = customerRepository.findAll();
            customers.stream().forEach(customer -> {
                customer.getAddresses();
            });
        }
    }

    @Test
    public void findAllCustomers() throws Exception {
        mvc.perform(get("/customers"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(customers.size())));
    }

    @Test
    public void getCustomerById() throws Exception {
        Customer customer = customers.get(15);
        mvc.perform(get("/customers/" + customer.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(customer.getId())));
    }

    @Test
    @Transactional
    public void getCustomerAddresses() throws Exception {
        Customer customer = customers.get(10);
        mvc.perform(get("/customers/" + customer.getId() + "/addresses"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(customer.getAddresses().size())));
    }

    @Test
    @Transactional
    public void getAddressByCustomerIdAndAddressId() throws Exception {
        Customer customer = customers.get(20);
        mvc.perform(get("/customers/" + customer.getId() + "/addresses/" + customer.getAddresses().get(0).getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(customer.getAddresses().get(0).getId())));
        //Optional<Address> addressHolder = addressRepository.findByIdAndCustomer(customer.getId(), Example.of(Customer.builder().id(customer.getAddresses().get(0).getId()).build()));
        //Optional<Address> addressHolder = addressRepository.findByIdAndCustomer(customer.getAddresses().get(0).getId(), Customer.builder().id(customer.getId()).build());
        //assertNotNull(addressHolder.get());
    }
}
