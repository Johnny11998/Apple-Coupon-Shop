package com.Project.Project.AppleCouponShop.Services;

import com.Project.Project.AppleCouponShop.Exceptions.MajorException;
import com.Project.Project.AppleCouponShop.Repositories.CompanyRepo;
import com.Project.Project.AppleCouponShop.Repositories.CouponRepo;
import com.Project.Project.AppleCouponShop.enums.Category;
import com.Project.Project.AppleCouponShop.beans.Company;
import com.Project.Project.AppleCouponShop.beans.Coupon;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CompanyServiceImpl extends ClientService implements CompanyService {

    @Autowired
    private CompanyRepo companyRepo; //update 19/09/21

    @Autowired
    private CouponRepo couponRepo; //update 20/09/21

    public int companyId;


    @Override
    public boolean login(String email, String password) throws MajorException { // 19/09/21
        if (companyRepo.findByEmailAndPassword(email, password) == null) {
            throw new MajorException("Company not found !");
        }
        Company company = companyRepo.findByEmailAndPassword(email, password);
        this.companyId = company.getCompanyId();
        System.out.println("Logging was succsessful , welcome Company: " + company.getCompanyName());
        return true;
    }


    public Coupon addCoupon(Coupon coupon) throws MajorException {
        for (Coupon item : couponRepo.findAll()) {
            if (coupon.getTitle().equals(item.getTitle()) && coupon.getCompanyId() == companyId) {
                throw new MajorException("Title already taken !");
            }
        }
        coupon.setCompanyId(companyId);
        couponRepo.save(coupon);
        Company company = companyRepo.getOne(companyId);
        company.getCoupons().add(coupon);

        adminService.updateCompany(company);
        System.out.println(coupon.getTitle() + "added to the company " + coupon.getCompanyId());
        return coupon;
    }

    public Coupon updateCoupon(Coupon coupon) throws MajorException {
        if  (!(couponRepo.existsById(coupon.getCouponId()))) {
            throw new MajorException("Coupon not found !");
        } else {
            couponRepo.saveAndFlush(coupon);
        }
        return couponRepo.findByCouponId(coupon.getCouponId());
    }

    public void deleteCoupon(int couponId) throws MajorException {
        if (!(couponRepo.existsById(couponId))) {
            throw new MajorException("Coupon id doesn't exist !");
        }
        couponRepo.deleteCompanyCoupon(couponId);
        couponRepo.deleteCustomerCoupon(couponId);
        couponRepo.deleteById(couponId);
        System.out.println("Coupon " + couponId + " deleted successfully !");

    }

    public Coupon findByCouponId(int couponId) throws MajorException {
        if (!(companyRepo.existsById(couponId))) {
            throw new MajorException("Coupon id doesn't exist! ");
        }
        return couponRepo.findByCouponId(couponId);
    }

    @Override
    public List<Coupon> getCompanyCoupon() {
        return null;
    }

    public List<Coupon> getCompanyCoupons() {
        return couponRepo.findByCompanyId(companyId);
    }

    public List<Coupon> getCompanyCoupons(Category category) {
        List<Coupon> coupons = getCompanyCoupons();
        coupons.removeIf(coupon -> !(coupon.getCategory().equals(category)));
        return coupons;
    }

    public List<Coupon> getCompanyCoupons(double maxPrice) {
        List<Coupon> coupons = getCompanyCoupons();
        coupons.removeIf(coupon -> coupon.getPrice() > maxPrice);
        return coupons;
    }

    public Company getCompanyDetails() { return companyRepo.findByCompanyId(companyId);

    }
}
