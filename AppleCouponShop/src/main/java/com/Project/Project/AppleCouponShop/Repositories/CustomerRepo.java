package com.Project.Project.AppleCouponShop.Repositories;

import com.Project.Project.AppleCouponShop.beans.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepo extends JpaRepository<Customer, Integer> {

    Customer findCustomerByEmailAndPassword(String email, String password);

    Customer findByEmail(String email);

    Customer findByCustomerId(int id);

}


