package com.Project.Project.AppleCouponShop.Services;

import com.Project.Project.AppleCouponShop.Exceptions.MajorException;
import com.Project.Project.AppleCouponShop.enums.Category;
import com.Project.Project.AppleCouponShop.beans.Company;
import com.Project.Project.AppleCouponShop.beans.Coupon;

import java.util.List;

public interface CompanyService {

    boolean login(String email, String password) throws MajorException;

    Coupon addCoupon(Coupon coupon) throws MajorException;

    Coupon updateCoupon(Coupon coupon) throws MajorException;

    void deleteCoupon(int couponId) throws MajorException;

    Coupon findByCouponId(int couponId) throws MajorException;

    List<Coupon> getCompanyCoupon();

    List<Coupon> getCompanyCoupons(Category category);

    List<Coupon> getCompanyCoupons(double maxPrice);

    Company getCompanyDetails();
}
