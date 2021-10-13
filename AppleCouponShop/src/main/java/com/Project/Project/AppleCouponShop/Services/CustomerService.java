package com.Project.Project.AppleCouponShop.Services;

import com.Project.Project.AppleCouponShop.Exceptions.MajorException;
import com.Project.Project.AppleCouponShop.enums.Category;
import com.Project.Project.AppleCouponShop.beans.Coupon;
import com.Project.Project.AppleCouponShop.beans.Customer;

import java.util.List;

public interface CustomerService {

    boolean login(String email,String password) throws MajorException;

    void updateCustomer(Customer customer) throws MajorException;

    void addCouponPurchase(Coupon coupon) throws MajorException;

    List<Coupon> getCustomerCoupons();

    List<Coupon> getCustomerCoupons(Category category);

    List<Coupon> getCustomerCoupons(double maxPrice);

    Customer getCustomerDetails();
}
