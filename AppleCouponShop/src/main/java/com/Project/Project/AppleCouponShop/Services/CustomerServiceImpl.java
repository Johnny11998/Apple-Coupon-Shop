package com.Project.Project.AppleCouponShop.Services;

import com.Project.Project.AppleCouponShop.Exceptions.MajorException;
import com.Project.Project.AppleCouponShop.Repositories.CouponRepo;
import com.Project.Project.AppleCouponShop.Repositories.CustomerRepo;
import com.Project.Project.AppleCouponShop.enums.Category;
import com.Project.Project.AppleCouponShop.beans.Coupon;
import com.Project.Project.AppleCouponShop.beans.Customer;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@Data
public class CustomerServiceImpl extends ClientService implements CustomerService {

    @Autowired
    private CustomerRepo customerRepo;

    @Autowired
    private CouponRepo couponRepo;

    private int customerId;

    @Override
    public boolean login(String email, String password) throws MajorException {
        if (customerRepo.findCustomerByEmailAndPassword(email, password) == null) {
            throw new MajorException("Incorrect email or password, try again !");
        }
        Customer customer = customerRepo.findCustomerByEmailAndPassword(email, password);
        this.customerId = getCustomerId();
        System.out.println("Logging was successful, Welcome: " + customer.getFirstName() + " " + customer.getLastName());
        return true;
    }

    @Override
    public void updateCustomer(Customer customer) throws MajorException {
        System.out.println("Adding new customer to the system");
        if (!(customerRepo.existsById(customerId))) {
            throw new MajorException("Customer not found");
        } else {
            customer.setCustomerId(customerId);
            customerRepo.saveAndFlush(customer);
        }
    }

    @Override
    public void addCouponPurchase(Coupon coupon) throws MajorException {
        Coupon myCoupon = couponRepo.findByCompanyIdAndTitle(coupon.getCompanyId(), coupon.getTitle());
        if (!(couponRepo.existsById(myCoupon.getCouponId()))) {
            throw new MajorException("Coupon doesn't exist !");
        }
        Customer customer = customerRepo.getOne(customerId);
        List<Coupon> coupons = customerRepo.findByCustomerId(customerId).getCoupons();
        Date currentDate = new java.util.Date(new java.util.Date().getTime());
        //check if the coupon is expired
        if (currentDate.after(myCoupon.getEnd_date())) {
            throw new MajorException("Coupon purchase unsuccessful ! \n Coupon Expired");
        }
        //check if coupon available
        if (myCoupon.getAmount() < 1) {
            throw new MajorException("Coupon purchase unsuccessful ! \n Coupon not available");
        }
        if (coupons.size() > 0) {
            for (Coupon item : coupons) {
                if ((myCoupon.getCouponId() == item.getCouponId())) {
                    throw new MajorException("Coupon purchase unsuccessful !\n Coupon already been purchased");
                }
            }
        }
        //update the amount after purchase
        myCoupon.setAmount((myCoupon.getAmount()) - 1);
        couponRepo.save(myCoupon);
        coupons.add(myCoupon);
        customer.setCoupons(coupons);
        customerRepo.save(customer);
        System.out.println("Coupon " + myCoupon.getTitle() + " purchased successfully !");
    }

    @Override
    public List<Coupon> getCustomerCoupons() {
        return customerRepo.getOne(customerId).getCoupons();
    }

    @Override
    public List<Coupon> getCustomerCoupons(Category category) {
        List<Coupon> coupons = getCustomerCoupons();
        coupons.removeIf(coupon -> !(coupon.getCategory().equals(category)));
        return coupons;
    }

    @Override
    public List<Coupon> getCustomerCoupons(double maxPrice) {
        List<Coupon> coupons = getCustomerCoupons();
        coupons.removeIf(coupon -> coupon.getPrice() > maxPrice);
        return coupons;
    }
    @Override
    public Customer getCustomerDetails() {
        return customerRepo.findByCustomerId(this.customerId);
    }
}


