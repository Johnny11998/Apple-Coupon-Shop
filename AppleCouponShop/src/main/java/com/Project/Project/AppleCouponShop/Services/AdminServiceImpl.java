package com.Project.Project.AppleCouponShop.Services;

import com.Project.Project.AppleCouponShop.Exceptions.MajorException;
import com.Project.Project.AppleCouponShop.Repositories.CompanyRepo;
import com.Project.Project.AppleCouponShop.Repositories.CouponRepo;
import com.Project.Project.AppleCouponShop.Repositories.CustomerRepo;
import com.Project.Project.AppleCouponShop.beans.Company;
import com.Project.Project.AppleCouponShop.beans.Customer;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.List;
import java.util.Optional;

@Service
@Data
@RequiredArgsConstructor
public class AdminServiceImpl extends ClientService implements AdminService {
    private final CompanyRepo companyRepo;
    private final CouponRepo couponRepo;
    private final CustomerRepo customerRepo;


    //---------------------------------------------------COMPANY---------------------------------------------------------
    @Override
    public boolean login(String email, String password) throws MajorException {
        if (!email.equals("admin@admin.com") && (!password.equals("admin"))) {
            throw new MajorException("Something went wrong, try other password or email");
        } else {
            System.out.println("Logging was successful, welcome Admin");
            return true;
        }
    }

    //------------------------------------------------------------------------------------------------------------------
    @Override
    @CrossOrigin
    public Company addCompany(Company company) throws MajorException {
        for (Company item : companyRepo.findAll()) {
            if (!company.getEmail().equals(item.getEmail()) || item.getCompanyName().equals(company.getCompanyName())) {
                throw new MajorException("Company " + company.getCompanyName() + " already exists");
            }
        }
        companyRepo.save(company);
        System.out.println("Company " + company.getCompanyName() + " added to the system");
        return companyRepo.findByEmailAndPassword(company.getEmail(), company.getPassword());
    }
    //------------------------------------------------------------------------------------------------------------------

    @Override
    public Company updateCompany(Company company) throws MajorException { //todo :you must get id in company
        if (!(companyRepo.existsById(company.getCompanyId()))) {
            throw new MajorException("Company not found !");
        } else {
            company.setCompanyId(company.getCompanyId());
            companyRepo.save(company);
        }
        return companyRepo.findByEmailAndPassword(company.getEmail(), company.getPassword());
    }

    //------------------------------------------------------------------------------------------------------------------
    //------------------------------------------------------------------------------------------------------------------
    @Override
    public void deleteCompany(String email) throws MajorException {
        int companyId = companyRepo.findByEmail(email).getCompanyId();
        if (!(companyRepo.existsById(companyId))) {
            throw new MajorException("Company id doesn't exist !");
        }
        companyRepo.getOne(companyId).getCoupons().forEach(item -> couponRepo.deleteCompanyCoupon(item.getCouponId()));
        companyRepo.deleteById(companyId);
        System.out.println("Compֿֿany " + companyId + " deleted successfully");
    }


    //------------------------------------------------------------------------------------------------------------------
    public Optional<Company> getOneCompany(int id) {
        return companyRepo.findById(id);
    }

    //------------------------------------------------------------------------------------------------------------------
    @Override
    public List<Company> getAllCompanies() {
        return companyRepo.findAll();
    }

    //------------------------------------------------------------------------------------------------------------------
    @Override
    public Company findByCompanyId(int companyId) throws MajorException {
        if (!(companyRepo.existsById(companyId))) {
            throw new MajorException("Company id doesn't exist");
        }
        return companyRepo.findByCompanyId(companyId);
    }
    //-----------------------------------------------------COMPANY--------------------------------------------------------
    //-----------------------------------
    //-----------------------------------------------------CUSTOMER-------------------------------------------------------
    public Customer addCustomer(Customer customer) throws MajorException {
        for (Customer item : customerRepo.findAll()) {
            if (customer.getEmail().equals(item.getEmail())) {
                throw new MajorException("Customer " + customer.getFirstName() + customer.getLastName() + " already exists");
            }
        }
        customerRepo.save(customer);
        System.out.println("Customer " + customer.getFirstName() + customer.getLastName() + " add to the system");
        return customerRepo.findCustomerByEmailAndPassword(customer.getEmail(), customer.getPassword());
    }



    //------------------------------------------------------------------------------------------------------------------
    @Override
    public void updateCustomer(Customer customer) throws MajorException {
        if (!(customerRepo.existsById(customer.getCustomerId()))) {
            throw new MajorException("Customer not found");
        } else {
            customer.setCustomerId(customer.getCustomerId());
            customerRepo.saveAndFlush(customer);
        }
    }

    public void deleteCustomer(String email) throws MajorException {
        int customerId = customerRepo.findByEmail(email).getCustomerId();
        if (!(companyRepo.existsById(customerId))) {
            throw new MajorException("Company id doesn't exist");
        }
        customerRepo.getOne(customerId).getCoupons().forEach(item -> couponRepo.deleteCompanyCoupon(item.getCouponId()));
        customerRepo.deleteById(customerId);
        System.out.println("Customer " + customerId + " deleted successfully");
    }

    @Override
    public List<Customer> getAllCustomers() {
        return null;
    }

    @Override
    public Customer findByCustomerId(int customerId) throws MajorException {
        if (!(customerRepo.existsById(customerId))) {
            throw new MajorException("Customer id doesn't exist !");
        }
        return customerRepo.findByCustomerId(customerId);
    }
    //------------------------------------------------------------------------------------------------------------------

    public void deleteCustomer(int customerId) {
        customerRepo.deleteById(customerId);
    }
    //------------------------------------------------------------------------------------------------------------------

    public Customer getOneCustomer(int customerId) {
        return customerRepo.findByCustomerId(customerId);
    }
    //------------------------------------------------------------------------------------------------------------------

    public List<Customer> getAllCustomeris() {
        return customerRepo.findAll();
    }
    //------------------------------------------------------------------------------------------------------------------
    //-----------------------------------------------------CUSTOMER-----------------------------------------------------
}
