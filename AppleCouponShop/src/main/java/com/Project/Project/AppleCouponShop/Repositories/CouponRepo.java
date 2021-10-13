package com.Project.Project.AppleCouponShop.Repositories;

import com.Project.Project.AppleCouponShop.enums.Category;
import com.Project.Project.AppleCouponShop.beans.Coupon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.List;

public interface CouponRepo extends JpaRepository<Coupon, Integer> {

    List<Coupon> findByCompanyId(int companyId);

    Coupon findByCouponId(int couponId);

    Coupon findByCompanyIdAndTitle(int companyId, String title);

    @Transactional
    @Modifying
    @Query(value = "DELETE FROM `exception`.`customer_coupons` WHERE coupons_coupon_id=:couponId", nativeQuery = true)
    void deleteCustomerCoupon(int couponId);

    @Transactional
    @Modifying
    @Query(value = "DELETE FROM `luxurycouponsspring`.`company_coupons` WHERE `coupons_coupon_id`=:couponId", nativeQuery = true)
    void deleteCompanyCoupon(int couponId);

    List<Coupon> findByCategory(Category category);

}
