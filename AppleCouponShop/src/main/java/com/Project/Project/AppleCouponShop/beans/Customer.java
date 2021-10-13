package com.Project.Project.AppleCouponShop.beans;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Data // getters and setters , hashCode , equal , toString
@NoArgsConstructor
@AllArgsConstructor
@Builder
//@Service
//spring JPA
@Entity
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int customerId;
    private String firstName;
    private String lastName;
    private String email;
    private String password;

    //private CustomerRepo customerRepo;

    @Singular
    @OneToMany(cascade = CascadeType.ALL,orphanRemoval = true, fetch = FetchType.EAGER)
    private List<Coupon> coupons;

//    public List<Customer> getAllCustomers(){
//        return customerRepo.findAll();
//    }
}
