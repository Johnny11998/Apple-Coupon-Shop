package com.Project.Project.AppleCouponShop.Services;

import com.Project.Project.AppleCouponShop.Exceptions.MajorException;
import com.Project.Project.AppleCouponShop.Repositories.CustomerRepo;
import com.Project.Project.AppleCouponShop.beans.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GuestService {

    @Autowired
    CustomerRepo customerRepo;

    public Customer register(Customer customer) throws MajorException {
        for (Customer item : customerRepo.findAll()) {
            if (customer.getEmail().equals(item.getEmail())) {
                throw new MajorException("Customer " + customer.getFirstName() + " " + customer.getLastName() + " already exists !");
            }
        }
        customerRepo.save(customer);
        System.out.println("Customer " + customer.getFirstName() + " " + customer.getLastName() + " added to the system");
        return customerRepo.findCustomerByEmailAndPassword(customer.getEmail(), customer.getFirstName());

    }
}
