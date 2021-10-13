package com.Project.Project.AppleCouponShop.beans;

import lombok.*;
import org.springframework.stereotype.Service;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data // getters and setters , hashCode , equal , toString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Service
@Entity //ORM - Object Relation Mapping  ------ Entity class is a prototype we dont need ---> @Scope("prototype")
public class Company {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false)
    private int companyId;

    @Column(updatable = false)
    private String companyName;

    private String email, password;

    @Singular
    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Coupon> coupons = new ArrayList<>();

    private List<Company> getAllCompanies(Coupon coupon) {
        return null;
    }
    
    @Override
    public String toString() {
        return "Company{" +
                "companyId=" + companyId +
                ", companyName='" + companyName + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", coupons=" + coupons +
                '}';
    }
}
